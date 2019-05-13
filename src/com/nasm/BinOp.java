package com.nasm;

import java.util.ArrayList;
import java.util.List;

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
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        if(dest instanceof VReg)list.add((VReg)dest);
        return list;
    }
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        if(dest instanceof VReg)list.add((VReg)dest);
        if(src instanceof VReg)list.add((VReg)src);
        return list;
    }
}
