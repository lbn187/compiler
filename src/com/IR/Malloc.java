package com.IR;

public class Malloc extends IRInst{
    Register dest;
    Value size;
    public Malloc(IRBlock blk,Register reg,Value sz){
        super(blk);
        dest=reg;
        size=sz;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
