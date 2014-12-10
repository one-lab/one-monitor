package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.Agent;

import java.util.HashMap;
import java.util.Map;

/**
 * 方法监听处理器
 * Created by chunliangh on 14-11-16.
 */
public class AgentHandler {

    public static void onEnter(int flag,String className,String methodName,String methodDesc,Object thisObj, Object[] args){
        StringBuilder sb = new StringBuilder()
                .append("==>onEnter -- ").append(className).append("#").append(methodName);
        Agent.logger.info(sb.toString());
        if (args!=null){
            Map<String,Object> map = new HashMap<String, Object>();
            for (int i=0;i<args.length;i++){
                map.put("args["+i+"]",args[i]!=null?args[i].toString():null);
            }
            Agent.logger.info("--------args:"+map.toString());
        }
    }
    public static void onFail(Throwable th,int exitFlag){
        Agent.logger.info("==> onFail --"+exitFlag+"-- "+th);
    }
    public static void onExit(Object returnValue,int exitFlag){
        Agent.logger.info("==> onExit --"+exitFlag+"-- "+returnValue);
    }
}
