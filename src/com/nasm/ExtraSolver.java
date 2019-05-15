package com.nasm;
import java.util.*;
import static com.nasm.RegConst.*;
public class ExtraSolver implements NasmVisitor {
    private Map<VReg, Memory> ExtraMap=new HashMap<>();
    private List<VReg> NewRegs=new ArrayList<>();
    private LinkedList<Inst>newinsts;
    private int regCounter=0;
    public ExtraSolver(Set<VReg> spilledNodes) {
        for (VReg reg : spilledNodes) {
            ExtraMap.put(reg, new Memory());
        }
    }
    public List<VReg> visit(Func func) {
        for (Block block:func.Blocks) {
            newinsts = new LinkedList<>();
            for (Inst inst:block.Insts)
                visit(inst);
            block.Insts=newinsts;
        }
        return NewRegs;
    }
    public void visit(Inst inst) {
        inst.accept(this);
    }
    private VReg AddNewReg() {
        ++regCounter;
        VReg tmp = new VReg("%Makenew_"+regCounter,true);
        NewRegs.add(tmp);
        return tmp;
    }
    private boolean ExtraJudge(Var var) {
        if (var instanceof VReg && ExtraMap.keySet().contains(var))return true;
        return false;
    }
    public void visit(BinOp inst) {
        Var first=inst.dest;
        Var second=inst.src;
        if(inst.op.equals("imul")){
            if(ExtraJudge(first)&&ExtraJudge(second)){
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(first)));
                inst.dest=tmp;
                inst.src=ExtraMap.get(second);
                newinsts.add(inst);
                newinsts.add(new Mov(ExtraMap.get(first), tmp));
            } else if(ExtraJudge(first)){
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(first)));
                inst.dest=tmp;
                newinsts.add(inst);
                newinsts.add(new Mov(ExtraMap.get(first), tmp));
            } else if(ExtraJudge(second)){
                inst.src=ExtraMap.get(second);
                newinsts.add(inst);
            } else{
                newinsts.add(inst);
            }
            return;
        }
        if(ExtraJudge(first)&&ExtraJudge(second)){
            VReg tmp = AddNewReg();
            newinsts.add(new Mov(tmp, ExtraMap.get(second)));
            inst.dest=ExtraMap.get(first);
            inst.src=tmp;
            newinsts.add(inst);
        } else if(ExtraJudge(first)){
            if (inst.src instanceof Memory) {
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(first)));
                inst.dest=tmp;
                newinsts.add(inst);
                newinsts.add(new Mov(ExtraMap.get(first), tmp));
            } else {
                inst.dest=ExtraMap.get(first);
                newinsts.add(inst);
            }
        } else if(ExtraJudge(second)){
            if (inst.dest instanceof Memory) {
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(second)));
                inst.src=tmp;
                newinsts.add(inst);
            } else {
                inst.src=ExtraMap.get(second);
                newinsts.add(inst);
            }
        } else{
            newinsts.add(inst);
        }
    }

    public void visit(Cmp inst) {
        Var lhs=inst.a;
        Var rhs=inst.b;
        if(ExtraJudge(lhs)&&ExtraJudge(rhs)){
            VReg tmp = AddNewReg();
            newinsts.add(new Mov(tmp, ExtraMap.get(rhs)));
            inst.a=ExtraMap.get(lhs);
            inst.b=tmp;
            newinsts.add(inst);
        } else if(ExtraJudge(lhs)) {
            if (inst.b instanceof Memory) {
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(lhs)));
                inst.a=tmp;
                newinsts.add(inst);
            } else {
                inst.a=ExtraMap.get(lhs);
                newinsts.add(inst);
            }
        } else if(ExtraJudge(rhs)){
            if (inst.a instanceof Memory) {
                VReg tmp = AddNewReg();
                newinsts.add(new Mov(tmp, ExtraMap.get(rhs)));
                inst.b=tmp;
                newinsts.add(inst);
            } else {
                inst.b=ExtraMap.get(rhs);
                newinsts.add(inst);
            }
        } else{
            newinsts.add(inst);
        }
    }

    public void visit(Cqo inst) {
        newinsts.add(inst);
    }

    public void visit(CallFunc inst) {
        newinsts.add(inst);
    }

    public void visit(IDiv inst) {
        if(ExtraJudge(inst.src))inst.src=ExtraMap.get(inst.src);
        newinsts.add(inst);
    }

    public void visit(Jmp inst) {
        newinsts.add(inst);
    }

    private void CalMem(Memory memory) {
        if ((memory.ok==false) || memory.label != null) return;
        assert !ExtraMap.keySet().contains(rbp);
        VReg base = memory.base;
        VReg index = memory.index;
        assert base != null;
        if (ExtraMap.keySet().contains(base)) {
            VReg tmp = AddNewReg();
            newinsts.add(new Mov(tmp, ExtraMap.get(base)));
            memory.base=tmp;
        }
        if (index != null&&ExtraMap.keySet().contains(index)) {
            VReg tmp = AddNewReg();
            newinsts.add(new Mov(tmp, ExtraMap.get(index)));
            memory.index=tmp;
        }
    }

    public void visit(Mov inst) {
        Var dest=inst.dest;
        Var src=inst.src;
        if(ExtraJudge(dest)&&ExtraJudge(src)){
            VReg tmp = AddNewReg();
            newinsts.add(new Mov(tmp, ExtraMap.get(src)));
            inst.dest=ExtraMap.get(dest);
            inst.src=tmp;
            newinsts.add(inst);
        } else {
            if (dest instanceof Memory) {
                CalMem((Memory)dest);
            }
            if (src instanceof Memory) {
                CalMem((Memory)src);
            }
            if (ExtraJudge(dest)){
                if (src instanceof Memory) {
                    VReg tmp = AddNewReg();
                    inst.dest=tmp;
                    newinsts.add(inst);
                    newinsts.add(new Mov(ExtraMap.get(dest), tmp));
                } else{
                    inst.dest=ExtraMap.get(dest);
                    newinsts.add(inst);
                }
            } else if(ExtraJudge(src)){
                if (inst.dest instanceof Memory) {
                    VReg tmp = AddNewReg();
                    newinsts.add(new Mov(tmp, ExtraMap.get(src)));
                    inst.src=tmp;
                    newinsts.add(inst);
                } else {
                    inst.src=ExtraMap.get(src);
                    newinsts.add(inst);
                }
            } else {
                newinsts.add(inst);
            }
        }
    }
    public void visit(Movzx inst) {
        VReg dst = inst.dest;
        if (ExtraMap.keySet().contains(dst)) {
            VReg tmp = AddNewReg();
            inst.dest=tmp;
            newinsts.add(inst);
            newinsts.add(new Mov(ExtraMap.get(dst), tmp));
        } else {
            newinsts.add(inst);
        }
    }
    public void visit(Nop inst) {
        newinsts.add(inst);
    }
    public void visit(Pop inst) {
        newinsts.add(inst);
    }
    public void visit(Push inst) {
        if(ExtraJudge(inst.src))
            inst.src=ExtraMap.get(inst.src);
        newinsts.add(inst);
    }

    public void visit(Ret inst) {
        newinsts.add(inst);
    }

    public void visit(SetFlag inst) {
        newinsts.add(inst);
    }

    public void visit(Shift inst) {
        if(ExtraJudge(inst.var))
            inst.var=ExtraMap.get(inst.var);
        newinsts.add(inst);
    }
    public void visit(UniOp inst) {
        if(ExtraJudge(inst.dest))
            inst.dest=ExtraMap.get(inst.dest);
        newinsts.add(inst);
    }

}