package org.onelab.monitor.agent.transform;

import org.onelab.monitor.agent.transform.asm.AgentClassAdapter;
import org.onelab.monitor.agent.transform.asm.AgentClassReader;
import org.onelab.monitor.agent.transform.asm.AgentClassWriter;
import org.onelab.monitor.agent.transform.asm.AgentUtil;
import org.onelab.monitor.agent.transform.matcher.TypeMatcher;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * one-monitor agent端类转换器
 * Created by chunliangh on 14-11-13.
 */
public class AgentTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException {
        if (TypeMatcher.match(className)) {
            AgentClassReader classReader = new AgentClassReader(classfileBuffer);
            if (classReader.usable()) {
                AgentClassWriter classWriter = new AgentClassWriter(classReader, loader);
                AgentClassAdapter classAdapter = new AgentClassAdapter(classWriter,className);
                classReader.accept(classAdapter, AgentUtil.getClassReaderFlags());
                return classWriter.toByteArray();
            }
        }
        return null;
    }
}
