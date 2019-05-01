package com.IR;
//$dest = alloc $size
public class Allocation extends IRInst {
    public Register dest;
    public int size;
    public Allocation(IRBlock blk,Register reg,int sz){
        super(blk);
        dest=reg;
        size=sz;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
