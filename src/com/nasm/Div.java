package com.nasm;
//Divide rax by src, and put the ratio into rax, and the remainder into rdx.
//Bizarrely, on input rdx must be zero, or you get a SIGFPE.
public class Div extends Inst{
    public Var src;
    public Div(Var s){
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
