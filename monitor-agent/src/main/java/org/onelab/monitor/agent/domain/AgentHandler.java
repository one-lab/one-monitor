package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.store.MethodTag;
import org.onelab.monitor.agent.store.MethodTagStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void onEnter(Object[] args, Object thisObj, String className, String methodName, String methodDesc){
        StringBuilder sb = new StringBuilder()
            .append("==> enter -- ").append(className)
            .append("#").append(methodName)
            .append(":").append(thisObj==null?null:thisObj.hashCode());
//            .append("--isRecursiveMethod:")
//            .append(
//                MethodTagStore.isTagExist(className, methodName, methodDesc, MethodTag.RECURSIVE));
        if (args!=null){
            for (int i=0;i<args.length;i++){
                sb.append(" *args[").append(i).append("]=").append(args[i]!=null?args[i].toString():null);
            }
        }
        Agent.logger.info(sb.toString());

    }
    public static void onFail(Throwable throwable, Object thisObj, String className, String methodName, String methodDesc){
        StringBuilder sb = new StringBuilder()
            .append("<== fail -- ").append(className)
            .append("#").append(methodName)
            .append(":").append(thisObj==null?null:thisObj.hashCode())
            .append(" *throw ").append(throwable);
        Agent.logger.info(sb.toString());
    }
    public static void onExit(Object returnValue, Object thisObj, String className, String methodName, String methodDesc){
        StringBuilder sb = new StringBuilder()
            .append("<== exit -- ").append(className)
            .append("#").append(methodName)
            .append(":").append(thisObj==null?null:thisObj.hashCode())
            .append(" *return ").append(returnValue);
        Agent.logger.info(sb.toString());
    }
}
