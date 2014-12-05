package org.onelab.monitor.agent.config;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统配置
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {
    private boolean privateClass;
    private boolean privateMethod;
    private boolean setMethod;
    private boolean methodFilter;
    private List<String> codeInserterBuilders;

    public void init() throws Exception{
        codeInserterBuilders = new LinkedList<String>();
        codeInserterBuilders.add("org.onelab.monitor.agent.transform.asm.inserter.builder.PicControllerBuilder");
    }

    public String getWhiteListPatten() {
        return "com/jumei/.*";
    }

    public String getBlackListPatten() {
        return "com/intellij/.*|com/google/.*|com/thoughtworks/.*|ch/qos/.*|com/alibaba/.*";
    }

    public String getMethodWhiteListPatten() {
        return null;
    }

    public String getMethodBlackListPatten() {
        return null;
    }

    public boolean getPrivateClass() {
        return privateClass;
    }

    public boolean getPrivateMethod() {
        return privateMethod;
    }

    public boolean getSetMethod() {
        return setMethod;
    }

    public boolean getMethodFilter() {
        return methodFilter;
    }

    public String getForceListPatten() {
        return null;
    }

    public List<String> getCodeInserterBuilders() {
        return codeInserterBuilders;
    }

    public static void main(String[] args){
        Pattern p = Pattern.compile("a.*");
        Matcher m = p.matcher("ass");
//        m.reset("ass");
        System.out.println(m.matches());
    }
}
