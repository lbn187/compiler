package com.IR;

public class VirtualRegister extends Register{
    public int id;
    public VirtualRegister(String s,int v){
        super("%"+s+"_"+String.valueOf(v));
        id=v;
    }
}
