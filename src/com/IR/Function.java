package com.IR;

import com.AST.VariableDefNode;

import java.util.ArrayList;
import java.util.List;
import com.Type.*;
public class Function {
    public List<VirtualRegister>args=new ArrayList<>();
    public IRBlock head;
    public IRBlock tail;
    public String name;
    public int VirtualRegisterCnt=0;
    public int BlockCnt=0;
    public boolean flag;
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public Function(String s,boolean initflag){
        name=s;
        flag=initflag;
        if(initflag==false){
            head=tail=AddBlock(".entry");
        }
    }
    public VirtualRegister AddVirtualRegister(String s){
        VirtualRegister register=new VirtualRegister(s,++VirtualRegisterCnt);
        return register;
    }
    public IRBlock AddBlock(String s){
        IRBlock blk=new IRBlock(this,s,++BlockCnt);
        return blk;
    }
    public void Append(IRBlock v){
        tail.AddNext(v);
    }
}