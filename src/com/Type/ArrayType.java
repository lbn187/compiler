package com.Type;

public class ArrayType extends Type {
    public Type basetype;
    public Type deeptype;
    public ArrayType(){
        super();
        size=4;
    }
    public ArrayType(Type type,int dimention){
        this.deeptype=type;
        if(dimention==1){
            basetype=type;
        }else{
            basetype=new ArrayType(type,dimention-1);
        }
        this.typename=basetype.typename+"[]";
        size=4;
    }
    public boolean equals(Type a){
        if(!(a instanceof ArrayType))return false;
        return basetype.equals(((ArrayType)a).basetype);
    }
    public String toString(){
        return "array";
    }
}
