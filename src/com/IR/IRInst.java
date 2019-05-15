package com.IR;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

abstract public class IRInst {
    public IRBlock curblock;
    public IRInst pre;
    public IRInst next;
    boolean deleteflag=false;
 //   public List<Register>registerlist=new ArrayList<>();//used register
//    public List<Value>valuelist=new ArrayList<>();//used value
    public IRInst(IRBlock cur,IRInst prev,IRInst nextv){
        curblock=cur;
        pre=prev;
        next=nextv;
    }
    public IRInst(IRBlock cur){
        curblock=cur;
    }
    public void LinkNext(IRInst nextv){
        nextv.pre=this;
        nextv.next=this.next;
        if(this.next!=null)this.next.pre=nextv;
        this.next=nextv;
    }
    public void LinkPre(IRInst prev){
        prev.pre=this.pre;
        prev.next=this;
        if(this.pre!=null)this.pre.next=prev;
        this.pre=prev;
    }
    public void PreAppend(IRInst v){//Add v in front
        LinkPre(v);
        if(this==curblock.head)curblock.head=v;
    }
    public void Append(IRInst v){//Add v behind
        LinkNext(v);
        if(this==curblock.tail)curblock.tail=v;
    }
    public abstract void accept(IRVisitor visitor);
}