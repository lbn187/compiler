package com.nasm;
import java.util.ArrayList;
import java.util.List;

public class SetFlag extends Inst {
    public String name;
    public VReg dest;
    public SetFlag(String s,VReg d){
        name=s;
        dest=d;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg>list=new ArrayList<>();
        list.add(dest);
        return list;
    }
}