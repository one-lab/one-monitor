package org.onelab.monitor.agent;

import org.onelab.monitor.agent.config.AgentConfig;
import org.onelab.monitor.agent.log.AgentLogger;
import org.onelab.monitor.agent.transform.AgentTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Created by chunliangh on 14-11-13.
 */
public class Agent {

    public static AgentConfig config = new AgentConfig();

    private static AgentTransformer agentTransformer = new AgentTransformer();
    private static Instrumentation instrumentation ;

    public static void premain(String options, Instrumentation inst){
        instrumentation = inst;
        try {
            start();
            AgentLogger.sys.info("One-Monitor-Agent start success!");
        } catch (Throwable t){
                AgentLogger.sys.severe("One-Monitor-Agent start error!");
            throw new RuntimeException(t);
        }
    }

    private static void start() throws Throwable{
        config.init();
        instrumentation.addTransformer(agentTransformer);
        AgentLogger.sys.info("AgentTransformer has bean registered...");
        setupShutdownHooks();
        AgentLogger.sys.info("setupShutdownHooks success...");
    }

    private static void stop(){
        instrumentation.removeTransformer(agentTransformer);
        AgentLogger.sys.info("One-Monior-Agent stop success!");
    }

    private static void setupShutdownHooks() {
        Thread thread = new Thread("OneMonitorAgent_Cleaner"){
            public void run() {
                Agent.stop();
            }
        };
        thread.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
