package com.IR;

public class VirtualRegister extends Register{
    String name;
    public VirtualRegister(String s){
        name=s;
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
