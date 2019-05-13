package com.nasm;

import java.util.ArrayList;
import java.util.List;

//Insert a value onto the stack.  Useful for passing arguments, saving registers, etc.
public class Push extends Inst{
    public Var src;
    public Push(Var s){
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalUse() {
        List<VReg> list = new ArrayList<>();
        if (src instanceof VReg) list.add((VReg) src);
        return list;
    }
}
