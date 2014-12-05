package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.*;
import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.config.Commons;
import org.onelab.monitor.agent.transform.matcher.CategoryMatcher;
import org.onelab.monitor.agent.transform.TransformedClass;
import org.onelab.monitor.agent.transform.matcher.method.MethodMatcher;

/**
 * monitor-agent类适配器
 * Created by chunliangh on 14-11-13.
 */
public class AgentClassAdapter extends ClassVisitor {
    private String className;
    private String supperName;
    private String[] interfaces;

    private boolean hasTransformedClass = false;

    public AgentClassAdapter(ClassVisitor cv, String className, String supperName, String[] interfaces) {
        super(Opcodes.ASM4, cv);
        this.className = className;
        this.supperName = supperName;
        this.interfaces = interfaces;
        Agent.logger.info("class:"+className+" is being transformed...");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.contains(Commons.AGENT_TRANSFORMED_CLASS)){
            hasTransformedClass = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, description, signature, exceptions);
        if(!MethodMatcher.match(className,name,description,access)){
            return mv;
        }else{
            int flag = CategoryMatcher.getPointCutName(className, supperName, interfaces, name, description);
            return new AgentMethodAdapter(flag,className,mv,access,name,description);
        }
    }

    public void visitEnd() {
        super.visitEnd();
        if (!hasTransformedClass){
            super.visitAnnotation(Type.getDescriptor(TransformedClass.class), true);
            Agent.logger.info("class:"+className+" be transformed success.");
        }
    }
}
