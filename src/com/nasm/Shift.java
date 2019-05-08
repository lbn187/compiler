package com.nasm;
//Bitshift a value right by a constant, or the low 8 bits of rcx ("cl").
//Shift count MUST go in rcx, no other register will do!
//op var,bits
public class Shift extends Inst{
    public String op;
    public Var var;
    public Var bits;
    public Shift(String o,Var v,Var b){
        op=o;
        var=v;
        bits=b;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
