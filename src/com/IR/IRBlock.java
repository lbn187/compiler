package com.IR;

public class IRBlock {
    public IRInst head=null;
    public IRInst tail=null;
    public String name;
    public Function function;
    boolean endflag=false;
    public IRBlock(Function func,String s){
        function=func;
        name=s;
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    public void add(IRInst inst) {
        if(endflag==true){
            System.out.println("cannot add inst after a basic block end");
            assert false;
        }
        if(tail==null)head=tail=inst;else{
            tail.LinkNext(inst);
            tail=inst;
        }
    }
    public void addend(IRInst inst){
        add(inst);
        endflag=true;
        if(inst instanceof Branch){

        }else if(inst instanceof Jump){

        }else if(inst instanceof Return){

        }else{
            System.out.println("Exception:addend inst is not branchinst");
        }
    }
}
