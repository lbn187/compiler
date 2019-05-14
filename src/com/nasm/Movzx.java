package com.nasm;

import java.util.ArrayList;
import java.util.List;

public class Movzx extends Inst{
    public VReg dest;
    public Var src;
    public Movzx(VReg d,Var s){
        dest=d;
        src=s;
    }
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        list.add((VReg)dest);
        return list;
    }
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        if(src instanceof VReg)list.add((VReg)src);
        return list;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
}
