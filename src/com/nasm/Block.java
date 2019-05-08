package com.nasm;
import java.util.List;
import java.util.ArrayList;
public class Block {
    public String name;
    public List<Inst>Insts=new ArrayList<>();
    public Block(String s){
        name=s;
    }
}
