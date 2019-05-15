package com.backend;
import com.IR.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionInliner {
    // Map<originFunc, backupFunc>
    private Map<Function, Function> funcBackupMap = new HashMap<>();

    public FunctionInliner(IRRoot irroot) {
        for (Function originFunc : irroot.functions.values()) {
            if (originFunc.flag==true) continue;
            FunctionBackuper backuper = new FunctionBackuper(originFunc);
            funcBackupMap.put(originFunc, backuper.fork());
        }
    }

    public void run() {
        for (int i = 0; i < 2; ++i) {
            for (Call callInst : collectInlineCall()) {
                Transplantor transplantor = new Transplantor(callInst, funcBackupMap.get(callInst.function.ir));
                transplantor.fork();
            }
        }
    }

    private List<Call> collectInlineCall() {
        List<Call> callList = new ArrayList<>();
        for (Function func : funcBackupMap.keySet()) {
            IRBlock bb = func.head;
            while (bb != null) {
                IRInst inst = bb.head;
                while (inst != null) {
                    if (inst instanceof Call) {
                        Call callInst = (Call) inst;
                        Function callFunc = funcBackupMap.get(callInst.function.ir);
                        if (callFunc != null && callFunc.InstsCount()<100) {
                            callList.add(callInst);
                        }
                    }
                    inst = inst.next;
                }
                bb = bb.next;
            }
        }
        return callList;
    }

    private static class Transplantor implements IRVisitor {
        private Function hostFunc;
        private Function inlineFunc;
        private IRBlock beforeCallBB;
        private IRBlock afterCallBB;
        private IRBlock curBB;
        private VirtualRegister RetVal;
        private VirtualRegister RetValAddr;
        // Map<originVirtualRegister, copyVirtualRegister>
        private Map<IRBlock, IRBlock> bbCopyMap = new HashMap<>();
        private Map<VirtualRegister, Value> varReplaceMap = new HashMap<>();

        public Transplantor(Call callInst, Function inlineFunc) {
            this.inlineFunc = inlineFunc;

            beforeCallBB = callInst.curblock;
            hostFunc = beforeCallBB.function;
            beforeCallBB.spiltBlock(callInst);
            afterCallBB = beforeCallBB.next;

            int numberOfArgs = inlineFunc.args.size();
            for (int i = 0; i < numberOfArgs; ++i) {
                varReplaceMap.put(inlineFunc.args.get(i), callInst.args.get(i));
            }

            if (callInst.dest != null) {
                RetVal = callInst.dest;
                RetValAddr = hostFunc.AddVirtualRegister("retValAddr");
                hostFunc.head.addfront(new Allocation(hostFunc.head, RetValAddr, 8));
                afterCallBB.addfront(new Load(afterCallBB, RetVal, RetValAddr));
            }

            callInst.delete();

            IRBlock bb = inlineFunc.head;
            curBB = beforeCallBB;
            while (bb != null) {
                curBB.AddNext(hostFunc.AddBlock("c" + bb.name));
                curBB = curBB.next;
                bbCopyMap.put(bb, curBB);
                bb = bb.next;
            }

            beforeCallBB.add(new Jump(beforeCallBB, beforeCallBB.next));

        }

        public void fork() {
            IRBlock bb = inlineFunc.head;
            while (bb != null) {
                curBB = bbCopyMap.get(bb);
                IRInst inst = bb.head;
                while (inst != null) {
                    visit(inst);
                    inst = inst.next;
                }
                bb = bb.next;
            }
        }

        private String copyName(String name) {
            return "c" + name.substring(1, name.indexOf("_"));
        }

        private Value getVar(Value var) {
            if (var instanceof VirtualRegister) {
                assert varReplaceMap.containsKey(var);
                return varReplaceMap.get(var);
            }
            return var;
        }

        private VirtualRegister copyVirtualRegister(VirtualRegister reg) {
            if (reg == null) return null;
            VirtualRegister creg = hostFunc.AddVirtualRegister(copyName(reg.toString()));
            varReplaceMap.put(reg, creg);
            return creg;
        }

        public void visit(IRInst node) {
            node.accept(this);
        }

        public void visit(Allocation node) {
            hostFunc.head.addfront(new Allocation(hostFunc.head, copyVirtualRegister(node.dest), node.size));
        }

        public void visit(Malloc node) {
            curBB.add(new Malloc(curBB, copyVirtualRegister(node.dest), getVar(node.size)));
        }

        public void visit(Load node) {
            curBB.add(new Load(curBB, copyVirtualRegister(node.dest), getVar(node.address)));
        }

        public void visit(Store node) {
            curBB.add(new Store(curBB,getVar(node.address),getVar(node.value)));
        }

        public void visit(BinaryOpIR node) {
            curBB.add(new BinaryOpIR(curBB, copyVirtualRegister(node.dest), node.operator, getVar(node.lvalue), getVar(node.rvalue)));
        }

        public void visit(UnaryOpIR node) {
            curBB.add(new UnaryOpIR(curBB, copyVirtualRegister(node.dest), node.operator, getVar(node.value)));
        }

        public void visit(Call node) {
            List<Value> args = new ArrayList<>();
            for (Value arg : node.args) {
                args.add(getVar(arg));
            }
            curBB.add(new Call(curBB,copyVirtualRegister(node.dest), node.function,  args));
        }
        public void visit(Move node) {
            curBB.add(new Move(curBB, copyVirtualRegister(node.dest), getVar(node.value)));
        }

        public void visit(Branch node) {
            curBB.add(new Branch(curBB, getVar(node.flag), bbCopyMap.get(node.trueblock), bbCopyMap.get(node.falseblock)));
        }

        public void visit(Jump node) {
            curBB.add(new Jump(curBB, bbCopyMap.get(node.target)));
        }

        public void visit(Return node) {
            if (RetVal != null) {
                curBB.add(new Store(curBB, RetValAddr,getVar(node.value)));
            }
            curBB.add(new Jump(curBB, afterCallBB));
        }

    }
}