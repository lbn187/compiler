package com.nasm;

import java.util.ArrayList;
import java.util.List;
import static com.nasm.RegConst.*;
//Divide rax by src, and put the ratio into rax, and the remainder into rdx.
//Bizarrely, on input rdx must be zero, or you get a SIGFPE.
public class IDiv extends Inst{
    public Var src;
    public IDiv(Var s){
        src=s;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg>list=new ArrayList<>();
        list.add(rax);
        list.add(rdx);
        return list;
    }
    public List<VReg> CalUse(){
        List<VReg>list=new ArrayList<>();
        list.add(rax);
        list.add(rdx);
        if(src instanceof VReg)list.add((VReg)src);
        return list;
    }
}
