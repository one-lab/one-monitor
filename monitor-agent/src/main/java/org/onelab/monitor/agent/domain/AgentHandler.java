package org.onelab.monitor.agent.domain;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void onEnter(String pointCutName,boolean requireStore,String className,String methodName,String methodDesc,Object thisObj, Object[] args){
        StringBuilder sb = new StringBuilder()
                .append("==>onEnter method[")
                .append("pointCutName:").append(pointCutName)
                .append(",requireStore:").append(requireStore)
                .append(",className:").append(className)
                .append(",methodName:").append(methodName)
                .append(",methodDesc:").append(methodDesc)
                .append(",thisObj:").append(thisObj)
                .append(",args:").append(args).append("]");
        System.out.println(sb);
    }
    public static void onFail(Throwable th){
        System.out.println("==> onFail throwable:"+th);
    }
    public static void onExit(Object returnValue){
        System.out.println("==> onExit value:"+returnValue);
    }
}
