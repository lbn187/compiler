package com.IR;
import com.Type.FunctionDefineType;

import java.util.List;
import java.util.ArrayList;
//dest=function(args)
public class Call extends IRInst{
    public VirtualRegister dest;
    public FunctionDefineType function;
    public List<Value> args=new ArrayList<>();
    public Call(IRBlock blk,VirtualRegister reg,FunctionDefineType func,List<Value> list){
        super(blk);
        dest=reg;
        function=func;
        args=list;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
