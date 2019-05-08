package com.nasm;
//op dest src        dest=dest op src
public class BinOp extends Inst{
    public String op;
    public Var dest;
    public Var src;
    public BinOp(String o,Var d,Var s){
        op=o;
        dest=d;
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
