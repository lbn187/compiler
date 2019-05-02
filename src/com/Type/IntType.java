package com.Type;

public class IntType extends Type {
    public IntType(){
        super();
        typename="int";
        size=4;
    }
    public boolean equals(Type a){
        if(a instanceof IntType)return true;
        return false;
    }
    public String toString(){
        return "integer";
    }
}

