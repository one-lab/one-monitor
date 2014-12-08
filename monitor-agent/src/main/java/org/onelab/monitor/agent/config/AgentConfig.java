package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.pattern.MethodPattern;
import org.onelab.monitor.agent.config.pattern.TypePattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Set;

/**
 * 系统配置
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {

    private Config config;
    private String jarHome;
    private String agent_config_path = Commons.AGENT_CONFIG_PATH;
    private String agent_home_file = Commons.AGENT_HOME_FILE;

    private TypePattern typePattern;
    private MethodPattern methodPattern;
    private Set<String> codeInserterBuilders;

    public void init() throws Throwable{
        initConfig();
        initAgentConfig();
    }

    private void initAgentConfig(){
        typePattern = new TypePattern(config.type.privateOn,
                config.type.includepatterns,config.type.excludepatterns,config.type.forceincludepatterns
        );
        methodPattern = new MethodPattern(config.method.privateOn,
                config.method.includepatterns,config.method.excludepatterns
        );
        codeInserterBuilders = config.codeinserterbuilders;
    }

    private void initConfig() throws Throwable{
        initJarHome();
        Agent.logger.info("agent_config_path:"+jarHome+agent_config_path);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);//开启验证XML功能
        SAXParser parser = factory.newSAXParser();
        ConfigHandler configHandler = new ConfigHandler();
        parser.parse(jarHome+agent_config_path,configHandler);
        this.config = configHandler.getConfig();
    }

    private void initJarHome(){
        jarHome = Agent.class.getClassLoader().getResource(agent_home_file).getPath();
        if (jarHome.contains("!")){
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("!"));
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("/")+1);
        } else {
            jarHome = jarHome.substring(0,jarHome.lastIndexOf("/")+1);
        }
    }

    public Set<String> getCodeInserterBuilders() {
        return codeInserterBuilders;
    }

    public TypePattern getTypePattern() {
        return typePattern;
    }

    public MethodPattern getMethodPattern() {
        return methodPattern;
    }

    public static void main(String[] args){
    }
}
