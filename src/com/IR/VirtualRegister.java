package com.IR;

public class VirtualRegister extends Register{
    public int id;
    public VirtualRegister(String s,int v){
        super("%"+s+"_"+v);
        id=v;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
