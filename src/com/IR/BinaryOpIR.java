package com.IR;
//rt=lv op rv
public class BinaryOpIR extends IRInst{
    public VirtualRegister dest;
    public Value lvalue;
    public Value rvalue;
    public String operator;
    public BinaryOpIR(IRBlock blk,VirtualRegister reg,String ope,Value lhs,Value rhs){
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