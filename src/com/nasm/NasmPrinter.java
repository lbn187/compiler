package com.nasm;
import java.util.*;
public class NasmPrinter implements NasmVisitor{
    public void visit(Nasm nasm){
        System.out.println("default rel");
        for(String s:nasm.GlobalVariables){
            System.out.println("global "+s);
        }
        for(String s:nasm.Builtins){
            System.out.println("global "+s);
        }
        for(Func func:nasm.Functions){
            System.out.println("global "+func.name);
        }
        System.out.println("extern strcmp");
        System.out.println("extern memcpy");
        System.out.println("extern malloc");
        System.out.println("extern puts");
        System.out.println("extern __printf_chk");
        System.out.println("extern __sprintf_chk");
        System.out.println("extern __stack_chk_fail");
        System.out.println("extern __isoc99_scanf");
        System.out.println("");
        System.out.println("SECTION .text");
        for(Func func:nasm.Functions)visit(func);
        System.out.println("");
        System.out.println("SECTION .data");
        for (String label : nasm.StringLiterals.keySet()) {
            System.out.println("");
            System.out.println(label + ":");
            String content = nasm.StringLiterals.get(label);
            System.out.println("    dq "+Integer.toString(content.length()));
            StringBuilder builder = new StringBuilder("\t\tdb      ");
            for (int i = 0; i < content.length(); ++i) {
                Formatter formatter = new Formatter();
                formatter.format("%02XH, ", (int) content.charAt(i));
                builder.append(formatter.toString());
            }
            builder.append("00H");
            System.out.println(builder.toString());
        }
        System.out.println("");
        System.out.println("    SECTION .bss");
        for(String s:nasm.GlobalVariables){
            System.out.println("");
            System.out.println(s+":");
            System.out.println("    resb 8");
        }
    }
    public void visit(Func func){
        for(Block block:func.Blocks){
            System.out.println("    "+block.name+":");
            for(Inst inst:block.Insts){
                visit(inst);
            }
        }
    }
    public void visit(Inst inst) {
        inst.accept(this);
    }
    public void visit(BinOp inst){
        System.out.println("    "+inst.op+" "+get(inst.dest)+" "+inst.src);
    }
    public void visit(Cmp inst){
        System.out.println("    cmp "+get(inst.a)+" "+get(inst.b));
    }
    public void visit(Cqo inst){
        System.out.println("    cqo");
    }
    public void visit(CallFunc inst){
        System.out.println("    call "+inst.name);
    }
    public void visit(IDiv inst){
        System.out.println("    idiv "+get(inst.divisor));
    }
    public void visit(Jmp inst){
        System.out.println("    "+inst.op+" "+inst.label.name);
    }
    public void visit(Mov inst){
        System.out.println("    mov "+get(inst.dest)+" "+get(inst.src));
    }
    public void visit(Nop inst){
        System.out.println("    nop");
    }
    public void visit(Pop inst){
        System.out.println("    pop "+get(inst.dest));
    }
    public void visit(Push inst){
        System.out.println("    push "+get(inst.src));
    }
    public void visit(Ret inst){
        System.out.println("    ret");
    }
    public void visit(SetFlag inst){
        System.out.println("    "+inst.name+" al");
    }
    public void visit(Shift inst){
        if(inst.bits instanceof Imm){
            System.out.println("    "+inst.op+" "+get(inst.var)+" "+inst.bits.toString());
        }else{
            System.out.println("    "+inst.op+" "+get(inst.var)+" cx");
        }
    }
    public void visit(UniOp inst){
        System.out.println("    "+inst.op+" "+get(inst.dest));
    }
    public String get(Var var){
        if(var instanceof VReg){
            return ((VReg)var).name;
        }
        if(var instanceof Memory){
            return ((Memory)var).getname();
        }
        return null;
    }
}