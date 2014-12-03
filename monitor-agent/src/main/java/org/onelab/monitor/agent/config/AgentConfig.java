package org.onelab.monitor.agent.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统配置
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {
    private static boolean privateClass;
    private static boolean privateMethod;
    private static boolean setMethod;
    private static boolean methodFilter;

    public static void init() throws Exception{}

    public static String getWhiteListPatten() {
        return ".*";
    }

    public static String getBlackListPatten() {
        return "com/intellij/.*|com/google/.*|com/thoughtworks/.*|ch/qos/.*";
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

    public static String getForceListPatten() {
        return null;
    }

    public static void main(String[] args){
        Pattern p = Pattern.compile("a.*");
        Matcher m = p.matcher("ass");
//        m.reset("ass");
        System.out.println(m.matches());
    }
}
