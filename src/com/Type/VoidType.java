package com.Type;

public class VoidType extends Type{
    public VoidType(){
        super();
        typename="void";
    }
    public boolean equals(Type a){
        if(a instanceof VoidType)return true;
        return false;
    }
    public String toString(){
        return "void";
    }
}
