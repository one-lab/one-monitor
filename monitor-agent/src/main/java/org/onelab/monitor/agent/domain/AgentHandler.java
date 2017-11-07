package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.domain.track.Track;
import org.onelab.monitor.agent.domain.track.TrackService;
import org.onelab.monitor.agent.store.MethodTag;
import org.onelab.monitor.agent.store.MethodTagStore;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    private static TrackService trackService = new TrackService();
    private static ThreadLocal<ThreadTrackContext> trackContextLocal = new ThreadLocal();

    private static Track buildTrack(Object[] args, Object thisObj, String className, String methodName, String methodDesc){
        Track track = new Track();
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
        ThreadTrackContext trackContext = trackContextLocal.get();
        if (trackContext == null){
            trackContext = new ThreadTrackContext();
            trackContextLocal.set(trackContext);
        }
        if (trackContext.incrementCurrDeepsAndTestPush()){
            trackContext.push(buildTrack(args, thisObj, className, methodName, methodDesc));
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
        ThreadTrackContext trackContext = trackContextLocal.get();
        Track t = trackContext.testPop();
        if (t != null){
            t.setETime(eTime);
            t.setReturnValue(returnValue);
            t.setThrowable(throwable);
            trackService.execute(t, isFail);
        }
    }
}
