package com.nasm;
import java.util.List;
import java.util.ArrayList;
import static com.nasm.RegConst.*;
public class CallFunc extends Inst{
    public String name;
    public int offset;
    public CallFunc(String s){
        name=s;
        offset=0;
    }
    public CallFunc(String s,int off){
        name=s;
        offset=off;
    }
    public void accept(NasmVisitor visitor){
        visitor.visit(this);
    }
    public List<VReg> CalDefine(){
        List<VReg> list=new ArrayList<>();
        for(int i=0;i<9;i++)
            list.add(VRegs.get(CallerRegs[i]));
        return list;
    }
}
