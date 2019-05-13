package com.nasm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.nasm.RegConst.*;
public class Cqo extends Inst{
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        list.add(rax);
        list.add(rdx);
        return list;
    }
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        list.add(rax);
        return list;
    }
}
