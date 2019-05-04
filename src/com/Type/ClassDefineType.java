package com.Type;
import java.util.Map;
import java.util.Hashtable;
import com.frontend.Information;
public class ClassDefineType extends Type {
    Map<String,Information> map;
    public ClassDefineType(){
        super();
        map=new Hashtable<String,Information>();
        size=0;
    }
    public ClassDefineType(Map<String,Information>mp){
        super();
        map=mp;
        size=0;
    }
    public void add(String s,Information tp){
        map.put(s,tp);
    }
    public void add(String s,Type tp){
        Information info=new Information(tp,0,true);//???
        map.put(s,info);
    }
    public Information get(String s){
        return map.get(s);
    }
    public boolean equals(Type a){
        return false;
    }
}
