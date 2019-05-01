package com.IR;

import com.AST.VariableDefNode;

import java.util.ArrayList;
import java.util.List;
import com.Type.*;
public class Function {
    public List<VirtualRegister>args=new ArrayList<>();
    public String name;
    public IRBlock startblock;
    public IRBlock endblock;
    public Type type;
    public int size;
    public Function(String s, Type tp, int sz, List<VariableDefNode> variables){
        name=s;
        type=tp;
        size=sz;
        for(VariableDefNode o:variables)
            args.add(new VirtualRegister(o.name));
        startblock=new IRBlock(this,name+".entry");
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}