package com.nasm;
import java.util.List;
import java.util.ArrayList;
public class Func {
    public String name;
    public List<Block>Blocks=new ArrayList<>();
    public int RspOffset;
    public Func(String s){
        name=s;
    }
}
