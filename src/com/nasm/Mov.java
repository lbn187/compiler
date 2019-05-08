package com.nasm;
//Move data between registers, load immediate data into registers, move data between registers and memory.
public class Mov extends Inst{
    public Var dest;
    public Var src;
    public Mov(Var d,Var s){
        dest=d;
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
