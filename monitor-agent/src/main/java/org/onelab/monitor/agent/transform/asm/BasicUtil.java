package org.onelab.monitor.agent.transform.asm;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicUtil {
    static char minorVersion ;
    static {
        String version = System.getProperty("java.version");
        minorVersion = version.charAt(2);
    }
    static final int CLASSREADER_FLAG = minorVersion >= '6' ? 8 : 0;

    static final int CLASSWRITER_FLAG = minorVersion >= '7' ? 2 : 1;

    public static int getClassReaderFlags() {
        return CLASSREADER_FLAG;
    }

    public static int getClassWriterFlags() {
        return CLASSWRITER_FLAG;
    }
}
