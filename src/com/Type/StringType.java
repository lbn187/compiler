package com.Type;

public class StringType extends Type {
    public StringType(){
        super();
        typename="string";
        size=8;
    }
    public boolean equals(Type a){
        if(a instanceof StringType)return true;
        return false;
    }
    public String toString(){
        return "string";
    }
}
