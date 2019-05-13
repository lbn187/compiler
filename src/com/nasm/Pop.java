package com.nasm;
import java.util.List;
import java.util.ArrayList;
//Remove topmost value from the stack.  Equivalent to "mov dest, [rsp]; add 8,rsp"
public class Pop extends Inst{
    public Var dest;
    public Pop(Var d){
        dest=d;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List list=new ArrayList<>();
        list.add(dest);
        return list;
    }
}