package com.IR;

public class GlobalRegister extends Register{
    public GlobalRegister(String s){
        super("@"+s);
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
