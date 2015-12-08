package org.onelab.monitor.agent.config;

/**
 * 系统常量
 * Created by chunliangh on 14-11-19.
 */
public interface Commons {

    String JAVA_OBJECT_TYPE = "java/lang/Object";
    // AgentHandler类相关描述
    String AGENT_HANDLER_CLASS = "org/onelab/monitor/agent/domain/AgentHandler";
    String AGENT_HANDLER_ENTER = "onEnter";
    String AGENT_HANDLER_FAIL = "onFail";
    String AGENT_HANDLER_EXIT = "onExit";
    String AGENT_HANDLER_ENTER_DESC = "([Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";
    String AGENT_HANDLER_FAIL_DESC = "(Ljava/lang/Throwable;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";
    String AGENT_HANDLER_EXIT_DESC = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";
    // 配置文件
    String AGENT_CONFIG_PATH = "agent-config.xml";
    // 用于获取项目路径的文件
    String AGENT_HOME_FILE = "one-monitor-agent-inner-file";
    // 构造子
    String CONSTRUCTOR_PATTERN = "<(cl)?init>";
    // 非法匹配
    String ILLEGAL_PATTERN = "\\$.*|.*\\$\\$.*|java/.*|javax/.*|sun/.*|sunw/.*|com/sun/.*|oracle/.*|com/oracle/.*|" +
            "javafx/.*|com/javafx/.*|jdk/.*|classes/.*|netscape/.*|apple/.*|com/apple/.*|toolbarButtonGraphics/.*|" +
            "org/ietf/jgss/.*|org/jcp/xml/dsig/internal/.*|org/omg/.*|org/w3c/dom/.*|org/xml/sax/.*|WrapperGenerator|" +
            "org/relaxng/datatype/.*|org/onelab/monitor/agent/.*|org/objectweb/asm/.*";
    // 默认排除的类
    String DEFAULT_EXCLUDE = "net/.*|org/.*|com/intellij/.*|com/google/.*|com/thoughtworks/.*|ch/qos/.*|com/alibaba/.*";
}
