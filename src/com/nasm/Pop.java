package com.nasm;
import java.util.List;
import java.util.ArrayList;
//Remove topmost value from the stack.  Equivalent to "mov dest, [rsp]; add 8,rsp"
public class Pop extends Inst{
    public VReg dest;
    public Pop(VReg d){
        dest=d;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        list.add(dest);
        return list;
    }
}