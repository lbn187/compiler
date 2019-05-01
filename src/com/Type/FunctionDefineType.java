package com.Type;
import com.Type.Type;
import java.util.List;
import java.util.ArrayList;
public class FunctionDefineType extends Type{
    public Type variable;
    public List<Type> parameters;
    public FunctionDefineType(){
        super();
        size=0;
    }
    public FunctionDefineType(Type v){
        super();
        variable=v;
        parameters=new ArrayList<Type>();
        size=0;
    }
    public FunctionDefineType(Type v,Type... params){
        super();
        variable=v;
        parameters=new ArrayList<Type>();
        for(int i=0;i<params.length;i++)parameters.add(params[i]);
        size=0;
    }
    public void add(Type param){
        parameters.add(param);
    }
    public boolean equals(Type a){
        if(!(a instanceof FunctionDefineType))return false;
        FunctionDefineType b=(FunctionDefineType)a;
        if(!(variable).equals(b.variable))return false;
        if(parameters.size()!=(b.parameters.size()))return false;
        for(int i=0;i<parameters.size();i++)
            if(!parameters.get(i).equals(b.parameters.get(i)))return false;
        return true;
    }
    public String toString(){
        return "function";
    }
}