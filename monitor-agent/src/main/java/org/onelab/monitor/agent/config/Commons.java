package org.onelab.monitor.agent.config;

/**
 * 系统常量
 * Created by chunliangh on 14-11-19.
 */
public interface Commons {

    String JAVA_OBJECT_TYPE = "java/lang/Object";

    String AGENT_TRANSFORMED_CLASS = "Lorg/onelab/monitor/agent/transform/TransformedClass;";
    String AGENT_TRANSFORMED_METHOD = "Lorg/onelab/monitor/agent/transform/TransformedMethod;";

    String AGENT_HANDLER_CLASS = "org/onelab/monitor/agent/domain/AgentHandler";
    String AGENT_HANDLER_ENTER = "onEnter";
    String AGENT_HANDLER_FAIL = "onFail";
    String AGENT_HANDLER_EXIT = "onExit";
    String AGENT_HANDLER_ENTER_DESC = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V";
    String AGENT_HANDLER_FAIL_DESC = "(Ljava/lang/Throwable;)V";
    String AGENT_HANDLER_EXIT_DESC = "(Ljava/lang/Object;)V";

    String CONSTRUCTOR_PATTERN = "<(cl)?init>";
    String SET = "set";

    //If you want sum of the patterned classes to be transformed,please put the right pattern into the WhiteList
    //but it might be cause unexpected result
    String ILLEGAL_PATTERN = "\\$.*|.*\\$\\$.*|java/.*|javax/.*|sun/.*|com/sun/.*|org/.*|net/.*";
}
