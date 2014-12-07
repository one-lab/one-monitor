package org.onelab.monitor.agent.config;

/**
 * 系统常量
 * Created by chunliangh on 14-11-19.
 */
public interface Commons {

    String JAVA_OBJECT_TYPE = "java/lang/Object";
    //转换标签描述
    String AGENT_TRANSFORMED_CLASS = "Lorg/onelab/monitor/agent/transform/TransformedClass;";
    String AGENT_TRANSFORMED_METHOD = "Lorg/onelab/monitor/agent/transform/TransformedMethod;";
    //AgentHandler类相关描述
    String AGENT_HANDLER_CLASS = "org/onelab/monitor/agent/domain/AgentHandler";
    String AGENT_HANDLER_ENTER = "onEnter";
    String AGENT_HANDLER_FAIL = "onFail";
    String AGENT_HANDLER_EXIT = "onExit";
    String AGENT_HANDLER_ENTER_DESC = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V";
    String AGENT_HANDLER_FAIL_DESC = "(Ljava/lang/Throwable;)V";
    String AGENT_HANDLER_EXIT_DESC = "(Ljava/lang/Object;)V";
    //排除构造子
    String CONSTRUCTOR_PATTERN = "<(cl)?init>";
    String SET = "set";
    //非法匹配
    String ILLEGAL_PATTERN = "\\$.*|.*\\$\\$.*|java/.*|javax/.*|sun/.*|sunw/.*|com/sun/.*|oracle/.*|com/oracle/.*|" +
            "javafx/.*|com/javafx/.*|jdk/.*|classes/.*|netscape/.*|apple/.*|com/apple/.*|toolbarButtonGraphics/.*|" +
            "org/ietf/jgss/.*|org/jcp/xml/dsig/internal/.*|org/omg/.*|org/w3c/dom/.*|org/xml/sax/.*|WrapperGenerator|" +
            "org/relaxng/datatype/.*|org/onelab/monitor/agent/.*|org/objectweb/asm/.*";
    //默认排除包，默认情况下符合改匹配的包豆浆被排除，除非在强制列表中添加
    String DEFAULT_EXCLUDE = "net/.*|org/.*|com/intellij/.*|com/google/.*|com/thoughtworks/.*|ch/qos/.*|com/alibaba/.*";
    //默认白名单，如果设置了白名单则该默认值将被替换
    String DEFAULT_INCLUDE = ".*";

    String TRUE = "true";
}
