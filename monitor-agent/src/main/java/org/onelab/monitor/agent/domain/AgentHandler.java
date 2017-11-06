package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.domain.track.Track;
import org.onelab.monitor.agent.domain.track.TrackService;
import org.onelab.monitor.agent.store.MethodTag;
import org.onelab.monitor.agent.store.MethodTagStore;

import java.util.Stack;
import java.util.UUID;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    static ThreadLocal<Integer> currIndex = new ThreadLocal();
    static ThreadLocal<Stack<Track>> trackStackLocal = new ThreadLocal();
    static TrackService trackService = new TrackService();

    private static Track buildTrack(String trackId, int index, Object[] args, Object thisObj, String className, String methodName, String methodDesc){
        Track track = new Track();
        track.setIndex(index);
        track.setTrackId(trackId);
        track.setClassName(className);
        track.setMethodName(methodName);
        track.setMethodDesc(methodDesc);
        track.setThisObj(thisObj);
        track.setArgs(args);
        track.setRecursive(MethodTagStore.isTagExist(className, methodName, methodDesc, MethodTag.RECURSIVE));
        track.setSTime(System.currentTimeMillis());
        return track;
    }

    public static void onEnter(Object[] args, Object thisObj, String className, String methodName, String methodDesc){
        Integer index = currIndex.get();
        if (index == null){
            index = 0;
            currIndex.set(index);
            Stack<Track> trackStack = new Stack<Track>();
            trackStackLocal.set(trackStack);
            String trackId = UUID.randomUUID().toString();
            trackStack.push(buildTrack(trackId, index, args, thisObj, className, methodName, methodDesc));
        } else {
            currIndex.set(++index);
            Stack<Track> trackStack = trackStackLocal.get();
            Track last = trackStack.peek();
            //递归方法只记录一次
            if (last.isRecursive() && last.getClassName().equals(className) &&
                last.getMethodName().equals(methodName) && last.getMethodDesc().equals(methodDesc)){
                return;
            }
            String trackId = last.getTrackId();
            trackStack.push(buildTrack(trackId, index, args, thisObj, className, methodName, methodDesc));
        }
    }

    public static void onExit(Object returnValue, Object thisObj,
                              String className, String methodName, String methodDesc){
        exit(returnValue, null, false, className, methodName, methodDesc);
    }

    public static void onFail(Throwable throwable, Object thisObj,
                              String className, String methodName, String methodDesc){
        exit(null, throwable, true, className, methodName, methodDesc);
    }

    private static void exit(Object returnValue, Throwable throwable, boolean isFail,
                             String className, String methodName, String methodDesc){
        long eTime = System.currentTimeMillis();
        Integer index = currIndex.get();
        Stack<Track> trackStack = trackStackLocal.get();
        Track last = trackStack.peek();
        if (last.getIndex() > index){
            Agent.logger.warn("AgentHandler.exit: last.getIndex()<index ! " + className + methodName + methodDesc);
            return;
        }
        if (last.getIndex() < index){
            currIndex.set(--index);
            return;
        }
        last = trackStack.pop();
        last.setETime(eTime);
        last.setThrowable(throwable);
        last.setReturnValue(returnValue);
        if (index == 0){
            currIndex.remove();
            trackStackLocal.remove();
        }
        currIndex.set(--index);
        // 逻辑处理
        trackService.execute(last, isFail);
    }
}
