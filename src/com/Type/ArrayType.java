package com.Type;

public class ArrayType extends Type {
    public Type basetype;
    public Type deeptype;
    public ArrayType(){
        super();
    }
    public ArrayType(Type type,int dimention){
        deeptype=type;
        if(dimention==1){
            basetype=type;
        }else{
            basetype=new ArrayType(type,dimention-1);
        }
        this.typename=basetype.typename+"[]";
    }
    public String toString(){
        return "array";
    }
}
