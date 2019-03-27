package com.Type;

public class StringType extends ClassType {
    public StringType(){
        super();
        typename="string";
    }
    public boolean equals(Type a){
        if(a instanceof StringType)return true;
        return false;
    }
    public String toString(){
        return "string";
    }
}
