package com.IR;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

abstract public class IRInst {
    public IRBlock curblock;
    public IRInst pre=null;
    public IRInst next=null;
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
        next=nextv;
        nextv.pre=this;
    }
    public void LinkPre(IRInst prev){
        pre=prev;
        prev.next=this;
    }
    public void PreAppend(IRInst v){//Add v in front
        if(pre==null){
            curblock.head=v;
        }else{
            pre.LinkNext(v);
        }
        v.LinkNext(this);
    }
    public void Append(IRInst v){//Add v behind
        if(next==null){
            curblock.tail=v;
        }else{
            next.LinkPre(v);
        }
        v.LinkPre(this);
    }
    public void Delete(IRInst v){//delete v from list
        if(deleteflag==true)return;
        deleteflag=true;
        //if(v instanceof BranchIR)curblock.DeleteEnd();
        if(pre!=null)pre.next=next;else curblock.head=next;
        if(next!=null)next.pre=pre;else curblock.tail=pre;
    }
    public abstract void accept(IRVisitor visitor);
}