package com.backend;
import com.nasm.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import static com.nasm.RegConst.*;

public class StackBuilder implements NasmVisitor{
    public Func func;
    public LinkedList<Inst> newinsts;
    public int rbpOffset=0;

    public static void visit(Nasm nasm){
        for (Func func:nasm.Functions){
            StackBuilder builder=new StackBuilder(func);
            builder.work();
        }
    }
    public StackBuilder(Func func){
        this.func=func;
    }
    public void work(){
        for (Block block:func.Blocks){
            newinsts=new LinkedList<>();
            if (block.EntryTest()==true){
                //System.out.println("FFFFFFFFF "+ block.name+ " offset:"+func.RspOffset);
                int rspOffset=func.RspOffset;
                if(rspOffset%16!=0)rspOffset+=8;
                newinsts.add(new Push(rbp));
                newinsts.add(new Mov(rbp, rsp));
                if(rspOffset!=0)newinsts.add(new BinOp("sub", rsp, new Imm(rspOffset)));
            }
            for(Inst inst:block.Insts)visit(inst);
            //System.out.println("BLOCKNAME: "+ block.name+" SIZE:"+newinsts.size());
            block.Insts=newinsts;
        }
    }
    public void solve(Var var){
        if (!(var instanceof Memory))return;
        Memory memory=(Memory)var;
        if (memory.ok==true)return;
        rbpOffset-=8;
        memory.PushInStack(rbpOffset);
    }
    public void visit(Inst inst){
        inst.accept(this);
    }

    public void visit(BinOp inst){
        solve(inst.dest);
        solve(inst.src);
        newinsts.add(inst);
    }
    public void visit(CallFunc inst){
        newinsts.add(inst);
        int rspOffset=inst.offset;
        if (rspOffset!=0)newinsts.add(new BinOp("add", rsp, new Imm(rspOffset)));
    }
    public void visit(Cmp inst){
        solve(inst.a);
        solve(inst.b);
        newinsts.add(inst);
    }
    public void visit(Cqo inst){
        newinsts.add(inst);
    }
    public void visit(IDiv inst){
        solve(inst.src);
        newinsts.add(inst);
    }
    public void visit(Jmp inst){
        newinsts.add(inst);
    }
    public void visit(Mov inst){
        solve(inst.dest);
        solve(inst.src);
        newinsts.add(inst);
    }
    public void visit(Movzx inst){
        newinsts.add(inst);
    }
    public void visit(Nop inst){
        newinsts.add(new BinOp("sub", rsp, new Imm(8)));
    }
    public void visit(Pop inst){
        newinsts.add(inst);
    }
    public void visit(Push inst){
        solve(inst.src);
        newinsts.add(inst);
    }
    public void visit(Ret inst){
        newinsts.add(new Mov(rsp, rbp));
        newinsts.add(new Pop(rbp));
        newinsts.add(inst);
    }
    public void visit(SetFlag inst){
        newinsts.add(inst);
    }
    public void visit(Shift inst){
        solve(inst.var);
        newinsts.add(inst);
    }
    public void visit(UniOp inst){
        solve(inst.dest);
        newinsts.add(inst);
    }
}