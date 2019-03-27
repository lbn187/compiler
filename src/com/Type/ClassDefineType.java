package com.Type;
import java.util.Map;
import java.util.Hashtable;
public class ClassDefineType extends Type {
    Map<String,Type> map;
    public ClassDefineType(){
        super();
        map=new Hashtable<String,Type>();
    }
    public ClassDefineType(Map<String,Type>mp){
        super();
        map=mp;
    }
    public void add(String s,Type tp){
        map.put(s,tp);
    }
    public Type get(String s){
        return map.get(s);
    }
}
