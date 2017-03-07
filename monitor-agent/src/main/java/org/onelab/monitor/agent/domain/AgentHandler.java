package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.Agent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    static ThreadLocal<String> trackId = new ThreadLocal();
    static ThreadLocal<AtomicInteger> investCount = new ThreadLocal();
    static ThreadLocal<Map<Integer,Long>> timeMap = new ThreadLocal<Map<Integer, Long>>();

    public static void onEnter(Object[] args, Object thisObj, String className, String methodName, String methodDesc){
        if (investCount.get() == null){
            investCount.set(new AtomicInteger(0));
        }
        int index = investCount.get().intValue();
        if (index == 0){
            trackId.set(UUID.randomUUID().toString());
        }
        if(timeMap.get() == null){
            timeMap.set(new HashMap<Integer, Long>());
        }
        timeMap.get().put(index,System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(trackId.get());
        for (int i=0; i<index; i++){
            sb.append(" >");
        }
        sb.append(" I -- ")
            .append(className)
            .append("#").append(methodName).append(methodDesc)
            .append(" [").append(thisObj==null?null:thisObj.hashCode()).append("]");
//            .append("--isRecursiveMethod:")
//            .append(
//                MethodTagStore.isTagExist(className, methodName, methodDesc, MethodTag.RECURSIVE));
        investCount.get().incrementAndGet();
//        if (args!=null){
//            for (int i=0;i<args.length;i++){
//                sb.append(" *args[").append(i).append("]=").append(args[i]!=null?args[i].toString():null);
//            }
//        }
        Agent.logger.info(sb.toString());
    }
    public static void onFail(Throwable throwable, Object thisObj, String className, String methodName, String methodDesc){
        investCount.get().decrementAndGet();
        int index = investCount.get().intValue();
        StringBuilder sb = new StringBuilder(trackId.get());
        for (int i=0; i<index; i++){
            sb.append(" >");
        }
        sb.append(" F -- ").append(className)
            .append("#").append(methodName).append(methodDesc)
            .append(" [").append(thisObj==null?null:thisObj.hashCode()).append("]");
//            .append(" *throw ").append(throwable);
        sb.append(" [").append(index).append(".cost:").append(System.currentTimeMillis()-timeMap.get().get(index)).append("ms]");
        Agent.logger.info(sb.toString());
    }
    public static void onExit(Object returnValue, Object thisObj, String className, String methodName, String methodDesc){
        investCount.get().decrementAndGet();
        int index = investCount.get().intValue();
        StringBuilder sb = new StringBuilder(trackId.get());
        for (int i=0; i<index; i++){
            sb.append(" >");
        }
        sb.append(" O -- ").append(className)
            .append("#").append(methodName).append(methodDesc)
            .append(" [").append(thisObj==null?null:thisObj.hashCode()).append("]");
//            .append(" *return ").append(returnValue);
        sb.append(" [").append(index).append(".cost:").append(System.currentTimeMillis()-timeMap.get().get(index)).append("ms]");
        Agent.logger.info(sb.toString());
    }
}
