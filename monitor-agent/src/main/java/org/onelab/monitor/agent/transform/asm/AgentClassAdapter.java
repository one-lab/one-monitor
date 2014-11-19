package org.onelab.monitor.agent.transform.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.onelab.monitor.agent.transform.Aop;
import org.onelab.monitor.agent.transform.TransformedClass;
import org.onelab.monitor.agent.transform.matcher.MethodMatcher;

/**
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
        System.out.println(desc);
        if (desc.contains("Lorg/onelab/monitor/agent/transform/TransformedClass;")){
            hasTransformedClass = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
        System.out.println("visitMethod:" + name + ",description:" + description);
        MethodVisitor mv = super.visitMethod(access, name, description, signature, exceptions);
        if(!MethodMatcher.match(hasTransformedClass,className,name,description,access)){
            return mv;
        }
        String pointCutName = Aop.getPointCutName(className,supperName,interfaces,name,description);
        AgentMethodAdapter amv = new AgentMethodAdapter(pointCutName,true,className,mv,access,name,description);
        JSRInlinerAdapter jsrInlinerAdapter = new JSRInlinerAdapter(amv, access, name, description, signature, exceptions);
        return jsrInlinerAdapter;
    }

    public void visitEnd() {
        super.visitEnd();
        if (!hasTransformedClass){
            this.cv.visitAnnotation(Type.getDescriptor(TransformedClass.class), true);
        }
    }
}
