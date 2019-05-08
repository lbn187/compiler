package com.nasm;
//Remove topmost value from the stack.  Equivalent to "mov dest, [rsp]; add 8,rsp"
public class Pop extends Inst{
    public Var dest;
    public Pop(Var d){
        dest=d;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
