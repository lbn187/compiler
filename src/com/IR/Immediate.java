package com.IR;

public class Immediate extends Value {
    public int value;
    public Immediate(int val){
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
