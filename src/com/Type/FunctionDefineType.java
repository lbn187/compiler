package com.Type;
import com.Type.Type;
import java.util.List;
import java.util.ArrayList;
public class FunctionDefineType extends Type{
    public Type variable;
    public List<Type> parameters;
    public FunctionDefineType(){
        super();
    }
    public FunctionDefineType(Type v){
        super();
        variable=v;
        parameters=new ArrayList<Type>();
    }
    public FunctionDefineType(Type v,Type... params){
        super();
        variable=v;
        parameters=new ArrayList<Type>();
        for(int i=0;i<params.length;i++)parameters.add(params[i]);
    }
    public void add(Type param){
        parameters.add(param);
    }
    public String toString(){
        return "function";
    }
}