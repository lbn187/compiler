package com.nasm;

//Goto the instruction label:.  Skips anything else in the way.
//op label
//include jmp and jl
public class Jmp extends Inst{
    String op;
    public Label label;
    public Jmp(String o,Label l){
        op=o;
        label=l;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}