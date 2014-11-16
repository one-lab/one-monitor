package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by chunliangh on 14-11-14.
 */
public class AgentMethodAdapter extends AdviceAdapter implements Opcodes {

    private String pointCutName;
    private String className;
    private boolean begStore;

    public AgentMethodAdapter(String pointCutName,String className,final MethodVisitor mv,
                              final int access, final String name, final String desc) {
        super(ASM5,mv,access,name,desc);
        this.pointCutName = pointCutName;
        this.className = className;
    }

    protected void onMethodEnter() {
    }
}
