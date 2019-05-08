package com.nasm;
//Pop the return program counter, and jump there.  Ends a subroutine.
public class Ret extends Inst{
    public Ret(){

    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
