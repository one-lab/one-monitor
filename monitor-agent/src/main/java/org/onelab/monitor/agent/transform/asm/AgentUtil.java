package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * monitor-agent工具类
 * Created by chunliangh on 14-11-13.
 */
public class AgentUtil {

    /**
     * 小版本号
     */
    static char minorVersion ;
    static {
        String version = System.getProperty("java.version");
        minorVersion = version.charAt(2);
    }
    static final int CLASSREADER_FLAG = minorVersion >= '6' ? ClassReader.EXPAND_FRAMES : 0;

    static final int CLASSWRITER_FLAG = minorVersion >= '7' ? ClassWriter.COMPUTE_FRAMES : ClassWriter.COMPUTE_MAXS;

    /**
     * 获取类读取器标识
     * @return
     */
    public static int getClassReaderFlags() {
        return CLASSREADER_FLAG;
    }

    /**
     * 获取类写入器标识
     * @return
     */
    public static int getClassWriterFlags() {
        return CLASSWRITER_FLAG;
    }
}
