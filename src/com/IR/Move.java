package com.IR;
//dest=value
public class Move extends IRInst {
    public VirtualRegister dest;
    public Value value;
    public Move(IRBlock blk,VirtualRegister reg,Value val){
        super(blk);
        dest=reg;
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
