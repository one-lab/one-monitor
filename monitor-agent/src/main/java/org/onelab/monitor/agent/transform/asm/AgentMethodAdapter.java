package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.TransformedMethod;

/**
 * Created by chunliangh on 14-11-14.
 */
public class AgentMethodAdapter extends AdviceAdapter implements Opcodes, Commons {

    private String pointCutName;
    private String className;
    private String methodName;
    private String methodDesc;
    private boolean requireStore;//0不存储，1存储

    private Label startFinally = new Label();

    public AgentMethodAdapter(String pointCutName, boolean requireStore, String className, final MethodVisitor mv,
                              final int access, final String methodName, final String methodDesc) {
        super(ASM4, mv, access, methodName, methodDesc);
        this.pointCutName = pointCutName;
        this.requireStore = requireStore;
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    protected void onMethodEnter() {
        super.visitLdcInsn(pointCutName);
        if (requireStore) {
            super.visitInsn(ICONST_1);
        } else {
            super.visitInsn(ICONST_0);
        }
        super.visitLdcInsn(className);
        super.visitLdcInsn(methodName);
        super.visitLdcInsn(methodDesc);
        if ((methodAccess & Opcodes.ACC_STATIC) != 0) {
            super.visitInsn(ACONST_NULL);
        } else {
            super.loadThis();
        }
        super.loadArgArray();
        super.visitMethodInsn(Opcodes.INVOKESTATIC, agentHandlerClass, agentHandlerEnter, agentHandlerEnterDesc, false);
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (opcode != Opcodes.ATHROW) {
            exitNormally(opcode);
        }
    }

    private void exitWithException() {
        super.dup();
        Type thType = Type.getType(Throwable.class);
        int th = newLocal(thType);
        storeLocal(th);
        loadLocal(th);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, agentHandlerClass, agentHandlerFail, agentHandlerFailDesc, false);
    }

    private void exitNormally(int opcode) {
        if (opcode == RETURN) {
            visitInsn(ACONST_NULL);
        } else if (opcode == ARETURN || opcode == ATHROW) {
            super.dup();
        } else {
            if (opcode == LRETURN || opcode == DRETURN) {
                super.dup2();
            } else {
                super.dup();
            }
            super.box(Type.getReturnType(this.methodDesc));
        }
        super.visitMethodInsn(Opcodes.INVOKESTATIC, agentHandlerClass, agentHandlerExit, agentHandlerExitDesc, false);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        super.visitLabel(this.startFinally);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        Label endFinally = new Label();
        super.visitTryCatchBlock(this.startFinally, endFinally, endFinally, null);
        super.visitLabel(endFinally);
        exitWithException();
        super.visitInsn(Opcodes.ATHROW);
        super.visitMaxs(0, 0);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        super.visitAnnotation(Type.getDescriptor(TransformedMethod.class), true);
    }
}
