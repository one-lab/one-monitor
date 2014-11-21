package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.*;
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
            String pointCutName = CategoryMatcher.getPointCutName(className, supperName, interfaces, name, description);
            return new AgentMethodAdapter(pointCutName,true,className,mv,access,name,description);
        }
    }

    public void visitEnd() {
        super.visitEnd();
        if (!hasTransformedClass){
            super.visitAnnotation(Type.getDescriptor(TransformedClass.class), true);
        }
    }
}
