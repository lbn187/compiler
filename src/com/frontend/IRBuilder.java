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
public class IRBuilder extends ASTVisitor{
    public IRBlock CurBlock;
    public IRBlock CurLoopBlock;
    public IRBlock CurLoopAfterBlock;
    public IRBlock exitblock;
    public Function CurFunction;
    public boolean AddressFlag=false;
    //public boolean FunctionArgFlag=false;
    public IRRoot irroot=new IRRoot();
    public VirtualRegister returnvalue;
    public Map<StaticData,VirtualRegister> CurFunctionStaticMap=new HashMap<>();
    public IRBuilder(){

    }
    public void getIR(Node root)throws Exception{
        visit(root);
    }
    public void Assign(boolean memflag,int size,Value address,int offset,ExprNode expr){
        if(expr.trueblock==null){
            if(memflag==true){
                CurBlock.add(new Store(CurBlock,size,address,offset,expr.register));
            }else{
                CurBlock.add(new Move(CurBlock,(Register)address,expr.register));
            }
        }else{
            IRBlock blk=new IRBlock(CurFunction,null);
            if(memflag==true){
                expr.trueblock.add(new Store(CurBlock,size,address,offset,new Immediate(1)));
                expr.falseblock.add(new Store(CurBlock,size,address,offset,new Immediate(0)));
            }else{
                expr.trueblock.add(new Move(CurBlock,(VirtualRegister)address,new Immediate(1)));
                expr.falseblock.add(new Move(CurBlock,(VirtualRegister)address,new Immediate(0)));
            }
            expr.trueblock.addend(new Jump(CurBlock,blk));
            expr.falseblock.addend(new Jump(CurBlock,blk));
            CurBlock=blk;
        }
    }
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
    public void visit(ArefNode u)throws Exception{
        //to do ...................................................
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
        if(op=="+"||op=="-"||op=="*"||op=="/"||op=="%"||op==">>"||op=="<<"||op=="&"||op=="|"||op=="^"){
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
        if(op=="&&"||op=="||"){
            if(op=="&&") {
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
        if(op=="<"||op=="<="||op==">"||op==">="||op=="=="||op=="!="){
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
        //to do ..................................
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
        //to do   if trueblock!=null ..................
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
    public void visit(ProgramNode u)throws Exception{
        for(Node o:u.son){
            visit(o);
        }
        //for(Node o:u.sons){
        //    if(!(o instanceof VariableDefNode))visit(o);
       // }
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
        //to do .................
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
        VirtualRegister register = null;
        visit(u.expr);
        if (op == "+") {
            u.register = u.expr.register;
        }
        if (op == "-" || op == "~") {
            CurBlock.add(new UnaryOpIR(CurBlock, register, op, u.expr.register));
            u.register=register;
        }
        if (op == "++") {
            CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            u.register=register;
        }
        if(op=="--"){
            CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            u.register=register;
        }
    }
    public void visit(VariableDefNode u)throws Exception{
        Scope scope=u.belong.getscope(u.name);
        Information information=u.belong.get(u.name);
        if(scope.father==null){//quanju
            StaticData data=new ArraySpace(u.name,information.type.size);
            information.register=data;
            irroot.datas.add(data);
        }else{//jubu
            VirtualRegister register=new VirtualRegister(u.name);
            information.register=register;
            if(u.expr==null){
                CurBlock.add(new Move(CurBlock,register,new Immediate(0)));
            }else{
                if(LogicalJudge(u.expr)){
                    u.expr.trueblock=new IRBlock(CurFunction,null);
                    u.expr.falseblock=new IRBlock(CurFunction,null);
                }
                u.expr.accept(this);
                Assign(false,u.expr.type.size,register,0,u.expr);
            }
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
    }
}