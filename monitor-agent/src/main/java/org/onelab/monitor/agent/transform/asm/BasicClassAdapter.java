package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassVisitor;

/**
 * Created by chunliangh on 14-11-13.
 */
public class BasicClassAdapter extends ClassVisitor {

    public BasicClassAdapter(int api) {
        super(api);
    }
}
