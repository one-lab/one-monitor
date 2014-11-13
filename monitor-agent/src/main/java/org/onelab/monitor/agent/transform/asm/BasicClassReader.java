package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassReader;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicClassReader extends ClassReader {

    public BasicClassReader(byte[] b) {
        super(b);
    }
    public boolean usable() {
        return false;
    }
}
