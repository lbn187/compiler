package com.IR;
import java.util.List;
import java.util.ArrayList;
//dest=function(args)
public class Call extends IRInst{
    public Register dest;
    public Function function;
    public List<Value> args=new ArrayList<>();
    public Call(IRBlock blk,Register reg,Function func,List<Value> list){
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
