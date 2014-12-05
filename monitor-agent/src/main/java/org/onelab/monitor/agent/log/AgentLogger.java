package org.onelab.monitor.agent.log;

/**
 * monitor-agent系统日志打印器
 * Created by chunliangh on 14-11-21.
 */
public class AgentLogger {
    public void info(String msg,Throwable throwable){
        System.out.println("AgentLogger info:"+msg);
    }
    public void warn(String msg,Throwable throwable){
        System.out.println("AgentLogger warn:"+msg);
    }
    public void error(String msg,Throwable throwable){
        System.out.println("AgentLogger error:"+msg);
    }
}
