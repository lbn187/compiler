package com.nasm;

public class Cqo extends Inst{
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
