package com.IR;

import java.util.HashSet;
import java.util.Set;

public class IRBlock {
    public IRInst head=null;
    public IRInst tail=null;
    public String name;
    public Function function;
    public IRBlock pre;
    public IRBlock next;
    public int id;
    boolean endflag=false;
    boolean deleteflag=false;
    public IRBlock(Function func,String s,int v){
        function=func;
        name=s;
        id=v;
        //function.Append(this);
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public void add(IRInst inst) {
        if(endflag==true){
            System.out.println("cannot add inst after a basic block end");
            assert false;
        }
        if(inst instanceof BranchIR)endflag=true;
        if(tail==null)head=tail=inst;else{
            tail.LinkNext(inst);
            tail=inst;
        }
    }
    public void addfront(IRInst inst){
        if(head==null)head=tail=inst;else{
            inst.LinkNext(head);
            head=inst;
        }
    }
    /*public void addend(IRInst inst){
        add(inst);
        endflag=true;
        if(inst instanceof Branch){

        }else if(inst instanceof Jump){

        }else if(inst instanceof Return){

        }else{
            System.out.println("Exception:addend inst is not branchinst");
        }
    }*/
    public void Delete(IRBlock v){//delete v from list
        if(deleteflag==true)return;
        deleteflag=true;
        //if(v instanceof BranchIR)curblock.DeleteEnd();
        if(pre!=null)pre.next=next;else function.head=next;
        if(next!=null)next.pre=pre;else function.tail=pre;
    }
    public void AddNext(IRBlock blk){
        blk.pre=this;
        blk.next=next;
        if(next!=null)next.pre=blk;
        next=blk;
        if(this==function.tail)function.tail=blk;
    }
    public boolean ishead(){
        if(function.head==this)return true;
        return false;
    }
    public Set<IRBlock> GetSuccs() {
        Set<IRBlock> succs = new HashSet<>();
        if (tail instanceof Jump) {
            succs.add(((Jump) tail).target);
        }
        if (tail instanceof Branch) {
            succs.add(((Branch) tail).trueblock);
            succs.add(((Branch) tail).falseblock);
        }
        // every basicBlock's tail is a BranchInst
        return succs;
    }
}
