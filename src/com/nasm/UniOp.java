package com.nasm;
import java.util.List;
import java.util.ArrayList;
public class UniOp extends Inst{
    public String op;
    public Var dest;
    public UniOp(String s,Var d){
        op=s;
        dest=d;
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
        return list;
    }
}
