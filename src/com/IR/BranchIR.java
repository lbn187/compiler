package com.IR;

abstract public class BranchIR extends IRInst{
    public BranchIR(IRBlock cur,IRInst prev,IRInst nextv){
        super(cur,prev,nextv);
    }
    public BranchIR(IRBlock cur){
        super(cur);
    }
    public abstract void accept(IRVisitor visitor);
}
