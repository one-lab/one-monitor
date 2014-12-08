package org.onelab.monitor.agent;

import org.onelab.monitor.agent.config.AgentConfig;
import org.onelab.monitor.agent.log.AgentLogger;
import org.onelab.monitor.agent.transform.AgentTransformer;
import org.onelab.monitor.agent.transform.asm.AgentUtil;
import org.onelab.monitor.agent.transport.Consumer;

import java.lang.instrument.Instrumentation;

/**
 * Created by chunliangh on 14-11-13.
 */
public class Agent {

    public static AgentLogger logger = new AgentLogger();
    public static AgentConfig config = new AgentConfig();

    private static AgentTransformer agentTransformer = new AgentTransformer();
    private static Instrumentation instrumentation ;

    public static void premain(String options, Instrumentation inst){
        instrumentation = inst;
        try {
            start();
            logger.info("One-Monior-Agent start success!");
        }catch (Throwable t){
            logger.error("One-Monior-Agent start error!",t);
        }
    }
    private static void start() throws Throwable{
        config.init();
        logger.info("AgentConfig init success...");
        Consumer.start();
        logger.info("Consumer start success...");
        instrumentation.addTransformer(agentTransformer);
        logger.info("AgentTransformer has bean registered...");
        setupShutdownHooks();
        logger.info("setupShutdownHooks success...");
    }
    private static void stop(){
        instrumentation.removeTransformer(agentTransformer);
        logger.info("One-Monior-Agent stop success!");
    }
    private static void setupShutdownHooks() {
        Thread thread = new Thread("OneMoniorAgent_Cleaner"){
            public void run() {
                Agent.stop();
            }
        };
        thread.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(thread);
    }
    public static void main(String[] args){
        System.out.println(AgentUtil.class);
    }
}
