package com.nasm;

public class CallFunc extends Inst{
    public String name;
    public int offset;
    public CallFunc(String s){
        name=s;
        offset=0;
    }
    public CallFunc(String s,int off){
        name=s;
        offset=off;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
