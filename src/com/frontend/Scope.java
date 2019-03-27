package com.frontend;
import com.Type.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
public class Scope{
    public List<Scope> son;
    public Scope father;
    public Map<String,Type> map;
    public String name;
    public boolean classflag;
    public Scope(Scope fa,String s,boolean flag){
        son=new ArrayList<Scope>();
        map=new Hashtable<String,Type>();
        father=fa;
        name=s;
        classflag=flag;
    }
    public boolean add(String str,Type id){
        if(map.get(str)!=null)return false;
        map.put(str,id);
        return true;
    }
    public Type get(String str){
        if(map.get(str)!=null)return map.get(str);
        if(father!=null)return father.get(str);
        return null;
    }
    public Scope getscope(String str){
        if(map.get(str)!=null)return this;
        if(father!=null)return father.getscope(str);
        return null;
    }
    public Scope addson(){
        Scope tmp=new Scope(this,name+"."+Integer.toString(son.size()),classflag);
        son.add(tmp);
        return tmp;
    }
}
