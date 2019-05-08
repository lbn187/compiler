package com.nasm;

public class VReg extends Var{
    public String name;
    public int PReg;
    public VReg(String s){
        name=s;
    }
    public VReg(String s,int pid){
        name=s;
        PReg=pid;
    }
    public String toString(){
        return name;
    }
}