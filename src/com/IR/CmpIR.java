package com.IR;

public class CmpIR extends IRInst {
    public Register dest;
    public Value lvalue;
    public Value rvalue;
    public String operator;
    public CmpIR(IRBlock blk,Register reg,String ope,Value lhs,Value rhs){
        super(blk);
        dest=reg;
        operator=ope;
        lvalue=lhs;
        rvalue=rhs;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
