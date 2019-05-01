package com.IR;
//store size $address $value offset    // M[$addr+offset : $address+offset+size-1] <- $value
public class Store extends IRInst {
    public int size;
    public Value address;
    public int offset;
    public Value value;
    //isstaticdata
    public Store(IRBlock blk,int sz,Value addr,int off,Value val){
        super(blk);
        size=sz;
        address=addr;
        offset=off;
        value=val;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}