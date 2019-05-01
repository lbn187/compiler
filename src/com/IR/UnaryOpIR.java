package com.IR;
//dest=operation value
public class UnaryOpIR extends IRInst {
    public Register dest;
    public Value value;
    public String operation;
    public UnaryOpIR(IRBlock blk,Register reg,String ope,Value val){
        super(blk);
        dest=reg;
        operation=ope;
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
