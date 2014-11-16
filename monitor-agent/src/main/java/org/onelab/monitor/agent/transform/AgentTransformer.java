package org.onelab.monitor.agent.transform;

import org.onelab.monitor.agent.transform.asm.AgentClassAdapter;
import org.onelab.monitor.agent.transform.asm.AgentClassReader;
import org.onelab.monitor.agent.transform.asm.AgentClassWriter;
import org.onelab.monitor.agent.transform.asm.AgentUtil;
import org.onelab.monitor.agent.transform.matcher.ClassNameMatcher;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by chunliangh on 14-11-13.
 */
public class AgentTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (ClassNameMatcher.match(className)) {
            System.out.println(className);
            AgentClassReader classReader = new AgentClassReader(classfileBuffer);
            if (classReader.usable()) {
                AgentClassWriter classWriter = new AgentClassWriter(classReader, loader);
                AgentClassAdapter classAdapter = new AgentClassAdapter(classWriter,className,classReader.getSuperName(),classReader.getInterfaces());
                classReader.accept(classAdapter, AgentUtil.getClassReaderFlags());
                return classWriter.toByteArray();
            }
        }
        return null;
    }
}
