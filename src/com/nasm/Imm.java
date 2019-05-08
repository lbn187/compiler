package com.nasm;

public class Imm extends Var{
    public int value;
    public Imm(int v){
        value=v;
    }
    public String toString(){
        return Integer.toString(value);
    }
}
