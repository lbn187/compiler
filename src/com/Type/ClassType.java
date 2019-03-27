package com.Type;

public class ClassType extends Type {
    public ClassType(){
        super();
    }
    public ClassType(String s){
        super();
        typename=s;
    }
    public boolean equals(Type a){
        if((a instanceof ClassType)&&typename.equals(a.typename))return true;
        return false;
    }
    public String toString(){
        return "class";
    }
}
