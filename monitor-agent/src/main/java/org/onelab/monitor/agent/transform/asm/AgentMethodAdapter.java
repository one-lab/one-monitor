package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.store.MethodTag;
import org.onelab.monitor.agent.store.MethodTagStore;
import org.onelab.monitor.agent.transform.TransformedMethod;
import org.onelab.monitor.agent.transform.asm.inserter.CodeInserter;
import org.onelab.monitor.agent.transform.asm.inserter.CodeInserterPool;

/**
 * monitor-agent方法适配器
 * Created by chunliangh on 14-11-14.
 */
public class AgentMethodAdapter extends AdviceAdapter implements Opcodes, Commons {

    private String className;
    private String methodName;
    private String methodDesc;

    /**
     * 方法标记：是否为递归方法
     */
    private boolean isRecursiveMethod = false;

    private Label startLabel= new Label();

    public AgentMethodAdapter(String className, final MethodVisitor mv,
                              final int access, final String methodName, final String methodDesc) {
        super(ASM4, mv, access, methodName, methodDesc);
        Agent.logger.info(" Method:"+methodName+methodDesc+" is being transformed...");
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    protected void onMethodEnter() {
        super.loadArgArray();
        if ((methodAccess & Opcodes.ACC_STATIC) != 0) {
            super.visitInsn(ACONST_NULL);
        } else {
            super.loadThis();
        }
        super.visitLdcInsn(className);
        super.visitLdcInsn(methodName);
        super.visitLdcInsn(methodDesc);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_HANDLER_CLASS,
                              AGENT_HANDLER_ENTER, AGENT_HANDLER_ENTER_DESC, false);
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
        if ((methodAccess & Opcodes.ACC_STATIC) != 0) {
            super.visitInsn(ACONST_NULL);
        } else {
            super.loadThis();
        }
        super.visitLdcInsn(className);
        super.visitLdcInsn(methodName);
        super.visitLdcInsn(methodDesc);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_HANDLER_CLASS, AGENT_HANDLER_FAIL, AGENT_HANDLER_FAIL_DESC, false);
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
        if ((methodAccess & Opcodes.ACC_STATIC) != 0) {
            super.visitInsn(ACONST_NULL);
        } else {
            super.loadThis();
        }
        super.visitLdcInsn(className);
        super.visitLdcInsn(methodName);
        super.visitLdcInsn(methodDesc);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_HANDLER_CLASS, AGENT_HANDLER_EXIT, AGENT_HANDLER_EXIT_DESC, false);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        super.visitLabel(this.startLabel);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (className.equals(owner) && methodName.equals(name) && methodDesc.equals(desc)){
            isRecursiveMethod = true;
        }
        CodeInserter inserter = null;
        try {
            inserter = CodeInserterPool.get(className, methodName, methodDesc,owner,name,desc);
        } catch (Throwable t){
            StringBuilder stringBuilder = new StringBuilder()
                    .append("CodeInserterPool get CodeInserter error:")
                    .append("target[").append(className).append("#").append(methodName+methodDesc).append("],")
                    .append("point[").append(owner).append("#").append(name+desc)
                    .append("#").append(inserter.getPointIndex()).append("]");
            Agent.logger.warn(stringBuilder.toString());
        }
        if (inserter == null){
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }else{
            inserter.insert(mv,opcode,owner,name,desc,itf);
            StringBuilder stringBuilder = new StringBuilder()
                    .append("CodeInserter=").append(inserter.getClass().getName()).append(":")
                    .append("target[").append(className).append("#")
                    .append(methodName+methodDesc).append("],")
                    .append("point[").append(owner).append("#")
                    .append(name+desc).append("#").append(inserter.getPointIndex()).append("]");
            Agent.logger.info(stringBuilder.toString());
        }
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        Label endLabel = new Label();
        super.visitTryCatchBlock(this.startLabel, endLabel, endLabel, null);
        super.visitLabel(endLabel);
        exitWithException();
        super.visitInsn(Opcodes.ATHROW);
        super.visitMaxs(0, 0);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        super.visitAnnotation(Type.getDescriptor(TransformedMethod.class), true);
        if (isRecursiveMethod){
            MethodTagStore.add(className,methodName,methodDesc, MethodTag.RECURSIVE);
        }
        Agent.logger.info(
            " Method:" + methodName + methodDesc + " has been Transformed. --isRecursiveMethod:" + isRecursiveMethod);
    }
}
