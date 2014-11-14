package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassWriter;

/**
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassWriter extends ClassWriter {

    public AgentClassWriter(AgentClassReader classReader, ClassLoader loader) {
        super(classReader, AgentUtil.getClassWriterFlags());
    }

    @Override
    public byte[] toByteArray() {
        return super.toByteArray();
    }
}
