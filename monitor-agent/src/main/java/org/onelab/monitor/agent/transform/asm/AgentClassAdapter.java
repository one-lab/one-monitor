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

    public AgentClassAdapter(ClassVisitor cv, String className, String supperName, String[] interfaces) {
        super(Opcodes.ASM4, cv);
        this.className = className;
        this.supperName = supperName;
        this.interfaces = interfaces;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, description, signature, exceptions);
        if(!MethodMatcher.match(className,name,description,access)){
            return mv;
        }
//        if (className.equals("com/jumei/pic/demo/controller/PicController")){
//            System.out.println("=========================com/jumei/pic/demo/controller/PicController");
//        }
        String pointCutName = Aop.getPointCutName(className,supperName,interfaces,name,description);
        return new AgentMethodAdapter(pointCutName,true,className,mv,access,name,description);
    }

    public void visitEnd() {
        super.visitEnd();
        this.cv.visitAnnotation(Type.getDescriptor(TransformedClass.class), true);
    }
}
