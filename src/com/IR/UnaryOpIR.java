package com.IR;
//dest=operation value
public class UnaryOpIR extends IRInst {
    public VirtualRegister dest;
    public Value value;
    public String operator;
    public UnaryOpIR(IRBlock blk,VirtualRegister reg,String ope,Value val){
        super(blk);
        dest=reg;
        operator=ope;
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
