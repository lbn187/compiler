package com.Type;

public class NullType extends Type {
    public NullType(){
        super();
        typename="null";
        size=8;
    }
    public boolean equals(Type a){
        if(a instanceof NullType)return true;
        return false;
    }
    public String toString(){
        return "null";
    }
}
