package com.nasm;

public class Nop extends Inst {
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
