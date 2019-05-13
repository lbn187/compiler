package com.nasm;
import java.util.List;
import java.util.ArrayList;
//Move data between registers, load immediate data into registers, move data between registers and memory.
public class Mov extends Inst{
    public Var dest;
    public Var src;
    public Mov(Var d,Var s){
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
        if(dest instanceof Memory)list.addAll(((Memory)dest).CalUse());
        if(src instanceof VReg)list.add((VReg)src);
        if(src instanceof Memory)list.addAll(((Memory)src).CalUse());
        return list;
    }
}
