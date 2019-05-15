package com.nasm;
import java.util.*;

import static com.nasm.RegConst.*;

public class SpillEditor implements NasmVisitor {

    private Map<VReg, Memory> spillMap = new HashMap<>();
    private List<VReg> newTemps = new ArrayList<>();
    private LinkedList<Inst> oldInstList;
    private LinkedList<Inst> newInstList;
    private int regCounter = 0;

    public SpillEditor(Set<VReg> spilledNodes) {
        for (VReg reg : spilledNodes) {
            spillMap.put(reg, new Memory());
        }
    }

    public List<VReg> visit(Func func) {
        for (Block block : func.Blocks) {
            oldInstList = block.Insts;
            newInstList = new LinkedList<>();
            for (Inst inst : oldInstList) {
                visit(inst);
            }
            block.Insts=newInstList;
        }
        return newTemps;
    }

    public void visit(Inst inst) {
        inst.accept(this);
    }

    private VReg makeTmpReg() {
        ++regCounter;
        VReg tmp = new VReg("%tmp_" + regCounter, true);
        newTemps.add(tmp);
        return tmp;
    }

    private VReg needSpill(Var var) {
        if (var instanceof VReg && spillMap.keySet().contains(var)) {
            return (VReg) var;
        }
        return null;
    }

    public void editImul(BinOp inst) {
        VReg first = needSpill(inst.dest);
        VReg second = needSpill(inst.src);
        if (first != null && second != null) {
            // ===================
            // imul ar br
            // ===================
            // mov tmp [am]
            // imul tmp [bm]
            // mov [am] tmp
            // ===================
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(first)));
            inst.dest=tmp;
            inst.src=spillMap.get(second);
            newInstList.add(inst);
            newInstList.add(new Mov(spillMap.get(first), tmp));
        } else if (first != null) {
            // ===================
            // imul ar ?
            // ===================
            // mov tmp [am]
            // imul tmp ?
            // mov [am] tmp
            // ===================
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(first)));
            inst.dest=tmp;
            newInstList.add(inst);
            newInstList.add(new Mov(spillMap.get(first), tmp));
        } else if (second != null) {
            // ===================
            // imul ar br
            // ===================
            // imul ar [bm]
            // ===================
            inst.src=spillMap.get(second);
            newInstList.add(inst);
        } else {
            newInstList.add(inst);
        }
    }

    public void visit(BinOp inst) {
        if (inst.op.equals("imul")) {
            editImul(inst);
            return;
        }
        VReg first = needSpill(inst.dest);
        VReg second = needSpill(inst.src);
        if (first != null && second != null) {
            // ===================
            // add ar br
            // ======== 1 ========
            // mov tmp [am]
            // add tmp [bm]
            // mov [am] tmp
            // ======== 2 ========
            // mov tmp [bm]
            // add [am] tmp
            // ===================
            // For now I choose 2
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(second)));
            inst.dest=spillMap.get(first);
            inst.src=tmp;
            newInstList.add(inst);
        } else if (first != null) {
            if (inst.src instanceof Memory) {
                // ====================
                // add ar [ ]
                // ====================
                // mov tmp [am]
                // add tmp [ ]
                // mov [am] tmp
                // ====================
                VReg tmp = makeTmpReg();
                newInstList.add(new Mov(tmp, spillMap.get(first)));
                inst.dest=tmp;
                newInstList.add(inst);
                newInstList.add(new Mov(spillMap.get(first), tmp));
            } else {
                // ====================
                // add ar br
                // ====================
                // add [am] br
                // ====================
                inst.dest=spillMap.get(first);
                newInstList.add(inst);
            }
        } else if (second != null) {
            if (inst.dest instanceof Memory) {
                // ====================
                // add [ ] br
                // ====================
                // mov tmp [bm]
                // add [ ] tmp
                // ====================
                VReg tmp = makeTmpReg();
                newInstList.add(new Mov(tmp, spillMap.get(second)));
                inst.src=tmp;
                newInstList.add(inst);
            } else {
                // ====================
                // add ar br
                // ====================
                // add ar [bm]
                // ====================
                inst.src=spillMap.get(second);
                newInstList.add(inst);
            }
        } else {
            newInstList.add(inst);
        }
    }

    public void visit(Cmp inst) {
        VReg lhs = needSpill(inst.a);
        VReg rhs = needSpill(inst.b);
        if (lhs != null && rhs != null) {
            // ===================
            // cmp ar br
            // ===================
            // mov tmp [bm]
            // cmp [am] tmp
            // ===================
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(rhs)));
            inst.a=spillMap.get(lhs);
            inst.b=tmp;
            newInstList.add(inst);
        } else if (lhs != null) {
            if (inst.b instanceof Memory) {
                // ===================
                // cmp ar [ ]
                // ===================
                // mov tmp [am]
                // cmp tmp [ ]
                // ===================
                VReg tmp = makeTmpReg();
                newInstList.add(new Mov(tmp, spillMap.get(lhs)));
                inst.a=tmp;
                newInstList.add(inst);
            } else {
                // ===================
                // cmp ar br
                // ===================
                // cmp [am] br
                // ===================
                inst.a=spillMap.get(lhs);
                newInstList.add(inst);
            }
        } else if (rhs != null) {
            if (inst.a instanceof Memory) {
                // ===================
                // cmp [ ] br
                // ===================
                // mov tmp [bm]
                // cmp [ ] tmp
                // ===================
                VReg tmp = makeTmpReg();
                newInstList.add(new Mov(tmp, spillMap.get(rhs)));
                inst.b=tmp;
                newInstList.add(inst);
            } else {
                // ===================
                // cmp ar br
                // ===================
                // cmp ar [bm]
                // ===================
                inst.b=spillMap.get(rhs);
                newInstList.add(inst);
            }
        } else {
            newInstList.add(inst);
        }
    }

    public void visit(Cqo inst) {
        newInstList.add(inst);
    }

    public void visit(CallFunc inst) {
        newInstList.add(inst);
    }

    public void visit(IDiv inst) {
        VReg divisor = needSpill(inst.src);
        if (divisor != null) {
            inst.src=spillMap.get(divisor);
        }
        newInstList.add(inst);
    }

    public void visit(Jmp inst) {
        newInstList.add(inst);
    }

    private void editMemory(Memory memory) {
        if ((memory.ok==false) || memory.label != null) return;
        assert !spillMap.keySet().contains(rbp);
        VReg base = memory.base;
        VReg index = memory.index;
        assert base != null;
        if (spillMap.keySet().contains(base)) {
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(base)));
            memory.base=tmp;
        }
        if (index != null && spillMap.keySet().contains(index)) {
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(index)));
            memory.index=tmp;
        }
    }

    public void visit(Mov inst) {
        VReg dst = needSpill(inst.dest);
        VReg src = needSpill(inst.src);
        if (dst != null && src != null) {
            // ===================
            // mov ar br
            // ===================
            // mov tmp [bm]
            // mov [am] tmp
            // ===================
            VReg tmp = makeTmpReg();
            newInstList.add(new Mov(tmp, spillMap.get(src)));
            inst.dest=spillMap.get(dst);
            inst.src=tmp;
            newInstList.add(inst);
        } else {
            if (inst.dest instanceof Memory) {
                editMemory((Memory) inst.dest);
            }
            if (inst.src instanceof Memory) {
                editMemory((Memory) inst.src);
            }
            if (dst != null) {
                if (inst.src instanceof Memory) {
                    // ===================
                    // mov ar [ ]
                    // ===================
                    // mov tmp [ ]
                    // mov [am] tmp
                    // ===================
                    VReg tmp = makeTmpReg();
                    inst.dest=tmp;
                    newInstList.add(inst);
                    newInstList.add(new Mov(spillMap.get(dst), tmp));
                } else {
                    // ===================
                    // mov ar br
                    // ===================
                    // mov [am] br
                    // ===================
                    inst.dest=spillMap.get(dst);
//                    if (newInstList == null) {
//                        System.out.println("newInstList is null");
//                    }
                    newInstList.add(inst);
                }
            } else if (src != null) {
                if (inst.dest instanceof Memory) {
                    // ===================
                    // mov [ ] br
                    // ===================
                    // mov tmp [bm]
                    // mov [ ] tmp
                    // ===================
                    VReg tmp = makeTmpReg();
                    newInstList.add(new Mov(tmp, spillMap.get(src)));
                    inst.src=tmp;
                    newInstList.add(inst);
                } else {
                    // ===================
                    // mov ar br
                    // ===================
                    // mov ar [bm]
                    // ===================
                    inst.src=spillMap.get(src);
                    newInstList.add(inst);
                }
            } else {
                newInstList.add(inst);
            }
        }
    }


    public void visit(Movzx inst) {
        // src is always rax(al)
        //assert inst.src instanceof VReg && physicalRegMap.values().contains(inst.getSrc());
        VReg dst = inst.dest;
        if (spillMap.keySet().contains(dst)) {
            VReg tmp = makeTmpReg();
            inst.dest=tmp;
            newInstList.add(inst);
            newInstList.add(new Mov(spillMap.get(dst), tmp));
        } else {
            newInstList.add(inst);
        }
    }

    public void visit(Nop inst) {
        newInstList.add(inst);
    }

    public void visit(Pop inst) {
        // In phase of register allocation, there is no pop.
        newInstList.add(inst);
    }

    public void visit(Push inst) {
        VReg src = needSpill(inst.src);
        if (src != null) {
            inst.src=spillMap.get(src);
        }
        newInstList.add(inst);
    }

    public void visit(Ret inst) {
        newInstList.add(inst);
    }

    public void visit(SetFlag inst) {
        // dst is always rax(al)
        //assert physicalRegMap.values().contains(inst.dest);
        newInstList.add(inst);
    }

    public void visit(Shift inst) {
        // second is imm or rcx(cl)
        //assert inst.getSecond() instanceof Imm ||
        //       (inst.getSecond() instanceof VReg && physicalRegMap.values().contains(inst.getSecond()));
        VReg first = needSpill(inst.var);
        if (first != null) {
            inst.var=spillMap.get(first);
        }
        newInstList.add(inst);
    }

    // inc qword [rel a]
    // this is ok
    public void visit(UniOp inst) {
        VReg var = needSpill(inst.dest);
        if (var != null) {
            inst.dest=spillMap.get(var);
        }
        newInstList.add(inst);
    }

}
