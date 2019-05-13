package com.nasm;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
public class Block {
    public String name;
    public LinkedList<Inst>Insts=new LinkedList<>();
    public Set<Block>Succs=new HashSet<>();
    public Set<VReg>Dies=new HashSet<>();
    public Block(String s){
        name=s;
    }
    public boolean EntryTest(){
        if(name.startsWith("__L_"))return false;
        return true;
    }
    public boolean ReturnTest(){
        if(Insts.isEmpty())return false;
        if(Insts.getLast() instanceof Ret)return true;
        return false;
    }
}
