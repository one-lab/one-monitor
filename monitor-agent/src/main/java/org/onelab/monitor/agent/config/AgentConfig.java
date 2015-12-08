package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.Agent;
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
        typePattern = new TypePattern(
            config.typeIncludepatterns,
            config.typeExcludepatterns,
            config.typeForceincludepatterns);
        methodPattern = new MethodPattern(
            config.methodIncludepatterns,
            config.methodExcludepatterns);
        codeInserterBuilders = config.codeinserterbuilders;
    }

    private void initConfig() throws Throwable{
        initJarHome();
        Agent.logger.info("agent_config_path:"+jarHome+agent_config_path);
        this.config = Config.init(jarHome+agent_config_path);
        Agent.logger.info(
                new StringBuilder("config-log:{level:").append(config.logLevel).append(",console:")
                        .append(config.logConsole).append("}").toString()
        );
        Agent.logger.info(
                new StringBuilder("config-type: ")
                        .append("{includepatterns:").append(config.typeIncludepatterns)
                        .append(",excludepatterns:").append(config.typeExcludepatterns)
                        .append(",forceincludepatterns:").append(config.typeForceincludepatterns)
                        .append("}").toString()
        );
        Agent.logger.info(
                new StringBuilder("config-method: ")
                        .append("{includepatterns:").append(config.methodIncludepatterns)
                        .append(",excludepatterns:").append(config.methodExcludepatterns)
                        .append("}").toString()
        );
        Agent.logger.info(
                new StringBuilder("config-codeinserterbuilders: ")
                        .append(config.codeinserterbuilders).toString()
        );
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
