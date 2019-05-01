package com.IR;
//return value;
public class Return extends BranchIR {
    public Value value;
    public Return(IRBlock blk,Value val){
        super(blk);
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
