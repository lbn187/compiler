package com.Type;

abstract public class Type {
    public int size;
    public String typename;
    public Type(){
        typename="";
    }
    public abstract boolean equals(Type a);
    public String toString(){
        return "type";
    }
}
