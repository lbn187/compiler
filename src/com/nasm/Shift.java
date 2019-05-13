package com.nasm;

import java.util.ArrayList;
import java.util.List;

//Bitshift a value right by a constant, or the low 8 bits of rcx ("cl").
//Shift count MUST go in rcx, no other register will do!
//op var,bits
public class Shift extends Inst{
    public String op;
    public Var var;
    public Var bits;
    public Shift(String o,Var v,Var b){
        op=o;
        var=v;
        bits=b;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        if(var instanceof VReg)list.add((VReg)var);
        return list;
    }
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        if(var instanceof VReg)list.add((VReg)var);
        if(bits instanceof VReg)list.add((VReg)bits);
        return list;
    }
}
