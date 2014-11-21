package org.onelab.monitor.agent.config;

/**
 * Created by chunliangh on 14-11-19.
 */
public interface Commons {
    String javaObjectType = "java/lang/Object";
    String agentTransformedClass = "Lorg/onelab/monitor/agent/transform/TransformedClass;";
    String agentTransformedMethod = "Lorg/onelab/monitor/agent/transform/TransformedMethod;";
    String agentHandlerClass = "org/onelab/monitor/agent/domain/AgentHandler";
    String agentHandlerEnter = "onEnter";
    String agentHandlerFail = "onFail";
    String agentHandlerExit = "onExit";
    String agentHandlerEnterDesc = "(Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V";
    String agentHandlerFailDesc = "(Ljava/lang/Throwable;)V";
    String agentHandlerExitDesc = "(Ljava/lang/Object;)V";

    //If you want sum of the patterned classes to be transformed,please put the right pattern into the WhiteList
    //but it might be cause unexpected result
    String ILLEGAL_PATTERN = "\\$.*|.*\\$\\$.*|java/.*|javax/.*|sun/.*|com/sun/.*|org/.*";

}
