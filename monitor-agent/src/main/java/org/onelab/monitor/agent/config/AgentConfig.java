package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.Agent;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统配置
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {

    private Config config;
    private String jarHome;
    private String agent_config_path = "agent-config.xml";

    private boolean privateClass;
    private boolean privateMethod;
    private boolean setMethod;
    private boolean methodFilter;
    private Set<String> codeInserterBuilders;

    public void init() throws Throwable{
//        codeInserterBuilders = new LinkedList<String>();
//        codeInserterBuilders.add("org.onelab.monitor.agent.transform.asm.inserter.builder.PicControllerBuilder");
        initConfig();
        codeInserterBuilders = config.codeinserterbuilders;
    }

    private void initConfig() throws Throwable{
        initJarHome();
        System.out.println("agent_config_path:"+agent_config_path);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);//开启验证XML功能
        SAXParser parser = factory.newSAXParser();
        ConfigHandler configHandler = new ConfigHandler();
        parser.parse(jarHome+agent_config_path,configHandler);
        this.config = configHandler.getConfig();
    }

    public void initJarHome(){
        jarHome = Agent.class.getClassLoader().getResource("one-monitor-agent-inner-file").getPath();
        if (jarHome.contains("!")){
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("!"));
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("/")+1);
        } else {
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("/")+1);
        }
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

    public Set<String> getCodeInserterBuilders() {
        return codeInserterBuilders;
    }

    public static void main(String[] args){
        Pattern p = Pattern.compile("a.*");
        Matcher m = p.matcher("ass");
//        m.reset("ass");
        System.out.println(m.matches());
        System.out.println(new AgentConfig().agent_config_path);
        String url = "Users/daojian/git/one-lab/one-monitor/monitor-agent/target/monitor-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar!/one-monitor-agent-inner-file";
        url = url.substring(0,url.lastIndexOf("!"));
        url = url.substring(0,url.lastIndexOf("/"));
        System.out.println(url);
        //Users/daojian/git/one-lab/one-monitor/monitor-agent/target/classes/one-monitor-agent-inner-file
        //Users/daojian/git/one-lab/one-monitor/monitor-agent/target/monitor-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar!/one-monitor-agent-inner-file
        ///Users/daojian/git/one-lab/one-monitor/monitor-agent/target/classes/config/agent-config.xml
        ///Users/daojian/git/one-lab/one-monitor/monitor-agent/target/monitor-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar!/config/agent-config.xml
    }
}
