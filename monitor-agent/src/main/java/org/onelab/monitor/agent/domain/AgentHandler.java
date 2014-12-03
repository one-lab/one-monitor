package org.onelab.monitor.agent.domain;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void onEnter(int flag,String className,String methodName,String methodDesc,Object thisObj, Object[] args){
        StringBuilder sb = new StringBuilder()
                .append("==>onEnter {")
                .append("flag:").append(flag)
                .append(",class:").append(className)
                .append(",method:").append(methodName)
                .append(",desc:").append(methodDesc)
//                .append(",thisObj:").append(thisObj)
//                .append(",args:").append(args).append("]");
                .append("}");
        System.out.println(sb);
    }
    public static void onFail(Throwable th){
        System.out.println("==> onFail {"+th+"}");
    }
    public static void onExit(Object returnValue){
        System.out.println("==> onExit {"+returnValue+"}");
    }
}
