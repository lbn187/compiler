package com.IR;
//jump target
public class Jump extends BranchIR{
    public IRBlock target;
    public Jump(IRBlock blk,IRBlock to){
        super(blk);
        target=to;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
