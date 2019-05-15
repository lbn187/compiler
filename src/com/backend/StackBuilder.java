package com.backend;
import com.nasm.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import static com.nasm.RegConst.*;

public class StackBuilder implements NasmVisitor {
    public Func func;
    public LinkedList<Inst> oldInstList;
    public LinkedList<Inst> newInstList;
    public int rbpOffset = 0;

    public static void visit(Nasm nasm) {
        for (Func func : nasm.Functions) {
            StackBuilder builder = new StackBuilder(func);
            builder.work();
        }
    }

    public StackBuilder(Func func) {
        this.func = func;
    }

    public void work() {
        for (Block block : func.Blocks) {
            oldInstList = block.Insts;
            newInstList = new LinkedList<>();
            if (block.EntryTest()==true) {
                //System.out.println("FFFFFFFFF "+ block.name+ " offset:"+func.RspOffset);
                int rspOffset = func.RspOffset;
                if (rspOffset % 16 != 0) {
                    rspOffset += 8;
                }
                newInstList.add(new Push(rbp));
                newInstList.add(new Mov(rbp, rsp));
                if (rspOffset != 0) {
                    newInstList.add(new BinOp("sub", rsp, new Imm(rspOffset)));
                }
            }
            for (Inst inst : oldInstList) visit(inst);
            //System.out.println("BLOCKNAME: "+ block.name+" SIZE:"+newInstList.size());
            block.Insts=newInstList;
        }
    }

    public void settle(Var var) {
        if (!(var instanceof Memory)) return;
        Memory memory = (Memory) var;
        if (memory.ok==true) return;
        rbpOffset -= 8;
        memory.PushInStack(rbpOffset);
    }

    public void visit(Inst inst) {
        inst.accept(this);
    }

    public void visit(BinOp inst) {
        settle(inst.dest);
        settle(inst.src);
        newInstList.add(inst);
    }
    public void visit(Cmp inst) {
        settle(inst.a);
        settle(inst.b);
        newInstList.add(inst);
    }

    public void visit(Cqo inst) {
        newInstList.add(inst);
    }

    public void visit(CallFunc inst) {
        newInstList.add(inst);
        int rspOffset = inst.offset;
        if (rspOffset != 0) {
            newInstList.add(new BinOp("add", rsp, new Imm(rspOffset)));
        }

    }

    public void visit(IDiv inst) {
        settle(inst.src);
        newInstList.add(inst);
    }

    public void visit(Jmp inst) {
        newInstList.add(inst);
    }

    public void visit(Mov inst) {
        settle(inst.dest);
        settle(inst.src);
        newInstList.add(inst);
    }

    public void visit(Movzx inst) {
        // src is always rax(al)
        newInstList.add(inst);
    }

    public void visit(Nop inst) {
        newInstList.add(new BinOp("sub", rsp, new Imm(8)));
    }

    public void visit(Pop inst) {
        newInstList.add(inst);
    }

    public void visit(Push inst) {
        settle(inst.src);
        newInstList.add(inst);
    }

    public void visit(Ret inst) {
        newInstList.add(new Mov(rsp, rbp));
        newInstList.add(new Pop(rbp));
        newInstList.add(inst);
    }

    public void visit(SetFlag inst) {
        // dst is always rax(al)
        newInstList.add(inst);
    }

    public void visit(Shift inst) {
        // second is imm or rcx(cl)
        settle(inst.var);
        newInstList.add(inst);
    }

    public void visit(UniOp inst) {
        settle(inst.dest);
        newInstList.add(inst);
    }

}
