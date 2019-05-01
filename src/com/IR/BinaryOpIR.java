package com.IR;
//rt=lv op rv
public class BinaryOpIR extends IRInst{
    public Register dest;
    public Value lvalue;
    public Value rvalue;
    public String operation;
    public BinaryOpIR(IRBlock blk,Register reg,String ope,Value lhs,Value rhs){
        super(blk);
        dest=reg;
        operation=ope;
        lvalue=lhs;
        rvalue=rhs;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}