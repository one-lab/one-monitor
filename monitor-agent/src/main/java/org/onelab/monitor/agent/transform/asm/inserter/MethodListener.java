package org.onelab.monitor.agent.transform.asm.inserter;

import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-12-3.
 */
public class MethodListener {

    private String targetType;
    private String targetMethod;
    private String targetMethodDesc;
    private List<MethodCodeInserter> inserters;
    private static List<MethodListener> listeners;

    private MethodListener(String targetType, String targetMethod, String targetMethodDesc, List<MethodCodeInserter> inserters) {
        this.targetType = targetType;
        this.targetMethod = targetMethod;
        this.targetMethodDesc = targetMethodDesc;
        this.inserters = inserters;
    }

    public void onVisitMethodInsn(MethodVisitor mv,String owner,String name,String desc){
        if (match(owner,name,desc)){
            for (MethodCodeInserter inserter:inserters){
                inserter.visit(mv,owner,name,desc);
            }
        }
    }

    public static void addListener(String targetType, String targetMethod, String targetMethodDesc, MethodCodeInserter... inserters){
        if (listeners == null){
            listeners = new LinkedList<MethodListener>();
        }
        listeners.add(new MethodListener(targetType, targetMethod, targetMethodDesc, Arrays.asList(inserters)));
    }

    public static List<MethodListener> list(){
        return listeners;
    }

    private boolean match(String owner,String name,String desc){
        if (!targetType.equals(owner)
                || !targetMethod.equals(name) ||!targetMethodDesc.equals(desc)) {
            return false;
        }
        return true;
    }
}
