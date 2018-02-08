package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.onelab.monitor.agent.log.AgentLogger;
import org.onelab.monitor.agent.transform.matcher.MethodMatcher;

/**
 * monitor-agent类适配器
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassAdapter extends ClassVisitor {
    private String className;

    public AgentClassAdapter(ClassVisitor cv, String className) {
        super(Opcodes.ASM4, cv);
        this.className = className;
//        Agent.sys.info("type : "+className+" is being transformed...");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String description, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, description, signature, exceptions);
        if(MethodMatcher.match(className,name,description,access)){
            return new JSRInlinerAdapter(
                new AgentMethodAdapter(className,mv,access,name,description),
                access,
                name,
                description,
                signature,
                exceptions
            );
        }
        return mv;
    }

    public void visitEnd() {
        super.visitEnd();
        // 给被处理的类加标记
//        super.visitAnnotation(Type.getDescriptor(TransformedClass.class), true);
        AgentLogger.asm.info("T " + className);
    }
}
