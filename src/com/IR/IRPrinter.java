package com.IR;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class IRPrinter implements IRVisitor {
    private PrintStream out;
    private Map<IRBlock, Boolean> BBVisited = new HashMap<>();
    private Map<IRBlock, String> labelMap = new HashMap<>();
    private Map<StaticData, String> dataMap = new HashMap<>();
    private Map<VirtualRegister, String> regMap;
    private Map<String, Integer> counterReg;
    private Map<String, Integer> counterBB = new HashMap<>();
    private Map<String, Integer> counterData = new HashMap<>();
    private boolean definingStatic = true;

    public IRPrinter(PrintStream out) {
        this.out = out;
    }

    private String newId(String name, Map<String, Integer> counter) {
        int cnt = counter.getOrDefault(name, 0) + 1;
        counter.put(name, cnt);
        if (cnt == 1) return name;
        return name + "_" + cnt;
    }

    private String regId(VirtualRegister reg) {
        String id = regMap.get(reg);
        if (id == null) {
            //if (reg.getOldName() != null) {
            //    id = regId(reg.getOldName()) + "." + reg.getSSAId();
            //} else {
            //    id = newId(reg.getHintName() == null ? "t" : reg.getHintName(), counterReg);
            //}
            id = newId(reg.name == null ? "t" : reg.name, counterReg);
            regMap.put(reg, id);
        }
        return id;
    }

    private String labelId(IRBlock BB) {
        String id = labelMap.get(BB);
        if (id == null) {
            id = newId(BB.name, counterBB);
            labelMap.put(BB, id);
        }
        return id;
    }

    private String dataId(StaticData data) {
        String id = dataMap.get(data);
        if (id == null) {
            id = newId(data.name, counterBB);
            dataMap.put(data, id);
        }
        return id;
    }

    @Override
    public void visit(IRRoot node) {
        node.datas.forEach(x -> x.accept(this));
        node.strings.values().forEach(this::visit);
        if (!node.datas.isEmpty() || !node.strings.isEmpty()) out.println();
        definingStatic = false;
        node.functions.values().forEach(this::visit);
    }

    @Override
    public void visit(IRBlock node) {
        if (BBVisited.containsKey(node)) return;
        BBVisited.put(node, true);
        out.println("%" + labelId(node) + ":");
        //node.phi.values().forEach(this::visit);
        for(IRInst i=node.head;i!=null;i=i.next)
            i.accept(this);
        //for (IRInstruction i = node.getHead(); i != null; i = i.getNext()) {
        //    i.accept(this);
        //}
    }

    @Override
    public void visit(Function node) {
        regMap = new IdentityHashMap<>();
        counterReg = new HashMap<>();
        out.printf("func %s ", node.name);
        //List<String> argNames = node.args.name;
        for (int i = 0; i < node.args.size(); ++i) {
            VirtualRegister reg = node.args.get(i);
            out.printf("$%s ", regId(reg));
        }
        out.printf("{\n");
        List<IRBlock>blocks=node.blocks;
        blocks.forEach(this::visit);
        //List<BasicBlock> RPO = node.getReversePostOrder();
        //RPO.forEach(this::visit);

        out.printf("}\n\n");
    }

    @Override
    public void visit(BinaryOpIR node) {
        out.print("    ");
        String op = null;
        switch (node.operator) {
            case "+": op = "add"; break;
            case "-": op = "sub"; break;
            case "*": op = "mul"; break;
            case "/": op = "div"; break;
            case "%": op = "rem"; break;
            case "<<": op = "shl"; break;
            case ">>": op = "ashr"; break;
            case "&": op = "and"; break;
            case "|": op = "or"; break;
            case "^": op = "xor"; break;
        }

        node.dest.accept(this);
        out.printf(" = %s ", op);
        node.lvalue.accept(this);
        out.printf(" ");
        node.rvalue.accept(this);
        out.println();
    }

    @Override
    public void visit(UnaryOpIR node) {
        out.print("    ");
        String op = null;
        switch (node.operator) {
            case "-": op = "neg"; break;
            case "~": op = "not"; break;
        }

        node.dest.accept(this);
        out.printf(" = %s ", op);
        node.value.accept(this);
        out.println();
    }

    @Override
    public void visit(CmpIR node) {
        out.print("    ");
        String op = null;
        switch (node.operator) {
            case "==": op = "seq"; break;
            case "!=": op = "sne"; break;
            case ">": op = "sgt"; break;
            case ">=": op = "sge"; break;
            case "<": op = "slt"; break;
            case "<=": op = "sle"; break;
        }

        node.dest.accept(this);
        out.printf(" = %s ", op);
        node.lvalue.accept(this);
        out.printf(" ");
        node.rvalue.accept(this);
        out.println();
    }

    @Override
    public void visit(Immediate node) {
        out.print(node.value);
    }

    @Override
    public void visit(Call node) {
        out.print("    ");
        if (node.dest != null) {
            node.dest.accept(this);
            out.print(" = ");
        }
        out.printf("call %s ", node.function.name);
        node.args.forEach(x -> {
            x.accept(this);
            out.print(" ");
        });
        out.println();
    }

    //@Override
    //public void visit(SystemCall node) {
    //    assert false;
    //}
/*
    @Override
    public void visit(PhiInstruction node) {
        out.print("    ");
        visit(node.dest);
        out.print(" = phi");
        for (Map.Entry<BasicBlock, IntValue> e : node.paths.entrySet()) {
            BasicBlock BB = e.getKey();
            IntValue reg = e.getValue();
            String src = null;
            if (reg == null) src = "undef";
            else if (reg instanceof VirtualRegister) src = "$"+regId((VirtualRegister) reg);
            else if (reg instanceof IntImmediate) src = String.valueOf(((IntImmediate) reg).getValue());
            else assert false;
            out.printf(" %%%s %s", labelId(BB), src);
        }
        out.println();
    }*/

    @Override
    public void visit(Branch node) {
        out.print("    br ");
        node.flag.accept(this);
        out.println(" %" + labelId(node.trueblock) + " %" + labelId(node.falseblock));
        out.println();
    }

    @Override
    public void visit(Return node) {
        out.print("    ret ");
        if (node.value != null) node.value.accept(this);
        out.println();
        out.println();
    }

    @Override
    public void visit(Jump node) {
        out.printf("    jump %%%s\n\n", labelId(node.target));
    }

    @Override
    public void visit(VirtualRegister node) {
        out.print("$" + regId(node));
    }

    /*@Override
    public void visit(PhysicalRegister node) {
        //TODO
    }

    @Override
    public void visit(StackSlot node) {
        //TODO
    }*/

    @Override
    public void visit(Allocation node) {
        out.print("    ");
        node.dest.accept(this);
        out.print(" = alloc ");
        node.size.accept(this);
        out.println();
    }

    @Override
    public void visit(Load node) {
        out.print("    ");
        node.dest.accept(this);
        out.printf(" = load %d ", node.size);
        node.address.accept(this);
        out.println(" " + node.offset);
    }

    @Override
    public void visit(Store node) {
        out.printf("    store %d ", node.size);
        node.address.accept(this);
        out.print(" ");
        node.value.accept(this);
        out.println(" 0");
    }

    @Override
    public void visit(Move node) {
        out.print("    ");
        node.dest.accept(this);
        out.print(" = move ");
        node.value.accept(this);
        out.println();
    }

    @Override
    public void visit(ArraySpace node) {
        if (definingStatic) out.printf("space @%s %d\n", dataId(node), node.length);
        else out.print("@" + dataId(node));
    }

    @Override
    public void visit(StringData node) {
        if (definingStatic) out.printf("asciiz @%s %s\n", dataId(node), node.value);
        else out.print("@" + dataId(node));
    }
}
