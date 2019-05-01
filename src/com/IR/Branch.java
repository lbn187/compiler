package com.IR;
//if(flag)trueblock;else falseblock
public class Branch extends BranchIR {
    public Value flag;
    public IRBlock trueblock;
    public IRBlock falseblock;
    public Branch(IRBlock blk,Value f,IRBlock truev,IRBlock falsev){
        super(blk);
        flag=f;
        trueblock=truev;
        falseblock=falsev;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
