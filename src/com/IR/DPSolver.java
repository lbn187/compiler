package com.IR;

public class DPSolver {
    public void run(IRRoot node) {
        for (GlobalRegister globalReg : node.globalregisters) {
            jump();
        }
        for (StringData stringData : node.strings.values()) {
            jump();
        }
        for (Function function : node.functions.values()) {
            FunctionSolve(function);
        }
    }
    public void jump(){

    }
    public void FunctionSolve(Function function){
        boolean flag=true;
        for(IRBlock block=function.head;block!=null;block=block.next){
            Judge(block);
        }
        if(flag==true){
            AddGlobalVeg();
        }else return;
    }
    public void Judge(IRBlock block){
        for(IRInst inst=block.head;inst!=null;inst=inst.next){
            Judge(inst);
        }
    }
    public void Judge(IRInst inst){

    }
    public void AddGlobalVeg(){
        int dpnum=200;
    }
}
