package org.onelab.monitor.agent.config;

/**
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {
    private static boolean privateClass;
    private static boolean privateMethod;
    private static boolean setMethod;
    private static boolean methodFilter;

    public static void init() throws Exception{}

    public static String getWhiteListPatten() {
        return "com/jumei/.*";
    }

    public static String getBlackListPatten() {
        return null;
    }

    public static String getMethodWhiteListPatten() {
        return null;
    }

    public static String getMethodBlackListPatten() {
        return null;
    }

    public static boolean getPrivateClass() {
        return privateClass;
    }

    public static boolean getPrivateMethod() {
        return privateMethod;
    }

    public static boolean getSetMethod() {
        return setMethod;
    }

    public static boolean getMethodFilter() {
        return methodFilter;
    }
}
