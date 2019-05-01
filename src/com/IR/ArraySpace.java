package com.IR;

public class ArraySpace extends StaticData{
    public int length;
    public ArraySpace(String s,int len){
        super(s);
        length=len;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
