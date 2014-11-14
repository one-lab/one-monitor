package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.onelab.monitor.agent.Agent;

import java.util.logging.Level;

/**
 * Created by chunliangh on 14-11-14.
 */
public class AgentMethodAdapter extends JSRInlinerAdapter {

    private static final String AGENT_INTERNAL_NAME = Agent.class.getName().replace('.', '/');

    public AgentMethodAdapter(String className,final MethodVisitor mv,
                              final int access, final String name, final String desc,
                              final String signature, final String[] exceptions) {
        super(Opcodes.ASM5,mv,access,name,desc,signature,exceptions);
        this.className = className;
        this.methodName = name;
        this.pointCutName = matchedInterceptor.getClass().getName();
        axis2AbstractHttpSenderListener = AsmVisitMethodListener.newInstance(mv,"org/apache/axis2/transport/http/AbstractHTTPSender","executeMethod");

    }



    private static final String HANDLER_INTERNAL_NAME = JavaAgentHandler.class.getName().replace('.', '/');
    private static final String HANDLE_NAME = "getHandler";
    private static final String HANDLE_METHOD_DESC = "()L" + HANDLER_INTERNAL_NAME + ";";
    private static final String TRACE_METHOD_DESC = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)Lcom/sinosoft/monitor/agent/trackers/Tracker;";
    protected String className;
    protected String methodName;
    protected String pointCutName;
    private Label startFinally = new Label();
    private int tracerVarId;
    private AsmVisitMethodListener axis2AbstractHttpSenderListener;
    private MethodCodeInserter abstractHTTPSenderSetTimeoutsInserter = new MethodCodeInserter("org/apache/axis2/transport/http/AbstractHTTPSender", "setTimeouts", 0) {
        @Override
        public void insert(MethodVisitor mv) {
            //aload 4
            mv.visitVarInsn(ALOAD, 4);
            //ldc "_one_m_agent_key_"
            mv.visitLdcInsn("_one_m_agent_key_");
            //invokestatic com/sinosoft/monitor/agent/util/AgentKeyUtil/createOneMAgentKey()Ljava/lang/String;
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/sinosoft/monitor/agent/util/AgentKeyUtil", "createOneMAgentKey", "()Ljava/lang/String;");
            //invokeinterface org/apache/commons/httpclient/HttpMethod/addRequestHeader(Ljava/lang/String;Ljava/lang/String;)V 3
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "org/apache/commons/httpclient/HttpMethod", "addRequestHeader", "(Ljava/lang/String;Ljava/lang/String;)V");
        }
    };

    public void visitCode() {
        super.visitCode();
        this.mv.visitLabel(this.startFinally);
    }

    protected void onMethodEnter() {
        this.tracerVarId = newLocal(Type.getType(Tracker.class));
        loadNull();
        storeLocal(this.tracerVarId);

        Label startLabel = new Label();
        Label endLabel = new Label();
        Label exceptionLabel = new Label();
        this.mv.visitTryCatchBlock(startLabel, endLabel, exceptionLabel, "java/lang/Throwable");
        this.mv.visitLabel(startLabel);

        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_INTERNAL_NAME, HANDLE_NAME, HANDLE_METHOD_DESC);
        this.mv.visitLdcInsn(this.pointCutName);
        this.mv.visitLdcInsn(this.className);
        this.mv.visitLdcInsn(this.methodName);
        this.mv.visitLdcInsn(this.methodDesc);

        if ((this.methodAccess & Opcodes.ACC_STATIC) != 0) {
            loadNull();
        } else {
            loadThis();
        }

        loadArgArray();
        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, HANDLER_INTERNAL_NAME, "invokeTracker", TRACE_METHOD_DESC);
        storeLocal(this.tracerVarId);
        this.mv.visitLabel(endLabel);

        Label doneLabel = new Label();
        this.mv.visitJumpInsn(Opcodes.GOTO, doneLabel);
        this.mv.visitLabel(exceptionLabel);

        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V");

        this.mv.visitLabel(doneLabel);
        JavaAgent.logger.log(Level.FINER, "Successfully instrumented method entry for {0}.{1}", new Object[]{this.className, this.methodName});
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        Label endFinally = new Label();
        this.mv.visitTryCatchBlock(this.startFinally, endFinally, endFinally, null);
        this.mv.visitLabel(endFinally);
        onFinally(Opcodes.ATHROW);
        this.mv.visitInsn(Opcodes.ATHROW);
        this.mv.visitMaxs(maxStack, maxLocals);
    }

    protected void onMethodExit(int opcode) {
        if (opcode != Opcodes.ATHROW) {
            onFinally(opcode);
            JavaAgent.logger.log(Level.FINER, "Successfully instrumented method exit for {0}.{1}", new Object[]{this.className, this.methodName});
        }
    }

    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
    }

    public void visitEnd() {
        super.visitEnd();
        this.mv.visitAnnotation(Type.getDescriptor(InstrumentedMethod.class), true);
    }

    private void onFinally(int opcode) {
        Label doneLabel = new Label();
        loadLocal(this.tracerVarId);
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
        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_INTERNAL_NAME, HANDLE_NAME, HANDLE_METHOD_DESC);
        loadLocal(this.tracerVarId);
        loadLocal(th);
        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, HANDLER_INTERNAL_NAME, "exitTracker", "(Lcom/sinosoft/monitor/agent/trackers/Tracker;Ljava/lang/Throwable;)V");
    }

    private void exitNormally(int opcode) {
        Type returnType = Type.getReturnType(this.methodDesc);
        int returnVar = -1;
        JavaAgent.logger.log(Level.FINER, "The return type for the method desc {0} is {1}", new Object[]{this.methodDesc, returnType});

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
        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, AGENT_INTERNAL_NAME, HANDLE_NAME, HANDLE_METHOD_DESC);
        loadLocal(this.tracerVarId);
        this.mv.visitIntInsn(Opcodes.SIPUSH, opcode);
        if (opcode == Opcodes.RETURN) {
            loadNull();
        } else {
            loadLocal(returnVar);
        }
        this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, HANDLER_INTERNAL_NAME, "exitTracker", "(Lcom/sinosoft/monitor/agent/trackers/Tracker;ILjava/lang/Object;)V");
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
            loadNull();
            JavaAgent.logger.finest("Default block is called");
        }
        return returnType;
    }

    public void loadNull() {
        this.mv.visitInsn(1);
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        super.visitMethodInsn(opcode, owner, name, desc);
        axis2AbstractHttpSenderListener.visitMethodInsn(this.className, this.methodName,
                opcode, owner, name,abstractHTTPSenderSetTimeoutsInserter);
    }
}
