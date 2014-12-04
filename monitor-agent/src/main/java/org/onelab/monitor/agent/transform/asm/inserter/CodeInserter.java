package org.onelab.monitor.agent.transform.asm.inserter;

import org.objectweb.asm.MethodVisitor;

/**
 * 方法插入器，作用域为指定方法
 * 注意：todo
 * Created by chunliangh on 14-12-3.
 */
public abstract class CodeInserter {
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
    public CodeInserter(String owner, String name, String desc, int index) {
        if(index<1 || owner==null || name==null || desc==null){
            throw new IllegalArgumentException();
        }
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.index = index;
    }

    public void visit(MethodVisitor mv,int opcode,String owner,String name,String desc,boolean itf){
        beforeMethodInsn(mv);
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
        afterMethodInsn(mv);
    }

    abstract protected void beforeMethodInsn(MethodVisitor mv);
    abstract protected void afterMethodInsn(MethodVisitor mv);

    public boolean match(String owner,String name,String desc){
        if (!this.owner.equals(owner)
                || !this.name.equals(name) ||!this.desc.equals(desc)) {
            return false;
        }
        currIndex++;
        if (index==currIndex) return true;
        return false;
    }
}
