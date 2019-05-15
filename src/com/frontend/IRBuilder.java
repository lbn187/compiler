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
import com.frontend.SemanticChecker;
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
    public List<Value> Regs=new ArrayList<>();
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
            if(op.equals("&&")||op.equals("||"))return true;else return false;
        }else if(u instanceof UnaryOpNode){
            String op=((UnaryOpNode)u).operator;
            if(op.equals("!"))return true;else return false;
        }else return false;
    }
    public boolean MemoryJudge(ExprNode u){
        if(u instanceof ArefNode)return true;
        if(u instanceof MemberNode)return true;
        return false;
    }
    public void visitexprprocess(ExprNode u)throws Exception{
        if(LogicalJudge(u)){
            u.trueblock=CurFunction.AddBlock("expr_bool_true");
            u.falseblock=CurFunction.AddBlock("expr_bool_false");
            IRBlock mergeblock=CurFunction.AddBlock("expr_bool_merge");
            CurBlock.AddNext(u.trueblock);
            u.trueblock.AddNext(u.falseblock);
            u.falseblock.AddNext(mergeblock);
            VirtualRegister reg=CurFunction.AddVirtualRegister("BoolValueAddress");
            CurFunction.head.addfront(new Allocation(CurFunction.head,reg,8));
            visit(u);
            u.trueblock.add(new Store(u.trueblock,reg,new Immediate(1)));
            u.trueblock.add(new Jump(u.trueblock,mergeblock));
            u.falseblock.add(new Store(u.falseblock,reg,new Immediate(0)));
            u.falseblock.add(new Jump(u.falseblock,mergeblock));
            VirtualRegister val=CurFunction.AddVirtualRegister("BoolValue");
            mergeblock.add(new Load(mergeblock,val,reg));
            u.register=val;
            CurBlock=mergeblock;
        }else visit(u);
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
                visitexprprocess(u.expr);
                CurBlock.add(new Store(CurBlock,reg,u.expr.register));
                InitBlock=CurBlock;
            }
        }else {//jubu
            VirtualRegister reg = CurFunction.AddVirtualRegister("VariableAddress");
            information.register = reg;
            CurFunction.head.addfront(new Allocation(CurFunction.head, reg, 8));
            if (u.expr != null) {
                visitexprprocess(u.expr);
                CurBlock.add(new Store(CurBlock, reg, u.expr.register));
            }
        }
    }
    public void visit(FunctionDefNode u)throws Exception {
        String name = u.belong.name + "_" + u.name;
        if(u.belong.name.startsWith("."))name="__"+u.belong.name.substring(1)+"_" + u.name;
        Type type=u.belong.map.get(u.name).type;
        if(!(type instanceof FunctionDefineType))System.out.println(u.name+"-------------------------");
        CurFunction = new Function(name, false);
        ((FunctionDefineType)type).ir=CurFunction;
        ExitBlock = CurFunction.AddBlock(CurFunction.name + ".exit");
        irroot.functions.put(name, CurFunction);
        CurBlock = CurFunction.head;
        CurBlock.AddNext(ExitBlock);
        if (ClassFlag == true) {
            VirtualRegister ThisValue = CurFunction.AddVirtualRegister("ThisValue");
            CurFunction.args.add(ThisValue);
            ThisAddress = CurFunction.AddVirtualRegister("ThisAddress");
            CurFunction.head.addfront(new Allocation(CurFunction.head, ThisAddress, 8));
            CurBlock.add(new Store(CurBlock,ThisAddress,ThisValue));
        }
        for (VariableDefNode o : u.variables) {
            VirtualRegister ArgValue = CurFunction.AddVirtualRegister("ArgValue");
            CurFunction.args.add(ArgValue);
            VirtualRegister ArgAddress = CurFunction.AddVirtualRegister("ArgAddress");
            Information information = o.belong.get(o.name);
            information.register = ArgAddress;
            CurFunction.head.addfront(new Allocation(CurFunction.head, ArgAddress, 8));
            CurBlock.add(new Store(CurBlock, ArgAddress, ArgValue));
        }
        if (u.type instanceof VoidType) {
            returnvalue = null;
        } else {
            returnvalue = CurFunction.AddVirtualRegister("ReturnValue");
            CurBlock.addfront(new Allocation(CurBlock,returnvalue,8));
            CurBlock.add(new Move(CurBlock, returnvalue, new Immediate(0)));
        }
        visit(u.block);
        CurBlock.add(new Jump(CurBlock,ExitBlock));
        //CurFunction.tail.add(new Jump(CurFunction.tail,ExitBlock));
        ExitBlock.add(new Return(ExitBlock, returnvalue));
        //CurFunction.Append(ExitBlock);
    }
    public void visit(ClassDefNode u)throws Exception{
        ClassFlag=true;
        for(FunctionDefNode o:u.functions)visit(o);
        ClassFlag=false;
    }
    public void visit(BlockNode u)throws Exception{
        for(StmtNode o:u.stmts)visit(o);
    }
    public void visit(ExprStmtNode u)throws Exception{
        visitexprprocess(u.expr);
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
        IRBlock stepblock=CurFunction.AddBlock("for_step");
        IRBlock afterblock=CurFunction.AddBlock("for_after");
        CurBlock.AddNext(condblock);
        condblock.AddNext(loopblock);
        loopblock.AddNext(stepblock);
        stepblock.AddNext(afterblock);
        /*if(u.mid==null){
            condblock=loopblock;
            CurBlock.AddNext(loopblock);
        }else {
            CurBlock.AddNext(condblock);
            condblock.AddNext(loopblock);
        }*/
        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=stepblock;
        CurLoopAfterBlock=afterblock;
        if(u.pre!=null)visitexprprocess(u.pre);
        CurBlock.add(new Jump(CurBlock,condblock));
        CurBlock=condblock;
        if(u.mid!=null) {
            u.mid.trueblock = loopblock;
            u.mid.falseblock = afterblock;
            visit(u.mid);
        }else CurBlock.add(new Jump(CurBlock,loopblock));
        CurBlock=loopblock;
        visit(u.stmt);
        CurBlock.add(new Jump(CurBlock,stepblock));
        CurBlock=stepblock;
        if(u.suc!=null)visitexprprocess(u.suc);
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
            visitexprprocess(u.expr);
            CurBlock.add(new Move(CurBlock,returnvalue,u.expr.register));
            CurBlock.add(new Jump(CurBlock,ExitBlock));
        }
    }

    public Value GetLhsAddress(ExprNode u) throws Exception{
        if(u instanceof VariableNode){
            VariableNode o=(VariableNode)u;
            if(o.name.equals("this"))return ThisAddress;
            //System.out.println(o.belong.name);
            if(o.belong.getscope(u.name).classflag==true){//is in a class
                VirtualRegister reg=CurFunction.AddVirtualRegister("ClassPtr");
                CurBlock.add(new Load(CurBlock,reg,ThisAddress));
                Immediate offset=new Immediate(o.belong.get(u.name).offset);
                VirtualRegister Address=CurFunction.AddVirtualRegister("MemberAddress");
                CurBlock.add(new BinaryOpIR(CurBlock,Address,"+",reg,offset));
                return Address;
            }
            return o.belong.get(o.name).register;
        }else
        if(u instanceof ArefNode){
            ArefNode o=(ArefNode)u;
            VirtualRegister ptr;
            if(o.exprname instanceof CreatorNode || o.exprname instanceof FuncExprNode){
                visitexprprocess(o.exprname);
                ptr=(VirtualRegister)o.exprname.register;
            }else{
                Value ptrptr=GetLhsAddress(o.exprname);
                ptr=CurFunction.AddVirtualRegister("ArrayPtr");
                CurBlock.add(new Load(CurBlock,ptr,ptrptr));
            }
            VirtualRegister base=CurFunction.AddVirtualRegister("ArrayBase");
            CurBlock.add(new BinaryOpIR(CurBlock,base,"+",ptr,new Immediate(8)));//store the ptr
            visitexprprocess(o.exprexpr);
            VirtualRegister off=CurFunction.AddVirtualRegister("Offset");
            CurBlock.add(new BinaryOpIR(CurBlock,off,"*",o.exprexpr.register,new Immediate(8)));
            VirtualRegister elementaddress=CurFunction.AddVirtualRegister("ElementAddress");
            CurBlock.add(new BinaryOpIR(CurBlock,elementaddress,"+",base,off));
            return elementaddress;
        }else
        if(u instanceof MemberNode){
            MemberNode o=(MemberNode)u;
            if(o.expr instanceof StringLiteralNode){
                visitexprprocess(o.expr);
                return o.expr.register;
            }
            if((o.expr instanceof FuncExprNode)&&(o.type instanceof FunctionDefineType)){
                visitexprprocess(o.expr);
                return o.expr.register;
            }
            Value ptrptr=GetLhsAddress(o.expr);
            VirtualRegister ptr=CurFunction.AddVirtualRegister("ClassPtr");
            CurBlock.add(new Load(CurBlock,ptr,ptrptr));
            if(o.type instanceof FunctionDefineType)return ptr;
            /*if(o.expr.type instanceof ArrayType){
                Type type=o.belong.get("array."+u.name).type;
            }else if(o.expr.type instanceof StringType){
                Type type=o.belong.get("string."+u.name).type;
            }else if(o.expr.type instanceof ClassType){*/
                ClassDefineType classtype=(ClassDefineType)ScopeBuilder.scoperoot.get(o.expr.type.typename).type;
                Immediate off=new Immediate(classtype.get(o.name).offset);
                VirtualRegister addr=CurFunction.AddVirtualRegister("MemberAddress");
                CurBlock.add(new BinaryOpIR(CurBlock,addr,"+",ptr,off));
            //}
            //Immediate off=new Immediate(((ClassDefineType)o.expr.type).get(o.name).offset);
            //VirtualRegister addr=CurFunction.AddVirtualRegister("MemberAddress");
            //CurBlock.add(new BinaryOpIR(CurBlock,addr,"+",ptr,off));
            return addr;
        }else System.out.println("GetLhsAddress Wrong");
        return null;
    }
    public void visit(AssignNode u)throws Exception{
        visitexprprocess(u.exprr);
        Value address=GetLhsAddress(u.exprl);
        CurBlock.add(new Store(CurBlock,address,u.exprr.register));
    }
    public void visit(BinaryOpNode u)throws Exception{
        String op=u.operator;
        if(u.exprl.type instanceof StringType){
            visitexprprocess(u.exprl);
            visitexprprocess(u.exprr);
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
        if(op.equals("&&")||op.equals("||")){
            if(op.equals("&&")) {
                u.exprl.trueblock=CurFunction.AddBlock("lhs_true");
                u.exprl.falseblock=u.falseblock;
                CurBlock.AddNext(u.exprl.trueblock);
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
        if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals(">>")||op.equals("<<")||
                op.equals("&")||op.equals("|")||op.equals("^")||op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!=")){
            visitexprprocess(u.exprl);
            visitexprprocess(u.exprr);
            if((u.exprl.register instanceof Immediate)&&(u.exprr.register instanceof Immediate)){
                u.register=new Immediate(ConstantFolding(op,((Immediate)u.exprl.register).value,((Immediate)u.exprr.register).value));
            }else {
                VirtualRegister register = CurFunction.AddVirtualRegister("res");
                u.register = register;
                CurBlock.add(new BinaryOpIR(CurBlock, register, u.operator, u.exprl.register, u.exprr.register));
            }
            if((op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")||op.equals("==")||op.equals("!="))&&u.trueblock!=null){
                CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
            }
        }
    }
    public void visit(SuffixOpNode u)throws Exception{
        /*String op=u.operator;
        if (op.equals("++")) {
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"++",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=prev;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
            //TODO youhua
        }else
        if(op.equals("--")){
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"--",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=prev;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
        }else System.out.println("-----GGGGGGGGGGGG");*/
        visit((UnaryOpNode)u);
    }
    public void visit(PrefixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
        /*String op=u.operator;
        if (op.equals("++")) {
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"++",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=nowv;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
            //TODO youhua
        }else
        if(op.equals("--")){
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"--",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=nowv;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
        }else System.out.println("-----GGGGGGGGGGGG");*/
    }
    public void visit(UnaryOpNode u)throws Exception {
        String op = u.operator;
        if (op.equals("!")) {
            u.expr.trueblock = u.falseblock;
            u.expr.falseblock = u.trueblock;
            visit(u.expr);
            return;
        }
        visitexprprocess(u.expr);
        if (op .equals("+")) {
            u.register = u.expr.register;
        }else
        if (op.equals("-")||op.equals("~")) {
            VirtualRegister register = CurFunction.AddVirtualRegister("res");
            CurBlock.add(new UnaryOpIR(CurBlock, register, op, u.expr.register));
            u.register=register;
        }else
        if (op.equals("+++")) {
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"++",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=nowv;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
            //TODO youhua
        }else
        if(op.equals("---")){
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"--",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=nowv;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
        }else if (op.equals("++")) {
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"++",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=prev;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"+",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
            //TODO youhua
        }else
        if(op.equals("--")){
            Value addr=GetLhsAddress(u.expr);
            VirtualRegister prev=CurFunction.AddVirtualRegister("PreValue");
            VirtualRegister nowv=CurFunction.AddVirtualRegister("NowValue");
            CurBlock.add(new Load(CurBlock,prev,addr));
            CurBlock.add(new UnaryOpIR(CurBlock,nowv,"--",prev));
            CurBlock.add(new Store(CurBlock,addr,nowv));
            u.register=prev;
            //CurBlock.add(new BinaryOpIR(CurBlock,register,"-",u.expr.register,new Immediate(1)));
            //CurBlock.add(new Move(CurBlock,(VirtualRegister)u.expr.register,register));
        }else System.out.println("-----GGGGGGGGGGGG" + op);
    }
    public void visit(FuncExprNode u)throws Exception{
        Type type=u.exprs.get(0).type;
        if(type==ScopeBuilder.STRING_PRINT){
            visitexprprocess(u.exprs.get(1));
            List<Value>args=new ArrayList<>();
            args.add(u.exprs.get(1).register);
            CurBlock.add(new Call(CurBlock,null,ScopeBuilder.STRING_PRINT,args));
            return;
        }
        if(type==ScopeBuilder.STRING_PRINTLN){
            visitexprprocess(u.exprs.get(1));
            List<Value>args=new ArrayList<>();
            args.add(u.exprs.get(1).register);
            CurBlock.add(new Call(CurBlock,null,ScopeBuilder.STRING_PRINTLN,args));
            return;
        }
        if(type==ScopeBuilder.GETSTRING){
            List<Value>args=new ArrayList<>();
            VirtualRegister ptr=CurFunction.AddVirtualRegister("StringPtr");
            CurBlock.add(new Call(CurBlock,ptr,ScopeBuilder.GETSTRING,args));
            u.register=ptr;
            return;
        }
        if(type==ScopeBuilder.GETINT){
            List<Value>args=new ArrayList<>();
            VirtualRegister reg=CurFunction.AddVirtualRegister("res");
            CurBlock.add(new Call(CurBlock,reg,ScopeBuilder.GETINT,args));
            u.register=reg;
            return;
        }
        if(type==ScopeBuilder.TOSTRING){
            visitexprprocess(u.exprs.get(1));
            List<Value>args=new ArrayList<>();
            args.add(u.exprs.get(1).register);
            VirtualRegister reg=CurFunction.AddVirtualRegister("res");
            CurBlock.add(new Call(CurBlock,reg,ScopeBuilder.TOSTRING,args));
            u.register=reg;
            return;
        }
        //WRONG  not belong.name
        List<Value>args=new ArrayList<>();
        if(u.exprs.get(0) instanceof MemberNode){
            args.add(GetLhsAddress(u.exprs.get(0)));
        }else if(u.belong.getscope(u.exprs.get(0).name).classflag==true){
            //TODO  maybe wrong
            //System.out.println("FUNCTION IN CLASS");
            VirtualRegister ptr=CurFunction.AddVirtualRegister("ClassPtr");
            CurBlock.add(new Load(CurBlock,ptr,ThisAddress));
            args.add(ptr);
        }
        /*if(type==ScopeBuilder.STRING_LENGTH||type==ScopeBuilder.STRING_SUBSTRING||type==ScopeBuilder.STRING_PARSEINT||type==ScopeBuilder.STRING_ORD||type==ScopeBuilder.ARRAY_SIZE){
            args.add(GetLhsAddress(u.exprs.get(0)));
        }*/
        for(int i=1;i<u.exprs.size();i++){
            visitexprprocess(u.exprs.get(i));
            args.add(u.exprs.get(i).register);
        }
        VirtualRegister register=null;
        if(!(u.type instanceof VoidType))register=CurFunction.AddVirtualRegister("res");
        CurBlock.add(new Call(CurBlock,register,(FunctionDefineType)type,args));
        u.register=register;
        if((u.type instanceof BoolType)&&u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
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
    public void visit(VariableNode u)throws Exception{
        Value addr=GetLhsAddress(u);
        VirtualRegister reg=CurFunction.AddVirtualRegister("idValue");
        CurBlock.add(new Load(CurBlock,reg,addr));
        u.register=reg;
        if((u.type instanceof BoolType)&&u.trueblock!=null){
            CurBlock.add(new Branch(CurBlock,u.register,u.trueblock,u.falseblock));
        }
    }
    //a=new int[10][][]
    //for(int i=0;i<10;i++)
    //    a[i]=new int[12];
    //    for(int j=0;j<12;j++)

    //qian 8 wei fang zhi zhen
    public Value CalPtr(int nv){
        VirtualRegister ptr=CurFunction.AddVirtualRegister("ArrayPtr");
        Value size=Regs.get(nv);
        VirtualRegister ElementSize=CurFunction.AddVirtualRegister("ElementSize");
        CurBlock.add(new BinaryOpIR(CurBlock,ElementSize,"*",size,new Immediate(8)));
        VirtualRegister AllSize=CurFunction.AddVirtualRegister("AllArraySize");
        CurBlock.add(new BinaryOpIR(CurBlock,AllSize,"+",ElementSize,new Immediate(8)));
        CurBlock.add(new Malloc(CurBlock,ptr,AllSize));
        CurBlock.add(new Store(CurBlock,ptr,size));
        if(nv==(Regs.size())-1)return ptr;
        IRBlock condblock=CurFunction.AddBlock("newarray_for_cond");
        IRBlock loopblock=CurFunction.AddBlock("newarray_for_loop");
        IRBlock stepblock=CurFunction.AddBlock("newarray_for_step");
        IRBlock afterblock=CurFunction.AddBlock("newarray_for_after");

        CurBlock.AddNext(condblock);
        condblock.AddNext(loopblock);
        loopblock.AddNext(stepblock);
        stepblock.AddNext(afterblock);

        IRBlock PreLoopBlock=CurLoopBlock;
        IRBlock PreLoopAfterBlock=CurLoopAfterBlock;
        CurLoopBlock=stepblock;
        CurLoopAfterBlock=afterblock;

        VirtualRegister endposition=CurFunction.AddVirtualRegister("EndPosition");
        CurBlock.add(new BinaryOpIR(CurBlock,endposition,"+",ptr,AllSize));
        VirtualRegister startposition=CurFunction.AddVirtualRegister("StartPosition");
        CurBlock.add(new BinaryOpIR(CurBlock,startposition,"+",ptr,new Immediate(8)));
        VirtualRegister tmp=CurFunction.AddVirtualRegister("Tmp");
        CurFunction.head.addfront(new Allocation(CurFunction.head,tmp,8));
        CurBlock.add(new Store(CurBlock,tmp,startposition));
        //VirtualRegister i=CurFunction.AddVirtualRegister("var");
        //CurFunction.head.addfront(new Allocation(CurFunction.head,i,8));
        //CurBlock.add(new Move(CurBlock,i,new Immediate(0)));
        CurBlock.add(new Jump(CurBlock,condblock));

        CurBlock=condblock;
        VirtualRegister posvalue=CurFunction.AddVirtualRegister("PosValue");
        CurBlock.add(new Load(CurBlock,posvalue,tmp));
        VirtualRegister condition=CurFunction.AddVirtualRegister("Condition");
        CurBlock.add(new BinaryOpIR(CurBlock,condition,"<",posvalue,endposition));
        CurBlock.add(new Branch(CurBlock,condition,loopblock,afterblock));

        CurBlock=loopblock;
        Store store=new Store(CurBlock,posvalue,CalPtr(nv+1));
        CurBlock.add(store);
        CurBlock.add(new Jump(CurBlock,stepblock));

        CurBlock=stepblock;
        VirtualRegister posadd=CurFunction.AddVirtualRegister("pos");
        CurBlock.add(new BinaryOpIR(CurBlock,posadd,"+",posvalue,new Immediate(8)));
        CurBlock.add(new Store(CurBlock,tmp,posadd));
        CurBlock.add(new Jump(CurBlock,condblock));
        CurBlock=afterblock;
        CurLoopBlock=PreLoopBlock;
        CurLoopAfterBlock=PreLoopAfterBlock;
        return ptr;
    }
    public void visit(CreatorNode u)throws Exception {
        if(u.exprs.size()==0){
            VirtualRegister ptr=CurFunction.AddVirtualRegister("ClassPtr");
            Type type=SemanticChecker.scoperoot.get(u.type.typename).type;
            if(!(type instanceof ClassDefineType)){
                throw new Exception("CreatorWrong"+u.loc.toString());
            }
            ClassDefineType classtype=(ClassDefineType)type;
            CurBlock.add(new Malloc(CurBlock,ptr,new Immediate(classtype.offset)));
            //System.out.println("CLASS "+classtype.typename);
            if(classtype.get(classtype.typename)!=null){
                //System.out.println("INIT CLASS");
                Type func=(classtype.get(classtype.typename).type);
                if(!(func instanceof FunctionDefineType)){
                    throw new Exception("CreatorWrong"+u.loc.toString());
                }
                List<Value>args=new ArrayList<>();
                args.add(ptr);
                CurBlock.add(new Call(CurBlock,null,(FunctionDefineType)func,args));
            }
            u.register=ptr;
        }else{
            Regs=new ArrayList<>();
            for(ExprNode o:u.exprs){
                visit(o);
                Regs.add(o.register);
            }
            u.register=CalPtr(0);
        }
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
    public int ConstantFolding(String op,int lv,int rv){
        int tmp=0;
        if(op.equals("<"))tmp=(lv<rv?1:0);
        if(op.equals("<="))tmp=(lv<=rv?1:0);
        if(op.equals(">"))tmp=(lv>rv?1:0);
        if(op.equals(">="))tmp=(lv>=rv?1:0);
        if(op.equals("=="))tmp=(lv==rv?1:0);
        if(op.equals("!="))tmp=(lv!=rv?1:0);
        if(op.equals(">>"))tmp=lv>>rv;
        if(op.equals("<<"))tmp=lv<<rv;
        if(op.equals("+"))tmp=lv+rv;
        if(op.equals("-"))tmp=lv-rv;
        if(op.equals("*"))tmp=lv*rv;
        if(op.equals("/"))tmp=lv/rv;
        if(op.equals("&"))tmp=lv&rv;
        if(op.equals("|"))tmp=lv|rv;
        if(op.equals("^"))tmp=lv^rv;
        if(op.equals("%"))tmp=lv%rv;
        return tmp;
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