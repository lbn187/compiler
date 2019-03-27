package com.Type;

abstract public class Type {
    public String typename;
    public Type(){
        typename="";
    }
    public abstract boolean equals(Type a);
    public String toString(){
        return "type";
    }
}
