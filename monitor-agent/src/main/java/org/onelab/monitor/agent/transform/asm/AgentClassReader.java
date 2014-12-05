package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.onelab.monitor.agent.Agent;

/**
 * monitor-agent类载入器
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassReader extends ClassReader {

    private static final boolean canAccPrivate = Agent.config.getPrivateClass();

    public AgentClassReader(byte[] b) {
        super(b);
    }
    public boolean usable() {
        int access = getAccess();
        if ((access & Opcodes.ACC_INTERFACE) != 0) return false;
        else if ((access & Opcodes.ACC_ENUM) != 0) return false;
        else if ((access & Opcodes.ACC_ANNOTATION) != 0) return false;
        else if (!canAccPrivate && (access & Opcodes.ACC_PRIVATE)!=0) return false;
        else return true;
    }
}
