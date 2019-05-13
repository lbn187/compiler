package com.nasm;

import java.util.ArrayList;
import java.util.List;

abstract public class Inst {
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        return new ArrayList<>();
    }
    public List<VReg> CalUse(){
        return new ArrayList<>();
    }
}
