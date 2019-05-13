package com.nasm;

import java.util.ArrayList;
import java.util.List;

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
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        if(a instanceof VReg)list.add((VReg)a);
        if(b instanceof VReg)list.add((VReg)b);
        return list;
    }
}
