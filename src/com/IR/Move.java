package com.IR;
//dest=value
public class Move extends IRInst {
    public Register dest;
    public Value value;
    public Move(IRBlock blk,Register reg,Value val){
        super(blk);
        dest=reg;
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
