package com.IR;
import java.io.*;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;
public class IRPrinter implements IRVisitor {
    private PrintStream out;
    private String indent = "";

    public IRPrinter(PrintStream out) {
        this.out = out;
    }

    private void addIndent() {
        indent += "\t";
    }

    private void subIndent() {
        indent = indent.substring(0, indent.length() - 1);
    }

    private void println(String s) {
        this.out.println(indent + s);
    }

    private void print(String s) {
        this.out.print(indent + s);
    }

    public void visit(IRRoot node) {
        for (GlobalRegister globalReg : node.globalregisters) {
            println(globalReg.toString());
            out.println();
        }
        for (StringData stringData : node.strings.values()) {
            println(stringData.toString() + " \"" + stringData.value + "\"");
            out.println();
        }
        for (Function function : node.functions.values()) {
            visit(function);
            out.println();
        }
    }

    public void visit(Function node) {
        if (node.flag==true) {
            out.println("define " + node.name);
            return;
        }
        StringBuilder funcSignature = new StringBuilder("define " + node.name + " ( ");
        for (VirtualRegister arg : node.args) {
            funcSignature.append(arg).append(" ");
        }
        funcSignature.append(")");
        print(funcSignature.toString());
        println(" {");
        IRBlock curBB = node.head;
        while (curBB != null) {
            visit(curBB);
            out.println();
            curBB = curBB.next;
        }
        println("}");
    }

    public void visit(IRBlock node) {
        println("<" + node.id + "> " + node.name);
        addIndent();
        IRInst curInst = node.head;
        while (curInst != null) {
            visit(curInst);
            curInst = curInst.next;
        }
        subIndent();
    }

    public void visit(IRInst node) {
        node.accept(this);
    }

    public void visit(Allocation node) {
        println(node.dest + " = alloca " + node.size);
    }

    public void visit(Malloc node) {
        println(node.dest + " = malloc " + node.size);
    }

    public void visit(Load node) {
        println(node.dest + " = load " + node.address);
    }

    public void visit(Store node) {
        println("store " + node.value + " " + node.address);
    }

    public void visit(BinaryOpIR node) {
        println(node.dest + " = " + toLowerCase(node.operator) + " " + node.lvalue + " " + node.rvalue);
    }

    public void visit(UnaryOpIR node) {
        println(node.dest + " = " + toLowerCase(node.operator) + " " + node.value);
    }

    public void visit(Call node) {
        StringBuilder funcCall = new StringBuilder();
        if (node.dest != null) {
            funcCall.append(node.dest).append(" = ");
        }
        //TODO
        //funcCall.append(node.function.name).append(" ( ");
        for (Value arg : node.args) {
            funcCall.append(arg).append(" ");
        }
        funcCall.append(")");
        println(funcCall.toString());
    }
/*
    public void visit(Phi node) {
        StringBuilder phi = new StringBuilder(node.dest + " = phi");
        for (IRBlock pred : node.getAllSource().keySet()) {
            Operand var = node.getAllSource().get(pred);
            phi.append(" <").append(pred.getLabel()).append("> ").append(var);
        }
        println(phi.toString());
    }*/

    public void visit(Move node) {
        println("mov " + node.dest + " " + node.value);
    }

    public void visit(Branch node) {
//        if (node.getIfFalse() == null)
//            System.out.println("null false");
        println("br " + node.flag.toString() + " <" + String.valueOf(node.trueblock.id) + "> <" + String.valueOf(node.falseblock.id) + ">");
    }

    public void visit(Jump node) {
        println("br <" + node.target.id + ">");
    }

    public void visit(Return node) {
        StringBuilder ret = new StringBuilder("ret ");
        if (node.value != null) ret.append(node.value);
        println(ret.toString());
    }
}
