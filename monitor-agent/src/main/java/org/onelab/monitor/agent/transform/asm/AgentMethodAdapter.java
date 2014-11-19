package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.TransformedMethod;

import java.util.logging.Level;

/**
 * Created by chunliangh on 14-11-14.
 */
public class AgentMethodAdapter extends AdviceAdapter implements Opcodes,Commons{

    private String pointCutName;
    private String className;
    private String methodName;
    private String methodDesc;
    private boolean requireStore;//0不存储，1存储
    private Label startFinally = new Label();

    public AgentMethodAdapter(String pointCutName,boolean requireStore,String className,final MethodVisitor mv,
                              final int access, final String methodName, final String methodDesc) {
        super(ASM4,mv,access,methodName,methodDesc);
        this.pointCutName = pointCutName;
        this.requireStore = requireStore;
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }
    @Override
    protected void onMethodEnter() {
        Label startLabel = new Label();
        Label endLabel = new Label();
        Label exceptionLabel = new Label();

        this.mv.visitTryCatchBlock(startLabel, endLabel, exceptionLabel, throwableClass);
        this.mv.visitLabel(startLabel);

        this.mv.visitLdcInsn(this.pointCutName);
        if (requireStore){
            this.mv.visitInsn(ICONST_1);
        }else{
            this.mv.visitInsn(ICONST_0);
        }
        this.mv.visitLdcInsn(this.className);
        this.mv.visitLdcInsn(this.methodName);
        this.mv.visitLdcInsn(this.methodDesc);
        if ((this.methodAccess & Opcodes.ACC_STATIC) != 0) {
            this.mv.visitInsn(ACONST_NULL);
        } else {
            loadThis();
        }
        loadArgArray();
        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, agentHandlerClass, agentHandlerEnter, agentHandlerEnterDesc, false);
        this.mv.visitLabel(endLabel);
        Label doneLabel = new Label();
        this.mv.visitJumpInsn(Opcodes.GOTO, doneLabel);
        this.mv.visitLabel(exceptionLabel);

        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,throwableClass,throwablePrintStackTrace,innerVoid,false);
        this.mv.visitLabel(doneLabel);
    }
    @Override
    protected void onMethodExit(int opcode) {
        if (opcode != Opcodes.ATHROW) {
            onFinally(opcode);
        }
    }
    private void onFinally(int opcode) {
        Label doneLabel = new Label();
        ifNull(doneLabel);
        if (opcode == Opcodes.ATHROW) {
            exitWithException();
        } else {
            exitNormally(opcode);
        }
        visitLabel(doneLabel);
    }
    private void exitWithException() {
        dup();
        Type thType = Type.getType(Throwable.class);
        int th = newLocal(thType);
        storeLocal(th);
        loadLocal(th);
        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC,agentHandlerClass, agentHandlerFail, agentHandlerFailDesc,false);
    }
    private void exitNormally(int opcode) {
        Type returnType = Type.getReturnType(this.methodDesc);
        int returnVar = -1;
        if (opcode != Opcodes.RETURN) {
            if (opcode == Opcodes.ARETURN) {
                dup();
            } else {
                if ((opcode == Opcodes.LRETURN) || (opcode == Opcodes.DRETURN)) {
                    dup2();
                } else {
                    dup();
                }
                returnType = loadReturnValueAsObject(returnType);
            }
            returnVar = newLocal(returnType);
            storeLocal(returnVar, returnType);
        }
        if (opcode == Opcodes.RETURN) {
            this.mv.visitInsn(ACONST_NULL);
        } else {
            loadLocal(returnVar);
        }
        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,agentHandlerClass,agentHandlerExit,agentHandlerExitDesc,false);
    }
    private Type loadReturnValueAsObject(Type returnType) {
        if (returnType.equals(Type.BYTE_TYPE)) {
            returnType = Type.getType(Boolean.class);
            invokeStatic(returnType, new Method("valueOf", "(B)Ljava/lang/Byte;"));
        } else if (returnType.equals(Type.SHORT_TYPE)) {
            returnType = Type.getType(Short.class);
            invokeStatic(returnType, new Method("valueOf", "(S)Ljava/lang/Short;"));
        } else if (returnType.equals(Type.INT_TYPE)) {
            returnType = Type.getType(Integer.class);
            invokeStatic(returnType, new Method("valueOf", "(I)Ljava/lang/Integer;"));
        } else if (returnType.equals(Type.LONG_TYPE)) {
            returnType = Type.getType(Long.class);
            invokeStatic(returnType, new Method("valueOf", "(J)Ljava/lang/Long;"));
        } else if (returnType.equals(Type.BOOLEAN_TYPE)) {
            returnType = Type.getType(Boolean.class);
            invokeStatic(returnType, new Method("valueOf", "(Z)Ljava/lang/Boolean;"));
        } else if (returnType.equals(Type.CHAR_TYPE)) {
            returnType = Type.getType(Character.class);
            invokeStatic(returnType, new Method("valueOf", "(C)Ljava/lang/Character;"));
        } else if (returnType.equals(Type.FLOAT_TYPE)) {
            returnType = Type.getType(Float.class);
            invokeStatic(returnType, new Method("valueOf", "(F)Ljava/lang/Float;"));
        } else if (returnType.equals(Type.DOUBLE_TYPE)) {
            returnType = Type.getType(Double.class);
            invokeStatic(returnType, new Method("valueOf", "(D)Ljava/lang/Double;"));
        } else {
            pop();
            this.mv.visitInsn(ACONST_NULL);
        }
        return returnType;
    }
    @Override
    public void visitCode() {
        super.visitCode();
        this.mv.visitLabel(this.startFinally);
    }
    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
    }
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
//        axis2AbstractHttpSenderListener.visitMethodInsn(this.className, this.methodName,
//                opcode, owner, name,abstractHTTPSenderSetTimeoutsInserter);
    }
    @Override
    public void visitEnd() {
        super.visitEnd();
        this.mv.visitAnnotation(Type.getDescriptor(TransformedMethod.class), true);
    }
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        Label endFinally = new Label();
        this.mv.visitTryCatchBlock(this.startFinally, endFinally, endFinally, null);
        this.mv.visitLabel(endFinally);
        onFinally(Opcodes.ATHROW);
        this.mv.visitInsn(Opcodes.ATHROW);
        this.mv.visitMaxs(maxStack, maxLocals);
    }
}
