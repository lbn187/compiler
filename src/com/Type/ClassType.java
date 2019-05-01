package com.Type;

public class ClassType extends Type {
    public ClassType(){
        super();
        size=4;
    }
    public ClassType(String s){
        super();
        typename=s;
        size=4;
    }
    public boolean equals(Type a){
        if((a instanceof ClassType)&&typename.equals(a.typename))return true;
        return false;
    }
    public String toString(){
        return "class";
    }
}
