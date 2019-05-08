package com.nasm;

abstract public class Inst {
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
