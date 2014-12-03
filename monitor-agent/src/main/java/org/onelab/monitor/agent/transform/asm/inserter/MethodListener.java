package org.onelab.monitor.agent.transform.asm.inserter;

import org.objectweb.asm.MethodVisitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-12-3.
 */
public class MethodListener {

    private String owner;
    private String name;
    private String desc;
    private List<MethodCodeInserter> inserters;
    private static List<MethodListener> listeners;

    private MethodListener(String owner, String name, String desc, List<MethodCodeInserter> inserters) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.inserters = inserters;
    }

    public void onVisitMethodInsn(MethodVisitor mv,String owner,String name,String desc){
        if (match(owner,name,desc)){
            for (MethodCodeInserter inserter:inserters){
                inserter.visit(mv,owner,name,desc);
            }
        }
    }

    public static void addListener(String owner, String name, String desc, MethodCodeInserter... inserters){
        if (listeners == null){
            listeners = new LinkedList<MethodListener>();
        }
        listeners.add(new MethodListener(owner, name, desc, Arrays.asList(inserters)));
    }

    public static List<MethodListener> list(){
        return listeners;
    }

    private boolean match(String owner,String name,String desc){
        if (!this.owner.equals(owner)
                || !this.name.equals(name) ||!this.desc.equals(desc)) {
            return false;
        }
        return true;
    }
}
