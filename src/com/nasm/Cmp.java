package com.nasm;
//Compare two values.  Sets flags that are used by the conditional jumps (below).
public class Cmp extends Inst{
    public Var a;
    public Var b;
    public Cmp(Var av,Var bv){
        a=av;
        b=bv;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
