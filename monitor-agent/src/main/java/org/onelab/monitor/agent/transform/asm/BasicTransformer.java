package org.onelab.monitor.agent.transform.asm;

import java.security.ProtectionDomain;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicTransformer {

    public static byte[] transform(ClassLoader loader, String className, byte[] classfileBuffer) {
        BasicClassReader classReader = new BasicClassReader(classfileBuffer);
        if (classReader.usable()) {
            BasicClassAdapter classAdapter = new BasicClassAdapter(0);
            classReader.accept(classAdapter, BasicUtil.getClassReaderFlags());
            BasicClassWriter classWriter = new BasicClassWriter(classReader, loader);
            return classWriter.toByteArray();
        }
        return null;
    }
}
