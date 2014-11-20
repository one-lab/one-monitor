package org.onelab.monitor.agent.domain;

/**
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void enter(String pointCutName,boolean requireStore,String className,String methodName,String methodDesc,Object thisObj, Object[] args){
        System.out.println("==>enter method..");
        System.out.println("==> pointCutName:"+pointCutName);
        System.out.println("==> requireStore:"+requireStore);
        System.out.println("==> className:"+className);
        System.out.println("==> methodName:"+methodName);
        System.out.println("==> methodDesc:"+methodDesc);
        System.out.println("==> thisObj:"+thisObj);
        System.out.println("==> args:"+args);

    }
    public static void fail(Throwable th){
        System.out.println("=======> fail th:"+th);
        System.out.println("=======>end method");
    }
    public static void exit(Object returnValue){
        System.out.println("==> exit returnValue:"+returnValue);
        System.out.println("==>end method");
    }
}
