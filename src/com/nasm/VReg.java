package com.nasm;

public class VReg extends Var{
    public String name;
    public int PReg;
    public boolean PrecolorFlag;
    public boolean TinyFlag;
    public VReg(String s){
        name=s;
    }
    public VReg(String s,int pid){
        name=s;
        PReg=pid;
        PrecolorFlag=true;
    }
    public VReg(String s,boolean tf){
        name=s;
        TinyFlag=tf;
    }
    public String toString(){
        return name;
    }
}