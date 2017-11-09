package org.onelab.monitor.agent.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * monitor-agent系统日志打印器
 * Created by chunliangh on 14-11-21.
 */
public class AgentLogger {

    public static Logger sys;
    public static Logger cus;

    public static final String filePreFix = "file:";
    public static final String sysLogFile = "sys.log";
    public static final String cusLogFile = "cus.log";

    public static final int maxSize = 30*1024*1024;
    public static final int maxFileCount = 500;

    public static void initialize(String dir) throws IOException {

        if (dir.startsWith(filePreFix)){
            dir = dir.substring(5);
        }

        File agentDir = new File(dir);
        if (!agentDir.isDirectory()){
            agentDir.mkdir();
        }

        sys = getLogger("SYS", agentDir, sysLogFile);
        cus = getLogger("CUS", agentDir, cusLogFile);
    }

    private static Logger getLogger(String name, File dir, String fileName) throws IOException {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logger.addHandler(getFileHandler(dir, fileName, Level.ALL));
        logger.setLevel(Level.ALL);
        return logger;
    }

    private static FileHandler getFileHandler(File dir, String fileName, Level level)
        throws IOException {
        File logFile = new File(dir, fileName);
        FileHandler handler = new FileHandler(logFile.getPath(), maxSize, maxFileCount, true);
        handler.setLevel(level);
        handler.setFormatter(new LogFormatter());
        return handler;
    }
}
