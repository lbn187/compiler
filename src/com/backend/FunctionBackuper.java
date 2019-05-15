package com.backend;
import com.IR.*;
import java.util.HashMap;
import java.util.Map;
public class FunctionBackuper implements IRVisitor {
    private Function originFunc;
    private Map<IRBlock, IRBlock> bbCopyMap = new HashMap<>();
    private Map<IRInst, IRInst> instCopyMap = new HashMap<>();
    public FunctionBackuper(Function originFunc) {
        this.originFunc = originFunc;
    }
    public Function fork() {
        Function backupFunc = new Function(originFunc);
        IRBlock backupBB = backupFunc.head;
        IRBlock originBB = originFunc.head;
        bbCopyMap.put(originBB, backupBB);
        originBB = originBB.next;
        while (originBB != null) {
            backupBB.AddNext(new IRBlock(backupFunc, originBB.name,originBB.id));
            backupBB = backupBB.next;
            bbCopyMap.put(originBB, backupBB);
            originBB = originBB.next;
        }
        originBB = originFunc.head;
        while (originBB != null) {
            backupBB = bbCopyMap.get(originBB);
            IRInst originInst = originBB.head;
            while (originInst != null) {
                visit(originInst);
                assert instCopyMap.containsKey(originInst);
                IRInst backupInst = instCopyMap.get(originInst);
                if (backupInst instanceof Branch) {
                    Branch condBranch = (Branch) backupInst;
                    condBranch.ReplaceTarget(condBranch.trueblock, bbCopyMap.get(condBranch.trueblock));
                    condBranch.ReplaceTarget(condBranch.falseblock, bbCopyMap.get(condBranch.falseblock));
                }
                if (backupInst instanceof Jump) {
                    Jump directBranch = (Jump) backupInst;
                    directBranch.target=bbCopyMap.get(directBranch.target);
                }
                backupBB.add(backupInst);
                originInst = originInst.next;
            }
            originBB = originBB.next;
        }

        return backupFunc;
    }

    public void visit(IRInst node) {
        node.accept(this);
    }

    public void visit(Allocation node) {
        instCopyMap.put(node, new Allocation(null, node.dest, node.size));
    }

    public void visit(Malloc node) {
        instCopyMap.put(node, new Malloc(null, node.dest, node.size));
    }

    public void visit(Load node) {
        instCopyMap.put(node, new Load(null, node.dest, node.address));
    }

    public void visit(Store node) {
        instCopyMap.put(node, new Store(null,node.address,node.value));
    }

    public void visit(BinaryOpIR node) {
        instCopyMap.put(node, new BinaryOpIR(null, node.dest, node.operator, node.lvalue, node.rvalue));
    }

    public void visit(UnaryOpIR node) {
        instCopyMap.put(node, new UnaryOpIR(null, node.dest, node.operator, node.value));
    }

    public void visit(Call node) {
        instCopyMap.put(node, new Call(null, node.dest,node.function, node.args));
    }
    public void visit(Move node) {
        instCopyMap.put(node, new Move(null, node.dest, node.value));
    }

    public void visit(Branch node) {
        instCopyMap.put(node, new Branch(null, node.flag, node.trueblock, node.falseblock));
    }

    public void visit(Jump node) {
        instCopyMap.put(node,  new Jump(null, node.target));
    }

    public void visit(Return node) {
        instCopyMap.put(node, new Return(null, node.value));
    }

}