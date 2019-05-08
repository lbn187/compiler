package com.nasm;
//Multiply rax and src as unsigned integers, and put the result in rax.  High 64 bits of product (usually zero) go into rdx.
public class Mul extends Inst{
    public Var src;
    public Mul(Var s){
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
