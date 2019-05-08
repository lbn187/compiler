package com.IR;
//$dest = alloc $size
public class Allocation extends IRInst {
    public VirtualRegister dest;
    public int size;
    public Allocation(IRBlock blk,VirtualRegister reg,int sz){
        super(blk);
        dest=reg;
        size=sz;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
