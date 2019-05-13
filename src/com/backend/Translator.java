package com.backend;
import com.IR.*;
import com.nasm.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static com.nasm.RegConst.*;
public class Translator implements IRVisitor{
    public Nasm nasm=new Nasm();
    public Func CurFunc;
    public Block CurBlock;
    public List<VirtualRegister> CurArgs=new ArrayList<>();
    public Map<IRBlock,Block>BlockMap=new HashMap<>();
    public Map<VirtualRegister,VReg>VirtualRegisterMap=new HashMap<>();
    public Map<VirtualRegister,VReg>AllocMap=new HashMap<>();
    public Map<VReg,VReg>CalleeMap=new HashMap<>();
    public int AllocCnt=0;
    public int BlockCnt=0;
    public int ImmCnt=0;
    public int min(int x,int y){
        return x<y?x:y;
    }
    public Nasm getNasm(IRRoot irroot){
        visit(irroot);
        return nasm;
    }
    public void visit(IRRoot irroot){
        for(GlobalRegister o:irroot.globalregisters){
            nasm.GlobalVariables.add(toNasm(o.toString()));
        }
        for(StringData o:irroot.strings.values()){
            nasm.StringLiterals.put(toNasm(o.toString()),o.value);
        }
        for(Function o:irroot.functions.values()){
            if(o.flag==true){
                nasm.Builtins.add(o.name);
            }else {
                visit(o);
            }
        }
        for (IRBlock bb : BlockMap.keySet()) {
            Block block = BlockMap.get(bb);
            for (IRBlock succBB:bb.GetSuccs()) {
                block.Succs.add(BlockMap.get(succBB));
            }
        }
    }
    public void visit(Function func){
        //System.out.println("VISITFUNC  :"+func.name);
        CurFunc=new Func(func.name);
        nasm.Functions.add(CurFunc);
        CalleeMap=new HashMap<>();
        for(int i=0;i<5;i++){
            CalleeMap.put(VRegs.get(CalleeRegs[i]),new VReg("%local_"+Regs[CalleeRegs[i]]));
        }
        CurArgs=func.args;
        for(IRBlock o=func.head;o!=null;o=o.next)
                visit(o);
    }
    /*
    TODO

    public boolean CmpAndJmp(IRInst inst){
        if(!(inst instanceof BinaryOpIR))return false;
        BinaryOpIR first=(BinaryOpIR)inst;
        String op=first.operator;
        if(op.equals("==")||op.equals("!=")||op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")){
            if(!(inst.next instanceof Branch))return false;
            Branch second=(Branch)inst.next;
            if(first.dest!=second.flag)return false;
            AddCmp(first.a,first.b);

        }else return false;
    }

     */
    public void visit(IRBlock block){
        //System.out.println("    VISITBLOCK : "+block.name);
        CurBlock=CalBlock(block);
       // System.out.println("        Curblockname : "+CurBlock.name);
        CurFunc.Blocks.add(CurBlock);
        if(block.ishead()){//is head
            for(VReg o:CalleeMap.keySet()){
                CurBlock.Insts.add(new Mov(CalleeMap.get(o),o));
            }
            int offset=8;
            for(int i=6;i<CurArgs.size();i++){
                offset+=8;
                CurBlock.Insts.add(new Mov(CalVirtualRegister(CurArgs.get(i)),new Memory(rbp,offset)));
            }
            for(int i=0;i<min(CurArgs.size(),6);i++){
                CurBlock.Insts.add(new Mov(CalVirtualRegister(CurArgs.get(i)),VRegs.get(Args[i])));
            }
            if(block.function.name.equals("_main")){
                CurBlock.Insts.add(new CallFunc("__Global",0));
            }
        }
        for(IRInst inst=block.head;inst!=null;){
            visit(inst);
            inst=inst.next;
        }
    }
    public void visit(IRInst inst){
        inst.accept(this);
    }
    public void visit(Allocation alloc){
        ++AllocCnt;
        AllocMap.put(alloc.dest,new VReg("%alloca_"+AllocCnt));
    }
    public void visit(Malloc node){
        CurBlock.Insts.add(new Mov(rdi,CalValue(node.size)));
        CurBlock.Insts.add(new CallFunc("malloc",0));
        CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),rax));
    }
    public void visit(Load node){
        Value address=node.address;
        if(address instanceof VirtualRegister){
            if(AllocMap.containsKey(address))CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),AllocMap.get(address)));
            else CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),new Memory(CalVirtualRegister((VirtualRegister)address))));
        }else{//quanju
            CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),new Memory(new Label(toNasm(address.toString())))));
        }
    }
    public void visit(Store node){
        Value address=node.address;
        if(address instanceof VirtualRegister){
            if(AllocMap.containsKey(address))CurBlock.Insts.add(new Mov(AllocMap.get(address),CalValue(node.value)));
            else CurBlock.Insts.add(new Mov(new Memory(CalVirtualRegister((VirtualRegister)address)),CalValue(node.value)));
        }else{//quanju
            CurBlock.Insts.add(new Mov(new Memory(new Label(toNasm(address.toString()))),CalValue(node.value)));
        }
    }
    public void visit(BinaryOpIR node){
        String op=node.operator;
        Value lhs=node.lvalue;
        Value rhs=node.rvalue;
        VirtualRegister dest=node.dest;
        VReg regl;
        VReg regr;
        if(op.equals("==")||op.equals("!=")||op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")){
            AddCmp(node.lvalue,node.rvalue);
            String name="";
            if(op.equals("=="))name="sete";
            if(op.equals("!="))name="setne";
            if(op.equals("<"))name="setl";
            if(op.equals("<="))name="setle";
            if(op.equals(">"))name="setg";
            if(op.equals(">="))name="setge";
            CurBlock.Insts.add(new SetFlag(name,rax));
            CurBlock.Insts.add(new Mov(CalVirtualRegister(dest),rax));
            return;
        }
        if(op.equals("/")||op.equals("%")){
            CurBlock.Insts.add(new Mov(rax,CalValue(lhs)));
            CurBlock.Insts.add(new Cqo());
            if(rhs instanceof Immediate){
                ++ImmCnt;
                regr=new VReg("%divisor_"+ImmCnt);
                CurBlock.Insts.add(new Mov(regr,new Imm(((Immediate)rhs).value)));
            }else{
                regr=CalVirtualRegister((VirtualRegister)rhs);
            }
            CurBlock.Insts.add(new IDiv(regr));
            if(op.equals("/")){
                CurBlock.Insts.add(new Mov(CalVirtualRegister(dest),rax));
            }else{
                CurBlock.Insts.add(new Mov(CalVirtualRegister(dest),rdx));
            }
            return;
        }
        if(op.equals("<<")||op.equals(">>")){
            CurBlock.Insts.add(new Mov(CalVirtualRegister(dest),CalValue(lhs)));
            Var cnt;
            if(rhs instanceof Immediate){
                cnt=new Imm(((Immediate)rhs).value);
            }else{
                CurBlock.Insts.add(new Mov(rcx,CalVirtualRegister((VirtualRegister)rhs)));
                cnt=rcx;
            }
            if(op.equals("<<")){
                CurBlock.Insts.add(new Shift("shl",CalVirtualRegister(dest),cnt));
            }else{
                CurBlock.Insts.add(new Shift("shr",CalVirtualRegister(dest),cnt));
            }
            return;
        }
        String opname="";
        if(op.equals("*"))opname="imul";
        if(op.equals("+"))opname="add";
        if(op.equals("-"))opname="sub";
        if(op.equals("&"))opname="and";
        if(op.equals("|"))opname="or";
        if(op.equals("^"))opname="xor";
        CurBlock.Insts.add(new Mov(CalVirtualRegister(dest),CalValue(lhs)));
        CurBlock.Insts.add(new BinOp(opname,CalVirtualRegister(dest),CalValue(rhs)));
    }
    public void visit(UnaryOpIR node){
        String op=node.operator;
        String name="";
        if(op.equals("++"))name="inc";
        if(op.equals("--"))name="dec";
        if(op.equals("!"))name="neg";
        if(op.equals("~"))name="not";
        CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),CalValue(node.value)));
        CurBlock.Insts.add(new UniOp(name,CalVirtualRegister(node.dest)));
    }
    public void visit(Call node){
        int ParamCnt=node.args.size();
        //System.out.println("NAME:"+node.function.typename+" SIZE:"+ParamCnt);
        int offset=0;
        if(ParamCnt>6){
            offset=(ParamCnt-6)*8;
            if(offset%16!=0){
                CurBlock.Insts.add(new Nop());
                offset+=8;
            }
        }
        for(int i=ParamCnt-1;i>=6;i--){
            Value arg=node.args.get(i);
            if(arg instanceof Immediate){
                CurBlock.Insts.add(new Push(new Imm(((Immediate)arg).value)));
            }else{
                CurBlock.Insts.add(new Push(CalVirtualRegister((VirtualRegister)arg)));
            }
        }
        for(int i=0;i<min(ParamCnt,6);i++){
            CurBlock.Insts.add(new Mov(VRegs.get(Args[i]),CalValue(node.args.get(i))));
        }
        CurBlock.Insts.add(new CallFunc(node.function.ir.name,offset));
        if(node.dest!=null){
            CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),rax));
        }
    }
    public void visit(Move node){
        CurBlock.Insts.add(new Mov(CalVirtualRegister(node.dest),CalValue(node.value)));
    }
    public void AddCmp(Value lhs,Value rhs){
        Var lv;
        if(lhs instanceof Immediate){
            ++ImmCnt;
            lv=new VReg("%cmplhs_"+ImmCnt);
            int val=((Immediate)lhs).value;
            CurBlock.Insts.add(new Mov(lv,new Imm(val)));
        }else{
            lv=CalValue(lhs);
        }
        CurBlock.Insts.add(new Cmp(lv,CalValue(rhs)));
    }
    public void AddJmp(IRBlock nextblock,IRBlock trueblock,IRBlock falseblock,String yes,String no){
        if(nextblock==trueblock){
            CurBlock.Insts.add(new Jmp(no,new Label(CalBlock(falseblock).name)));
        }else{
            CurBlock.Insts.add(new Jmp(yes,new Label(CalBlock(trueblock).name)));
            if(nextblock!=falseblock){
                CurBlock.Insts.add(new Jmp("jmp",new Label(CalBlock(falseblock).name)));
            }
        }
    }
    public void visit(Branch node) {
        /*VReg reg;
        if(node.flag instanceof Immediate){
            ++ImmCnt;
            reg=new VReg("%cmplhs_"+ImmCnt);
            CurBlock.Insts.add(new Mov(reg,new Imm(((Immediate)node.flag).value)));
        }else{
            reg=(VReg)CalValue(node.flag);
        }*/
        AddCmp(node.flag,new Immediate(0));
        AddJmp(node.curblock.next,node.trueblock,node.falseblock,"jne","je");
        //CurBlock.Insts.add(new Cmp(reg,new Imm(0)));
        //CurBlock.Insts.add(new Jmp("jne",new Label(CalBlock(node.trueblock).name)));
        //CurBlock.Insts.add(new Jmp("jmp",new Label(CalBlock(node.falseblock).name)));
    }
    public void visit(Jump node){
        CurBlock.Insts.add(new Jmp("jmp",new Label(CalBlock(node.target).name)));
    }
    public void visit(Return node){
        for(VReg o:CalleeMap.keySet()){
            CurBlock.Insts.add(new Mov(o,CalleeMap.get(o)));
        }
        if(node.value!=null){
            CurBlock.Insts.add(new Mov(rax,CalValue(node.value)));
        }
        CurBlock.Insts.add(new Ret());
    }
    public String toNasm(String s){
        if(s.startsWith("@")){
            return "_"+s.substring(1);
        }
        if(s.startsWith("$")){
            return "__"+s.substring(1);
        }
        assert false;
        return null;
    }
    public Block CalBlock(IRBlock block){
        if(!BlockMap.containsKey(block)){
            String s;
            if(block.ishead()){
                s=block.function.name;
            }else{
                ++BlockCnt;
                s="__L_"+BlockCnt;
            }
            BlockMap.put(block,new Block(s));
        }
        return BlockMap.get(block);
    }
    public VReg CalVirtualRegister(VirtualRegister register){
        if(!VirtualRegisterMap.containsKey(register))
            VirtualRegisterMap.put(register,new VReg(register.toString()));
        return VirtualRegisterMap.get(register);
    }
    public Var CalValue(Value value){
        if(value instanceof Immediate)return new Imm(((Immediate)value).value);
        if(value instanceof VirtualRegister)return CalVirtualRegister((VirtualRegister)value);
        if(value instanceof StringData) {
            return new Label(toNasm(value.toString()));
        }
        return null;
    }
}
