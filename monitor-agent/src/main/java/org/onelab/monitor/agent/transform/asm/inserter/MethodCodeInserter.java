package org.onelab.monitor.agent.transform.asm.inserter;

import org.objectweb.asm.MethodVisitor;

/**
 * 方法插入器，作用域为指定方法
 * Created by chunliangh on 14-12-3.
 */
public abstract class MethodCodeInserter {
    private String owner;
    private String name;
    private String desc;
    private int index;

    private int currIndex;

    /**
     *
     * @param owner
     * @param name
     * @param desc
     * @param index 从1开始
     */
    public MethodCodeInserter(String owner, String name, String desc, int index) {
        if(index<1 || owner==null || name==null || desc==null){
            throw new IllegalArgumentException();
        }
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.index = index;
    }

    public void visit(MethodVisitor mv,String owner,String name,String desc){
        if (match(owner,name,desc)){
            insert(mv);
        }
    }

    abstract protected void insert(MethodVisitor mv);

    private boolean match(String owner,String name,String desc){
        if (!this.owner.equals(owner)
                || !this.name.equals(name) ||!this.desc.equals(desc)) {
            return false;
        }
        currIndex++;
        if (index==currIndex) return true;
        return false;
    }
}
