package com.IR;

abstract public class StaticData extends Register{
    public String name;
    public StaticData(String s){
        name=s;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
