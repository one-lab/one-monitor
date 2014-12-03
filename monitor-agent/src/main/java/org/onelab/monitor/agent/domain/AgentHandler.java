package org.onelab.monitor.agent.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void onEnter(int flag,String className,String methodName,String methodDesc,Object thisObj, Object[] args){
        StringBuilder sb = new StringBuilder()
                .append("==>onEnter:")
                .append("class:").append(className)
                .append(",method:").append(methodName);
        System.out.println(sb);
        if (args!=null){
            JSONObject jsonObject = new JSONObject();
            for (int i=0;i<args.length;i++){
                jsonObject.put("args["+i+"]",args[i]!=null?args[i].toString():null);
            }
            System.out.println("    args:"+jsonObject.toJSONString());
        }
    }
    public static void onFail(Throwable th){
        System.out.println("==> onFail {"+th+"}");
    }
    public static void onExit(Object returnValue){
        System.out.println("==> onExit "+returnValue);
    }
}
