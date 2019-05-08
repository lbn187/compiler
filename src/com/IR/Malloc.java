package com.IR;

public class Malloc extends IRInst{
    public VirtualRegister dest;
    public Value size;
    public Malloc(IRBlock blk,VirtualRegister reg,Value sz){
        super(blk);
        dest=reg;
        size=sz;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
