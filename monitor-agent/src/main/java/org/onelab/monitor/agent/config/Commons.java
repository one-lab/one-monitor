package org.onelab.monitor.agent.config;

/**
 * Created by chunliangh on 14-11-19.
 */
public interface Commons {
    String throwableClass = "java/lang/Throwable";
    String throwablePrintStackTrace = "printStackTrace";
    String agentHandlerClass = "org/onelab/monitor/agent/domain/AgentHandler";
    String agentHandlerEnter = "enter";
    String agentHandlerFail = "fail";
    String agentHandlerExit = "exit";
    String agentHandlerEnterDesc = "(Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V";
    String agentHandlerFailDesc = "(Ljava/lang/Throwable;)V";
    String agentHandlerExitDesc = "(Ljava/lang/Object;)V";
    String innerVoid = "()V";
}
