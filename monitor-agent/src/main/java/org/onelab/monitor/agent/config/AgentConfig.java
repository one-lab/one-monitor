package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.log.AgentLogger;
import org.onelab.monitor.agent.transform.pattern.MethodPattern;
import org.onelab.monitor.agent.transform.pattern.TypePattern;

import java.util.Set;

/**
 * 系统配置
 * Created by chunliangh on 14-11-13.
 */
public class AgentConfig {

    private Config config;
    private String jarHome;

    private TypePattern typePattern;
    private MethodPattern methodPattern;
    private Set<String> codeInserterBuilders;

    public void init() throws Throwable{
        initConfig();
        initAgentConfig();
    }

    private void initAgentConfig(){
        typePattern = new TypePattern(
            config.typeIncludepatterns,
            config.typeExcludepatterns);
        methodPattern = new MethodPattern(
            config.methodIncludepatterns,
            config.methodExcludepatterns);
        codeInserterBuilders = config.codeinserterbuilders;
    }

    private void initConfig() throws Throwable{
        initJarHome();
        AgentLogger.initialize(jarHome + "log");
        AgentLogger.sys.info("agent_config_path:" + jarHome + Const.AGENT_CONFIG_PATH);
        this.config = Config.init(jarHome+Const.AGENT_CONFIG_PATH);

        AgentLogger.sys.info(
                new StringBuilder("config-type: ")
                        .append("{includepatterns:").append(config.typeIncludepatterns)
                        .append(",excludepatterns:").append(config.typeExcludepatterns)
                        .append("}").toString()
        );
        AgentLogger.sys.info(
                new StringBuilder("config-method: ")
                        .append("{includepatterns:").append(config.methodIncludepatterns)
                        .append(",excludepatterns:").append(config.methodExcludepatterns)
                        .append("}").toString()
        );
        AgentLogger.sys.info(
                new StringBuilder("config-codeinserterbuilders: ")
                        .append(config.codeinserterbuilders).toString()
        );
    }

    private void initJarHome(){
        jarHome = Agent.class.getClassLoader().getResource(Const.AGENT_HOME_FILE).getPath();
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
}
