package com.Type;

public class IntType extends Type {
    public IntType(){
        super();
        typename="int";
    }
    public boolean equals(Type a){
        if(a instanceof IntType)return true;
        return false;
    }
    public String toString(){
        return "integer";
    }
}

