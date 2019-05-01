package com.IR;

public class StringData extends StaticData{
    public String value;
    public StringData(String s){
        super("str");
        value=s;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
