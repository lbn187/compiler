package com.frontend;
import com.IR.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;
import com.AST.*;
import com.Type.*;
import com.frontend.Information;
import com.frontend.ScopeBuilder;
public class IRBuilder extends ASTVisitor{
    public IRBlock CurBlock;
    public IRBlock CurLoopBlock;
    public IRBlock CurLoopAfterBlock;
    public IRBlock ExitBlock;
    public IRBlock InitBlock;
    public Function CurFunction;
    public VirtualRegister ThisAddress;
    public boolean AddressFlag=false;
    public boolean ClassFlag=false;
    //public boolean FunctionArgFlag=false;
    public IRRoot irroot=new IRRoot();
    public VirtualRegister returnvalue;
    //public Map<StaticData,VirtualRegister> CurFunctionStaticMap=new HashMap<>();
    public IRBuilder(){

    }
    public void getIR(Node root)throws Exception{
        visit(root);
    }
    /*public void Assign(boolean memflag,int size,Value address,int offset,ExprNode expr){
        if(expr.trueblock==null){
            if(memflag==true){
                CurBlock.add(new Store(CurBlock,size,address,expr.register,offset));
            }else{
                CurBlock.add(new Move(CurBlock,(Register)address,expr.register));
            }
        }else{
            IRBlock blk=new IRBlock(CurFunction,null);
            if(memflag==true){
                expr.trueblock.add(new Store(CurBlock,size,address,new Immediate(1),offset));
                expr.falseblock.add(new Store(CurBlock,size,address,new Immediate(0),offset));
            }else{
                expr.trueblock.add(new Move(CurBlock,(VirtualRegister)address,new Immediate(1)));
                expr.falseblock.add(new Move(CurBlock,(VirtualRegister)address,new Immediate(0)));
            }
            expr.trueblock.addend(new Jump(CurBlock,blk));
            expr.falseblock.addend(new Jump(CurBlock,blk));
            CurBlock=blk;
        }
    }*/
    public boolean LogicalJudge(ExprNode u){
        if(u instanceof BinaryOpNode){
            String op=((BinaryOpNode)u).operator;
            if(op=="&&"||op=="||")return true;else return false;
        }else if(u instanceof UnaryOpNode){
            String op=((UnaryOpNode)u).operator;
            if(op=="!")return true;else return false;
        }else return false;
    }
    public boolean MemoryJudge(ExprNode u){
        if(u instanceof ArefNode)return true;
        if(u instanceof MemberNode)return true;
        return false;
    }
    public void visit(ProgramNode u)throws Exception{
        InitBlock=irroot.functions.get("__Global").head;
        for(Node o:u.son)visit(o);
        IRBlock blk=irroot.functions.get("__Global").tail;
        blk.add(new Return(blk,null));
    }
    public void visit(VariableDefNode u)throws Exception{
        Scope scope=u.belong.getscope(u.name);
        Information information=u.belong.get(u.name);
        boolean isclass=u.belong.classflag;
        if(isclass)return;//class bu ding yi
        if(scope.father==null){//quanju
            GlobalRegister reg=new GlobalRegister(u.name);
            irroot.globalregisters.add(reg);
            information.register=reg;
            if(u.expr!=null){
                CurFunction=irroot.functions.get("__Global");
                CurBlock=InitBlock;
                visit(u.expr);
                CurBlock.add(new Store(CurBlock,reg,u.expr.register));
                InitBlock=CurBlock;
            }
        }else {//jubu
            VirtualRegister reg = CurFunction.AddVirtualRegister("VariableAddress");
            information.register = reg;
            CurFunction.head.addfront(new Allocation(CurFunction.head, reg, 8));
            if (u.expr != null) {
                visit(u.expr);
                CurBlock.add(new Store(CurBlock, reg, u.expr.register));
            }
        }
    }
    public void visit(FunctionDefNode u)throws Exception {
        String name = u.belong.name + "_" + u.name;
        CurFunction = new Function(name, false);
        ExitBlock = CurFunction.AddBlock(CurFunction.name + ".exit");
        irroot.functions.put(name, CurFunction);
        CurBlock = CurFunction.head;
        if (ClassFlag == true) {
            VirtualRegister ThisValue = CurFunction.AddVirtualRegister("ThisValue");
            CurFunction.args.add(ThisValue);
            ThisAddress = CurFunction.AddVirtualRegister("ThisAddress");
            CurFunction.head.addfront(new Store(CurBlock, ThisAddress, ThisValue));
        }
        for (VariableDefNode o : u.variables) {
            VirtualRegister ArgValue = CurFunction.AddVirtualRegister("ArgValue");
            CurFunction.args.add(ArgValue);
            VirtualRegister ArgAddress = CurFunction.AddVirtualRegister("ArgAddress");
            Information information = o.belong.get(o.name);
            information.register = ArgAddress;
            CurFunction.head.addfront(new Allocation(CurFunction.head, ArgAddress, 8));
            CurBlock.add(new Store(CurBlock, ArgValue, ArgAddress));
        }
        if (u.type instanceof VoidType) {
            returnvalue = null;
        } else {
            returnvalue = CurFunction.AddVirtualRegister("ReturnValue");
            CurBlock.add(new Move(CurBlock, returnvalue, new Immediate(0)));
        }
        visit(u.block);
        ExitBlock.add(new Return(ExitBlock, returnvalue));
        CurFunction.Append(ExitBlock);
    }
    public void visit(ClassDefNode u)throws Exception{
        ClassFlag=true;
        for(FunctionDefNode o:u.functions)visit(o);
        ClassFlag=false;
    }
    public void visit(BlockNode u)throws Exception{
        for(StmtNode o:u.stmts)visit(o);
    }
    public void visit(BreakNode u)throws Exception{
        CurBlock.add(new Jump(CurBlock,CurLoopAfterBlock));
    }
    public void visit(ContinueNode u)throws Exception{
        CurBlock.add(new Jump(CurBlock,CurLoopBlock));
    }
    public void visit(ForNode u)throws Exception{
        IRBlock condblock=CurFunction.AddBlock("for_cond");
        IRBlock loopblock=CurFunction.AddBlock("for_loop");
        IRBlock afterblock=CurFunction.AddBlock("for_after");
        if(u.mid==null){
            condblock=loopblock;
            CurBlock.AddNext(loopblock);
        }else {
            CurBlock.AddNext(condblock);
            condblock.AddNext(loopblock);
        }
        loopblock.AddNext(afterblock);
        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=condblock;
        CurLoopAfterBlock=afterblock;
        if(u.pre!=null)visit(u.pre);
        CurBlock.add(new Jump(CurBlock,condblock));
        if(u.mid!=null) {
            CurBlock = condblock;
            u.mid.trueblock = loopblock;
            u.mid.falseblock = afterblock;
            visit(u.mid);
        }
        CurBlock=loopblock;
        visit(u.stmt);
        if(u.suc!=null)visit(u.suc);
        CurBlock.add(new Jump(CurBlock,condblock));
        CurBlock=afterblock;
        CurLoopBlock=PreLoopBlock;
        CurLoopAfterBlock=PreLoopAfterBlock;
    }
    public void visit(WhileNode u)throws Exception{
        IRBlock condblock=CurFunction.AddBlock("while_cond");
        IRBlock loopblock=CurFunction.AddBlock("while_loop");
        IRBlock afterblock=CurFunction.AddBlock("while_after");
        CurBlock.AddNext(condblock);
        condblock.AddNext(loopblock);
        loopblock.AddNext(afterblock);
        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=condblock;
        CurLoopAfterBlock=afterblock;
        CurBlock.add(new Jump(CurBlock,condblock));
        CurBlock=condblock;
        u.expr.trueblock=loopblock;
        u.expr.falseblock=afterblock;
        visit(u.expr);
        CurBlock=loopblock;
        visit(u.stmt);
        CurBlock.add(new Jump(CurBlock,condblock));
        CurBlock=afterblock;
        CurLoopBlock=PreLoopBlock;
        CurLoopAfterBlock=PreLoopAfterBlock;
    }
    public void visit(IfNode u)throws Exception{
        IRBlock trueblock=CurFunction.AddBlock("if_true");
        CurBlock.AddNext(trueblock);
        IRBlock falseblock=null;
        IRBlock mergeblock=CurFunction.AddBlock("if_merge");
        trueblock.AddNext(mergeblock);
        if(u.elsestmt!=null){
            falseblock=CurFunction.AddBlock("if_false");
            trueblock.AddNext(falseblock);
        }
        u.expr.trueblock=trueblock;
        if(u.elsestmt==null)u.expr.falseblock=mergeblock;else u.expr.falseblock=falseblock;
        visit(u.expr);
        CurBlock=trueblock;
        visit(u.ifstmt);
        CurBlock.add(new Jump(CurBlock,mergeblock));
        if(u.elsestmt!=null){
            CurBlock=falseblock;
            visit(u.elsestmt);
            CurBlock.add(new Jump(CurBlock,mergeblock));
        }
        CurBlock=mergeblock;
    }
    public void visit(ReturnNode u)throws Exception{
        if(u.expr==null){
            CurBlock.add(new Jump(CurBlock,ExitBlock));
        }else{
            visit(u.expr);
            CurBlock.add(new Move(CurBlock,returnvalue,u.expr.register));
            CurBlock.add(new Jump(CurBlock,ExitBlock));
        }
    }

    public Value GetLhsAddress(ExprNode u) throws Exception{
        if(u instanceof VariableNode){
            VariableNode o=(VariableNode)u;
            if(o.name.equals("this"))return ThisAddress;
            if(o.belong.classflag==true){//is in a class
                VirtualRegister reg=CurFunction.AddVirtualRegister("ClassPtr");
                CurBlock.add(new Load(CurBlock,reg,ThisAddress));
                Immediate offset=new Immediate(o.belong.get(u.name).offset);
                VirtualRegister Address=CurFunction.AddVirtualRegister("MemberAddress");
                CurBlock.add(new BinaryOpIR(CurBlock,Address,"+",reg,offset));
                return Address;
            }
            return o.belong.get(u.name).register;
        }else
        if(u instanceof ArefNode){
            ArefNode o=(ArefNode)u;
            VirtualRegister ptr;
            if(o.exprname instanceof CreatorNode || o.exprname instanceof FuncExprNode){
                visit(o.exprname);
                ptr=(VirtualRegister)o.exprname.register;
            }else{
                Value ptrptr=GetLhsAddress(o.exprname);
                ptr=CurFunction.AddVirtualRegister("ArrayPtr");
                CurBlock.add(new Load(CurBlock,ptr,ptrptr));
            }
            VirtualRegister base=CurFunction.AddVirtualRegister("ArrayBase");
            CurBlock.add(new BinaryOpIR(CurBlock,base,"+",ptr,new Immediate(8)));//store the ptr
            visit(o.exprexpr);
            VirtualRegister off=CurFunction.AddVirtualRegister("Offset");
            CurBlock.add(new BinaryOpIR(CurBlock,off,"*",o.exprexpr.register,new Immediate(8)));
            VirtualRegister elementaddress=CurFunction.AddVirtualRegister("ElementAddress");
            CurBlock.add(new BinaryOpIR(CurBlock,elementaddress,"+",base,off));
            return elementaddress;
        }else
        if(u instanceof MemberNode){
            MemberNode o=(MemberNode)u;
            //TODO
        }else
        System.out.println("GetLhsAddress Wrong");
        return null;
    }
    public void visit(AssignNode u)throws Exception{
        visit(u.exprr);
        Value address=GetLhsAddress(u.exprl);
        CurBlock.add(new Store(CurBlock,address,u.exprr.register));
    }
    public void visit(BinaryOpNode u)throws Exception{
        String op=u.operator;
        if(u.exprl.type instanceof StringType){
            visit(u.exprl);
            visit(u.exprr);
            VirtualRegister register=CurFunction.AddVirtualRegister("res");
            List<Value>args=new ArrayList<>();
            args.add(u.exprl.register);
            args.add(u.exprr.register);
            u.register=register;
            if(op.equals("+"))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_ADD,args));
            if(op.equals("=="))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_EQ,args));
            if(op.equals("!="))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_NEQ,args));
            if(op.equals("<"))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_LT,args));
            if(op.equals("<="))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_LEQ,args));
            if(op.equals(">"))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_GT,args));
            if(op.equals(">="))CurBlock.add(new Call(CurBlock,register,ScopeBuilder.STRING_GEQ,args));
            if((op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!="))&&u.trueblock!=null){
                CurBlock.add(new Branch(CurBlock,register,u.trueblock,u.falseblock));
            }
            return;
        }
        if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals(">>")||op.equals("<<")||
                op.equals("&")||op.equals("|")||op.equals("^")||op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!=")){
            visit(u.exprl);
            visit(u.exprr);
            VirtualRegister register=CurFunction.AddVirtualRegister("res");
            u.register=register;
            CurBlock.add(new BinaryOpIR(CurBlock,register,u.operator,u.exprl.register,u.exprr.register));
            if((op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!="))&&u.trueblock!=null){
                CurBlock.add(new Branch(CurBlock,register,u.trueblock,u.falseblock));
            }
        }
        if(op.equals("&&")||op.equals("||")){
            if(op.equals("&&")) {
                u.exprl.trueblock=CurFunction.AddBlock("lhs_true");
                CurBlock.AddNext(u.exprl.trueblock);
                u.exprl.falseblock=u.falseblock;
                visit(u.exprl);
                CurBlock=u.exprl.trueblock;
            }else{
                u.exprl.trueblock=u.trueblock;
                u.exprl.falseblock=CurFunction.AddBlock("lhs_false");
                CurBlock.AddNext(u.exprl.falseblock);
                visit(u.exprl);
                CurBlock=u.exprl.falseblock;
            }
            u.exprr.trueblock=u.trueblock;
            u.exprr.falseblock=u.falseblock;
            visit(u.exprr);
        }
    }
    public void visit(SuffixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }
    public void visit(PrefixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }
    public void visit(UnaryOpNode u)throws Exception {
        String op = u.operator;
        if (op.equals("!")) {
            u.expr.trueblock = u.trueblock;
            u.expr.falseblock = u.falseblock;
            visit(u.expr);
            return;
        }
        visit(u.expr);
        if (op .equals("+")) {
            u.register = u.expr.register;
        }
        if (op.equals("-")||op.equals("~")) {
            VirtualRegister register = CurFunction.AddVirtualRegister("res");
            CurBlock.add(new UnaryOpIR(CurBlock, register, op, u.expr.register));
            u.register=register;
        }
        if (op.equals("++")) {
            VirtualRegister register = CurFunction.AddVirtualRegister("res");
            CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            CurBlock.add(new Move(CurBlock,(Register)u.expr.register,register));
        }
        if(op.equals("--")){
            VirtualRegister register = CurFunction.AddVirtualRegister("res");
            CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            CurBlock.add(new Move(CurBlock,(Register)u.expr.register,register));
        }
    }
    public void visit(FuncExprNode u)throws Exception{
        String name=u.name;
        if(name.equals("print")){
            visit(u.exprs.get(0));
            List<Value>args=new ArrayList<>();
            args.add(u.exprs.get(0).register);
            CurBlock.add(new Call(CurBlock,null,ScopeBuilder.STRING_PRINT,args));
            return;
        }
        if(name.equals("println")){
            visit(u.exprs.get(0));
            List<Value>args=new ArrayList<>();
            args.add(u.exprs.get(0).register);
            CurBlock.add(new Call(CurBlock,null,ScopeBuilder.STRING_PRINTLN,args));
            return;
        }
        if(name.equals("getString")){
            List<Value>args=new ArrayList<>();
            VirtualRegister ptr=CurFunction.AddVirtualRegister("StringPtr");
            CurBlock.add(new Call(CurBlock,ptr,ScopeBuilder.GETSTRING,args));
            return;
        }
        if(name.equals("getInt")){
            List<Value>args=new ArrayList<>();
            VirtualRegister reg=CurFunction.AddVirtualRegister("res");
            CurBlock.add(new Call(CurBlock,reg,ScopeBuilder.GETINT,args));
            return;
        }
        if(name.equals("toString")){
            //TODO
        }
        if(name.equals("length")){
            //TODO
        }
        if (name.equals("substring")) {
            //TODO
        }
        if(name.equals("parseInt")){
            //TODO
        }
        if(name.equals("ord")){
            //TODO
        }
        if(name.equals("size")){
            //TODO
        }
        if(u.belong.classflag==true){
            //TODO
        }
        //WRONG  not belong.name
        /*
        Function function=irroot.functions.get(u.belong.name+"_"+u.name);
        List<Value>args;
        for(ExprNode o:u.exprs){
            visit(o);
            args.add(o.register);
        }
        VirtualRegister register=null;
        if(!(u.type instanceof VoidType))register=CurFunction.AddVirtualRegister("res");
        CurBlock.add(new Call(CurBlock,register,function,arglist));
        u.register=register;
        if((u.type instanceof BoolType)&&u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }*/
    }
    public void visit(ArefNode u)throws Exception{
        Value address=GetLhsAddress(u);
        VirtualRegister reg=CurFunction.AddVirtualRegister("ElementValue");
        CurBlock.add(new Load(CurBlock,reg,address));
        u.register=reg;
        if((u.type instanceof BoolType)&&u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(MemberNode u)throws Exception{
        Value address=GetLhsAddress(u);
        VirtualRegister reg=CurFunction.AddVirtualRegister("MemberValue");
        CurBlock.add(new Load(CurBlock,reg,address));
        u.register=reg;
        if((u.type instanceof BoolType)&&u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(CreatorNode u)throws Exception {
        //TODO
    }
    public void visit(NullStmtNode u)throws Exception{

    }
    public void visit(NullLiteralNode u)throws Exception{
        u.register=new Immediate(0);
    }
    public void visit(BoolLiteralNode u)throws Exception{
        if(u.flag==true)u.register=new Immediate(1);else u.register=new Immediate(0);
        if(u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(IntLiteralNode u)throws Exception{
        u.register=new Immediate(Integer.valueOf(u.value));
    }
    public void visit(StringLiteralNode u)throws Exception{
        StringData data=irroot.strings.get(u.name);
        if(data==null){
            data=new StringData(u.name);
            irroot.strings.put(u.name,data);
        }
        u.register=data;
    }
        /*
    public void visit(VariableDecl node) {
        if (node.scope instanceof ClassSymbol) return;
        if (node.scope instanceof GlobalSymbolTable) {
            GlobalReg globalVarAddr = new GlobalReg(node.varName);
            module.globalRegs.add(globalVarAddr);
            node.var.reg = globalVarAddr;
            if (node.init != null) {
                curFunc = module.funcs.get("__globalInit");
                curBB = curInitBB;
                processVariableDeclInit(globalVarAddr, node.init);
                curInitBB = curBB;
            }
            return;
        }

        LocalReg varAddr = curFunc.makeLocalReg("varAddr");
        node.var.reg = varAddr;
        curFunc.getStartBB().appendFront(new Alloca(curFunc.getStartBB(), varAddr, 8));
        if (node.init != null) {
            processVariableDeclInit(varAddr, node.init);
        }
    }
    public void visit(ArefNode u)throws Exception{
        boolean adflag=AddressFlag;
        AddressFlag=false;
        visit(u.exprname);
        visit(u.exprexpr);
        AddressFlag=adflag;
        Value tmp=new Immediate(((ArrayType)u.exprname.type).deeptype.size);
        VirtualRegister register=new VirtualRegister(null);
        CurBlock.add(new BinaryOpIR(CurBlock,register,"*",u.exprexpr.register,tmp));
        CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.exprname.register,register));
        if(AddressFlag==true){
            u.address=register;
            u.offset=4;
        }else{
            CurBlock.add(new Load(CurBlock,register,u.type.size,register,4));
            u.register=register;
            if(u.trueblock!=null)CurBlock.addend(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(AssignNode u)throws Exception{
        if(LogicalJudge(u.exprr)){
            u.exprr.trueblock=new IRBlock(CurFunction,null);
            u.exprr.falseblock=new IRBlock(CurFunction,null);
        }
        visit(u.exprr);
        boolean memoryflag=MemoryJudge(u.exprl);
        AddressFlag=memoryflag;
        visit(u.exprl);
        AddressFlag=false;
        Value addr;
        if(memoryflag==true)addr=u.exprl.address;else addr=u.exprl.register;
        int offset=0;
        if(memoryflag==true)offset=u.exprl.offset;
        Assign(memoryflag,u.exprr.type.size,addr,offset,u.exprr);
        u.register=u.exprr.register;
    }
    public void visit(BinaryOpNode u)throws Exception{
        String op=u.operator;
        if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals(">>")||op.equals("<<")||
                op.equals("&")||op.equals("|")||op.equals("^")){
            if(u.type instanceof StringType){
                //to do ...............................................
            }else{
                visit(u.exprl);
                visit(u.exprr);
                VirtualRegister register=new VirtualRegister(null);
                u.register=register;
                CurBlock.add(new BinaryOpIR(CurBlock,register,u.operator,u.exprl.register,u.exprr.register));
            }
        }
        if(op.equals("&&")||op.equals("||")){
            if(op.equals("&&")) {
                u.exprl.trueblock=new IRBlock(CurFunction,"lhs_true");
                u.exprl.falseblock=u.falseblock;
                visit(u.exprl);
                CurBlock=u.exprl.trueblock;
            }else{
                u.exprl.trueblock=u.trueblock;
                u.exprl.falseblock=new IRBlock(CurFunction,"lhs_false");
                visit(u.exprl);
                CurBlock=u.exprl.falseblock;
            }
            u.exprr.trueblock=u.trueblock;
            u.exprr.falseblock=u.falseblock;
            visit(u.exprr);
        }
        if(op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!=")){
            if(u.exprl.type instanceof StringType){
                //to do ..................................
            }else{
                visit(u.exprl);
                visit(u.exprr);
                VirtualRegister register=new VirtualRegister(null);
                CurBlock.add(new CmpIR(CurBlock,register,u.operator,u.exprl.register,u.exprr.register));
                if(u.trueblock!=null){
                    CurBlock.addend(new Branch(CurBlock,register,u.trueblock,u.falseblock));
                }else{
                    u.register=register;
                }
            }
        }
    }
    public void visit(BlockNode u)throws Exception{
        for(StmtNode o:u.stmts)visit(o);
    }
    public void visit(BoolLiteralNode u)throws Exception{
        if(u.flag==true)u.register=new Immediate(1);else u.register=new Immediate(0);
    }
    public void visit(BreakNode u)throws Exception{
        CurBlock.add(new Jump(CurBlock,CurLoopAfterBlock));
    }
    public void visit(ClassDefNode u)throws Exception{
        //to do ................................................
    }
    public void visit(ContinueNode u)throws Exception{
        CurBlock.add(new Jump(CurBlock,CurLoopBlock));
    }
    public void visit(CreatorNode u)throws Exception{
        VirtualRegister register=new VirtualRegister(null);
        if(u.type instanceof ClassType){
            //to do .......................................
        }else{
            ExprNode firstv=u.exprs.get(0);
            boolean addressflag=AddressFlag;
            AddressFlag=false;
            visit(firstv);
            AddressFlag=addressflag;
            ArrayType type=(ArrayType)u.type;
            CurBlock.add(new BinaryOpIR(CurBlock,register,"*",firstv.register,new Immediate(type.deeptype.size)));
            CurBlock.add(new BinaryOpIR(CurBlock,register,"+",register,new Immediate(4)));
            CurBlock.add(new Allocation(CurBlock,register,register));
            CurBlock.add(new Store(CurBlock,4,register,firstv.register,0));
        }
        u.register=register;
    }
    public void visit(ForNode u)throws Exception{
        IRBlock condblock=new IRBlock(CurFunction,"for_cond");
        IRBlock loopblock=new IRBlock(CurFunction,"for_loop");
        IRBlock afterblock=new IRBlock(CurFunction,"for_after");
        if(u.mid==null)condblock=loopblock;
        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=condblock;
        CurLoopAfterBlock=afterblock;
        if(u.pre!=null)visit(u.pre);
        CurBlock.addend(new Jump(CurBlock,condblock));
        if(u.mid!=null) {
            CurBlock = condblock;
            u.mid.trueblock = loopblock;
            u.mid.falseblock = afterblock;
            visit(u.mid);
        }
        CurBlock=loopblock;
        visit(u.stmt);
        if(u.suc!=null)visit(u.suc);
        CurBlock.addend(new Jump(CurBlock,condblock));
        CurBlock=afterblock;
        CurLoopBlock=PreLoopBlock;
        CurLoopAfterBlock=PreLoopAfterBlock;
    }
    public void visit(FuncExprNode u)throws Exception{
        //to do   string define size and more .............
        Function function=irroot.functions.get(u.name);
        List<Value>arglist=new ArrayList<>();
        for(ExprNode o:u.exprs){
            visit(o);
            arglist.add(o.register);
        }
        VirtualRegister register=null;
        CurBlock.add(new Call(CurBlock,register,function,arglist));
        u.register=register;
        if(u.trueblock!=null){
            CurBlock.addend(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(FunctionDefNode u)throws Exception{
        CurFunctionStaticMap.clear();
        CurFunction=new Function(u.name,u.type,u.type.size,u.variables);
        exitblock=new IRBlock(CurFunction,CurFunction.name+".exit");
        irroot.functions.put(u.name,CurFunction);
        CurBlock=CurFunction.startblock;
        if(u.type instanceof VoidType){
            returnvalue=null;
        }else{
            returnvalue=new VirtualRegister("returnvalue");
            CurBlock.add(new Move(CurBlock,returnvalue,new Immediate(0)));
        }
        visit(u.block);
        exitblock.addend(new Return(exitblock,returnvalue));
    }
    public void visit(IfNode u)throws Exception{
        IRBlock trueblock=new IRBlock(CurFunction,"if_true");
        IRBlock falseblock=null;
        if(u.elsestmt!=null)falseblock=new IRBlock(CurFunction,"if_false");
        IRBlock mergeblock=new IRBlock(CurFunction,"if_merge");
        u.expr.trueblock=trueblock;
        if(u.elsestmt==null)u.expr.falseblock=mergeblock;else u.expr.falseblock=falseblock;
        visit(u.expr);
        CurBlock=trueblock;
        visit(u.ifstmt);
        CurBlock.addend(new Jump(CurBlock,mergeblock));
        if(u.elsestmt!=null){
            CurBlock=falseblock;
            visit(u.elsestmt);
            CurBlock.addend(new Jump(CurBlock,mergeblock));
        }
        CurBlock=mergeblock;
    }
    public void visit(IntLiteralNode u)throws Exception{
        u.register=new Immediate(Integer.valueOf(u.value));
    }
    public void visit(MemberNode u)throws Exception{
        //to do ...................................
    }
    public void visit(NullLiteralNode u)throws Exception{
        u.register=new Immediate(0);
    }
    public void visit(NullStmtNode u)throws Exception{

    }
    public void visit(PrefixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }

    public void visit(ReturnNode u)throws Exception{
        if(u.expr==null){
            CurBlock.add(new Jump(CurBlock,exitblock));
        }else{
            if(LogicalJudge(u.expr)){
                u.expr.trueblock=new IRBlock(CurFunction,null);
                u.expr.falseblock=new IRBlock(CurFunction,null);
                visit(u.expr);
                //VirtualRegister reg=new VirtualRegister("returnvalue");
                Assign(false,4,returnvalue,0,u.expr);
                CurBlock.addend(new Jump(CurBlock,exitblock));
            }else{
                visit(u.expr);
                CurBlock.add(new Move(CurBlock,returnvalue,u.expr.register));
                CurBlock.addend(new Jump(CurBlock,exitblock));
            }
        }
    }
    public void visit(StringLiteralNode u)throws Exception{
        StringData data=irroot.strings.get(u.name);
        if(data==null){
            data=new StringData(u.name);
            irroot.strings.put(u.name,data);
        }
        u.register=data;
    }
    public void visit(SuffixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }
    public void visit(UnaryOpNode u)throws Exception {
        String op = u.operator;
        if (op == "!") {
            u.expr.trueblock = u.trueblock;
            u.expr.falseblock = u.falseblock;
            visit(u.expr);
            return;
        }
        visit(u.expr);
        if (op .equals("+")) {
            u.register = u.expr.register;
        }
        if (op.equals("-")||op.equals("~")) {
            VirtualRegister register = new VirtualRegister(null);
            CurBlock.add(new UnaryOpIR(CurBlock, register, op, u.expr.register));
            u.register=register;
        }
        if (op.equals("++")) {
            VirtualRegister register = new VirtualRegister(null);
            CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            CurBlock.add(new Move(CurBlock,(Register)u.expr.register,register));
        }
        if(op.equals("--")){
            VirtualRegister register = new VirtualRegister(null);
            CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            CurBlock.add(new Move(CurBlock,(Register)u.expr.register,register));
        }
    }
    public void visit(VariableNode u)throws Exception{
        Information information=u.belong.get(u.name);
        u.register=information.register;
        if(u.trueblock!=null){
            CurBlock.addend(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    public void visit(WhileNode u)throws Exception{
        IRBlock condblock=new IRBlock(CurFunction,"while_cond");
        IRBlock loopblock=new IRBlock(CurFunction,"while_loop");
        IRBlock afterblock=new IRBlock(CurFunction,"while_after");
        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=condblock;
        CurLoopAfterBlock=afterblock;
        CurBlock.addend(new Jump(CurBlock,condblock));
        CurBlock=condblock;
        u.expr.trueblock=loopblock;
        u.expr.falseblock=afterblock;
        visit(u.expr);
        CurBlock=loopblock;
        visit(u.stmt);
        CurBlock.addend(new Jump(CurBlock,condblock));
        CurBlock=afterblock;
        CurLoopBlock=PreLoopBlock;
        CurLoopAfterBlock=PreLoopAfterBlock;
    }*/
}