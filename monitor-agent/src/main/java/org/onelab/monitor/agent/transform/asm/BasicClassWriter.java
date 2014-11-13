package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassWriter;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicClassWriter extends ClassWriter {

    public BasicClassWriter(BasicClassReader classReader, ClassLoader loader) {
        super(classReader, BasicUtil.getClassWriterFlags());
    }

    @Override
    public byte[] toByteArray() {
        return super.toByteArray();
    }
}
