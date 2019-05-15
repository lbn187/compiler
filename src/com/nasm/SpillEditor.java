package com.nasm;
import java.util.*;
import static com.nasm.RegConst.*;
public class SpillEditor implements NasmVisitor {
    public Map<VReg, Memory> spillMap = new HashMap<>();
    public List<VReg> newTemps = new ArrayList<>();
    public LinkedList<Inst> newinsts=new LinkedList<>();
    public int regCounter = 0;
    public SpillEditor(Set<VReg> spilledNodes) {
        for (VReg reg : spilledNodes) {
            spillMap.put(reg,new Memory());
        }
    }
    public List<VReg> visit(Func func) {
        for (Block block : func.Blocks){
            for (Inst inst:block.Insts)
                visit(inst);
            block.Insts=newinsts;
        }
        return newTemps;
    }
    public void visit(Inst inst) {
        inst.accept(this);
    }
    public VReg NewTmpReg() {
        ++regCounter;
        VReg tmp = new VReg("%tmp_"+regCounter,true);
        newTemps.add(tmp);
        return tmp;
    }
    public boolean SpillJudge(Var var) {
        if (var instanceof VReg&&spillMap.keySet().contains(var))return true;
        return false;
    }
    public void editImul(BinOp inst) {
        Var dest=inst.dest;
        boolean lf=SpillJudge(dest);
        Var src=inst.src;
        boolean rf=SpillJudge(src);
        if(lf==true&&rf==true){
            VReg tmp = NewTmpReg();
            newinsts.add(new Mov(tmp,spillMap.get(dest)));
            inst.dest=tmp;
            inst.src=spillMap.get(src);
            newinsts.add(inst);
            newinsts.add(new Mov(spillMap.get(dest),tmp));
        }else if(lf==true){
            VReg tmp = NewTmpReg();
            newinsts.add(new Mov(tmp,spillMap.get(dest)));
            inst.dest=tmp;
            newinsts.add(inst);
            newinsts.add(new Mov(spillMap.get(dest),tmp));
        }else if(rf==true){
            inst.src=spillMap.get(src);
            newinsts.add(inst);
        }else{
            newinsts.add(inst);
        }
    }
    public void visit(BinOp inst) {
        if (inst.op.equals("imul")) {
            editImul(inst);
            return;
        }
        Var dest=inst.dest;
        boolean lf=SpillJudge(dest);
        Var src=inst.src;
        boolean rf=SpillJudge(src);
        if(lf==true&&rf==true){
            VReg tmp = NewTmpReg();
            newinsts.add(new Mov(tmp,spillMap.get(src)));
            inst.dest=spillMap.get(dest);
            inst.src=tmp;
            newinsts.add(inst);
        } else if(lf==true){
            if (src instanceof Memory){
                VReg tmp=NewTmpReg();
                newinsts.add(new Mov(tmp,spillMap.get(dest)));
                inst.dest=tmp;
                newinsts.add(inst);
                newinsts.add(new Mov(spillMap.get(dest),tmp));
            } else {
                inst.dest=spillMap.get(dest);
                newinsts.add(inst);
            }
        } else if(rf==true){
            if(dest instanceof Memory){
                VReg tmp=NewTmpReg();
                newinsts.add(new Mov(tmp,spillMap.get(src)));
                inst.src=tmp;
                newinsts.add(inst);
            } else{
                inst.src=spillMap.get(src);
                newinsts.add(inst);
            }
        } else{
            newinsts.add(inst);
        }
    }
    public void visit(Cmp inst) {
        Var a=inst.a;
        boolean lf=SpillJudge(a);
        Var b=inst.b;
        boolean rf=SpillJudge(b);
        if (lf==true&&rf==true) {
            VReg tmp=NewTmpReg();
            newinsts.add(new Mov(tmp,spillMap.get(b)));
            inst.a=spillMap.get(a);
            inst.b=tmp;
            newinsts.add(inst);
        } else if(lf==true){
            if (inst.b instanceof Memory) {
                VReg tmp = NewTmpReg();
                newinsts.add(new Mov(tmp, spillMap.get(a)));
                inst.a=tmp;
                newinsts.add(inst);
            }else{
                inst.a=spillMap.get(a);
                newinsts.add(inst);
            }
        } else if(rf==true){
            if(inst.a instanceof Memory){
                VReg tmp = NewTmpReg();
                newinsts.add(new Mov(tmp, spillMap.get(b)));
                inst.b=tmp;
                newinsts.add(inst);
            }else{
                inst.b=spillMap.get(b);
                newinsts.add(inst);
            }
        }else{
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
        if(SpillJudge(inst.src)==true)inst.src=spillMap.get(inst.src);
        newinsts.add(inst);
    }
    public void visit(Jmp inst) {
        newinsts.add(inst);
    }
    public void editMemory(Memory memory) {
        if ((memory.ok==false) || memory.label != null) return;
        assert !spillMap.keySet().contains(rbp);
        VReg base = memory.base;
        VReg index = memory.index;
        assert base != null;
        if (spillMap.keySet().contains(base)) {
            VReg tmp = NewTmpReg();
            newinsts.add(new Mov(tmp, spillMap.get(base)));
            memory.base=tmp;
        }
        if (index != null && spillMap.keySet().contains(index)) {
            VReg tmp = NewTmpReg();
            newinsts.add(new Mov(tmp, spillMap.get(index)));
            memory.index=tmp;
        }
    }
    public void visit(Mov inst) {
        Var dest=inst.dest;
        boolean lf=SpillJudge(dest);
        Var src=inst.src;
        boolean rf=SpillJudge(src);
        if(lf==true&&rf==true){
            VReg tmp=NewTmpReg();
            newinsts.add(new Mov(tmp,spillMap.get(src)));
            inst.dest=spillMap.get(dest);
            inst.src=tmp;
            newinsts.add(inst);
        } else {
            if(inst.dest instanceof Memory) {
                editMemory((Memory) inst.dest);
            }
            if(inst.src instanceof Memory){
                editMemory((Memory) inst.src);
            }
            if(lf==true){
                if(inst.src instanceof Memory) {
                    VReg tmp = NewTmpReg();
                    inst.dest=tmp;
                    newinsts.add(inst);
                    newinsts.add(new Mov(spillMap.get(dest), tmp));
                } else{
                    inst.dest=spillMap.get(dest);
                    newinsts.add(inst);
                }
            } else if(rf==true){
                if (inst.dest instanceof Memory) {
                    VReg tmp = NewTmpReg();
                    newinsts.add(new Mov(tmp, spillMap.get(src)));
                    inst.src=tmp;
                    newinsts.add(inst);
                } else{
                    inst.src=spillMap.get(src);
                    newinsts.add(inst);
                }
            } else {
                newinsts.add(inst);
            }
        }
    }
    public void visit(Movzx inst) {
        VReg dst=inst.dest;
        if (spillMap.keySet().contains(dst)) {
            VReg tmp=NewTmpReg();
            inst.dest=tmp;
            newinsts.add(inst);
            newinsts.add(new Mov(spillMap.get(dst), tmp));
        } else{
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
        if(SpillJudge(inst.src))inst.src=spillMap.get(inst.src);
        newinsts.add(inst);
    }

    public void visit(Ret inst) {
        newinsts.add(inst);
    }

    public void visit(SetFlag inst) {
        newinsts.add(inst);
    }

    public void visit(Shift inst) {
        if(SpillJudge(inst.var))inst.var=spillMap.get(inst.var);
        newinsts.add(inst);
    }
    public void visit(UniOp inst) {
        if(SpillJudge(inst.dest))inst.dest=spillMap.get(inst.dest);
        newinsts.add(inst);
    }
}