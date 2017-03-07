package org.onelab.monitor.agent.log;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * monitor-agent系统日志打印器
 * Created by chunliangh on 14-11-21.
 */
public class AgentLogger {

    private static Logger logger;
    private static FileHandler fh;

    public void info(String msg){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
//        System.out.println("[" + time + "] [AGL] INFO : "+msg);
        logger.info(msg);
    }
    public void warn(String msg){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
//        System.out.println("[" + time + "] [AGL] WARN : "+msg);
        logger.warning(msg);
    }
    public void warn(String msg,Throwable throwable){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
//        System.out.println("[" + time + "] [AGL] WARN : "+msg);
//        System.out.println(throwable);
        logger.warning(msg);
        logger.warning(throwable.toString());
    }
    public void error(String msg){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
//        System.out.println("[" + time + "] [AGL] ERROR : "+msg);
        logger.severe(msg);
    }
    public void error(String msg,Throwable throwable){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
//        System.out.println("[" + time + "] [AGL] ERROR : "+msg);
//        System.out.println(throwable);
        logger.severe(msg);
        logger.severe(throwable.toString());
    }

    public static Logger initialize(String dir) {
        if (dir.startsWith("file:")){
            dir = dir.substring(5);
        }
        logger = Logger.getLogger("AGL");
        logger.setUseParentHandlers(false);
        File agentDir = new File(dir);
        if (!agentDir.isDirectory()){
            agentDir.mkdir();
        }
        String fileName = "agent.log";
        File logFile = new File(agentDir, fileName);
        try {
            int limit = 5 * 1024 * 1024;
            int count = 10;
            fh = new FileHandler(logFile.getPath(), limit, count, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fh.setFormatter(new LogFormatter());
//        ConsoleHandler consoleHandler = new ConsoleHandler();
//        consoleHandler.setFormatter(new LogFormatter());
        logger.addHandler(fh);
//        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
