package com.nasm;

public class IDiv extends Inst{
    public Var divisor;
    public IDiv(Var div){
        divisor=div;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
