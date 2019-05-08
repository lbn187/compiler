package com.IR;
//$dest = load size $address offset  // $dest <- M[$addr+offset : $addr+offset+size-1]

//dest=V[address]
public class Load extends IRInst {
    public VirtualRegister dest;
    //public int size;
    public Value address;
    //public int offset;
    /*
    public Load(IRBlock blk,Register reg,int sz,Value addr,int off){
        super(blk);
        dest=reg;
        size=sz;
        address=addr;
        offset=off;
    }*/
    public Load(IRBlock blk,VirtualRegister reg,Value addr){
        super(blk);
        dest=reg;
        address=addr;
    }
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
