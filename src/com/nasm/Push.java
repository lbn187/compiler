package com.nasm;
//Insert a value onto the stack.  Useful for passing arguments, saving registers, etc.
public class Push extends Inst{
    public Var src;
    public Push(Var s){
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
