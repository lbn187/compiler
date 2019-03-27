package com.Type;

public class BoolType extends Type {
    public BoolType(){
        super();
        typename="bool";
    }
    public boolean equals(Type a){
        if(a instanceof BoolType)return true;
        return false;
    }
    public String toString(){
        return "bool";
    }
}
