package org.onelab.monitor.agent.log;

import java.util.Date;

/**
 * monitor-agent系统日志打印器
 * Created by chunliangh on 14-11-21.
 */
public class AgentLogger {
    public void info(String msg){
        System.out.println(new Date().toLocaleString()+"-AgentLogger INFO : "+msg);
    }
    public void warn(String msg){
        System.out.println(new Date().toLocaleString()+"-AgentLogger WARN : "+msg);
    }
    public void warn(String msg,Throwable throwable){
        System.out.println(new Date().toLocaleString()+"-AgentLogger WARN : "+msg);
        System.out.println(throwable);
    }
    public void error(String msg){
        System.out.println(new Date().toLocaleString()+"-AgentLogger ERROR : "+msg);
    }
    public void error(String msg,Throwable throwable){
        System.out.println(new Date().toLocaleString()+"-AgentLogger ERROR : "+msg);
        System.out.println(throwable);
    }
}
