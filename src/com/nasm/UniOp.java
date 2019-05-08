package com.nasm;

public class UniOp extends Inst{
    public String op;
    public Var dest;
    public UniOp(String s,Var d){
        op=s;
        dest=d;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
