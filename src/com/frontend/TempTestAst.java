package com.frontend;
import com.AST.*;
import java.util.List;

public class TempTestAst {
    private String delta = "    ";
    public void dfs(Node u,String s){
        if(u instanceof  ExprNode)dfs((ExprNode)u,s);
        else if(u instanceof StmtNode)dfs((StmtNode)u,s);
        else if(u instanceof ProgramNode)dfs((ProgramNode)u,s);
        else System.out.println("DFSNode ERROR");
    }
    public void dfs(ExprNode u,String s){
        if(u instanceof AssignNode)dfs((AssignNode)u,s);
        else if(u instanceof BinaryOpNode)dfs((BinaryOpNode)u,s);
        else if(u instanceof CreatorNode)dfs((CreatorNode)u,s);
        else if(u instanceof FuncExprNode)dfs((FuncExprNode)u,s);
        else if(u instanceof LHSNode)dfs((LHSNode)u,s);
        else if(u instanceof LiteralNode)dfs((LiteralNode)u,s);
        else System.out.println("DFSEExprNode ERROR");
    }
    public void dfs(LHSNode u,String s){
        if(u instanceof ArefNode)dfs((ArefNode)u,s);
        else if(u instanceof MemberNode)dfs((MemberNode)u,s);
        else if(u instanceof VariableNode)dfs((VariableNode)u,s);
        else System.out.println("DFSLHSNode ERROR");
    }
    public void dfs(LiteralNode u,String s){
        if(u instanceof BoolLiteralNode)dfs((BoolLiteralNode)u,s);
        else if(u instanceof IntLiteralNode)dfs((IntLiteralNode)u,s);
        else if(u instanceof NullLiteralNode)dfs((NullLiteralNode)u,s);
        else if(u instanceof StringLiteralNode)dfs((StringLiteralNode)u,s);
        else System.out.println("DFSLiteralNode ERROR");
    }
    public void dfs(StmtNode u,String s){
        if(u instanceof BlockNode)dfs((BlockNode)u,s);
        else if(u instanceof BreakNode)dfs((BreakNode)u,s);
        else if(u instanceof ClassDefNode)dfs((ClassDefNode)u,s);
        else if(u instanceof ContinueNode)dfs((ContinueNode)u,s);
        else if(u instanceof ExprStmtNode)dfs((ExprStmtNode)u,s);
        else if(u instanceof ForNode)dfs((ForNode)u,s);
        else if(u instanceof FunctionDefNode)dfs((FunctionDefNode)u,s);
        else if(u instanceof IfNode)dfs((IfNode)u,s);
        else if(u instanceof ReturnNode)dfs((ReturnNode)u,s);
        else if(u instanceof VariableDefNode)dfs((VariableDefNode)u,s);
        else if(u instanceof WhileNode)dfs((WhileNode)u,s);
        else System.out.println("DFSStmtNode ERROR");
    }
    public void dfs(ArefNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.exprname,s);
        dfs(u.exprexpr,s);
    }
    public void dfs(AssignNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.exprl,s);
        dfs(u.exprr,s);
    }
    public void dfs(BinaryOpNode u,String s){
            s = s + delta;
            u.print(s);
            dfs(u.exprl, s);
            dfs(u.exprr, s);
    }
    public void dfs(BlockNode u,String s){
        s=s+delta;
        u.print(s);
       // for(Object o:u.stmts)dfs(o,s);
    }
    public void dfs(BoolLiteralNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(BreakNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(ClassDefNode u,String s){
        s=s+delta;
        u.print(s);
        for(VariableDefNode o:u.variables)dfs(o,s);
        for(FunctionDefNode o:u.functions)dfs(o,s);
    }
    public void dfs(ContinueNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(CreatorNode u,String s){
        s=s+delta;
        u.print(s);
        //dfs(u.expr,s);
    }
    public void dfs(ExprStmtNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
    }
    public void dfs(ForNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.pre,s);
        dfs(u.mid,s);
        dfs(u.suc,s);
        dfs(u.stmt,s);
    }
    public void dfs(FuncExprNode u,String s){
        s=s+delta;
        u.print(s);
        for(ExprNode o:u.exprs)
            dfs(o,s);
    }
    public void dfs(FunctionDefNode u,String s){
        s=s+delta;
        u.print(s);
        for(VariableDefNode o:u.variables)
            dfs(o,s);
        dfs(u.block,s);
    }
    public void dfs(IfNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
        dfs(u.ifstmt,s);
        dfs(u.elsestmt,s);
    }
    public void dfs(IntLiteralNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(MemberNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
        //dfs(u.member,s);
    }
    public void dfs(NullLiteralNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(PrefixOpNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
    }
    public void dfs(ProgramNode u,String s){
        s=s+delta;
        u.print(s);
        for(Node o:u.son)
            dfs(o,s);
        /*for(VariableDefNode o:u.variables)
            dfs(o,s);
        for(ClassDefNode o:u.classes)
            dfs(o,s);
        for(FunctionDefNode o:u.functions)
            dfs(o,s);*/
    }
    public void dfs(ReturnNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
    }
    public void dfs(StringLiteralNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(SuffixOpNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
    }
    public void dfs(UnaryOpNode u,String s) {
        if(u instanceof PrefixOpNode)dfs((PrefixOpNode)u,s);
        else if(u instanceof SuffixOpNode)dfs((SuffixOpNode)u,s);
        else {
            s = s + delta;
            u.print(s);
            dfs(u.expr, s);
        }
    }
    public void dfs(VariableDefNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
    }
    public void dfs(VariableNode u,String s){
        s=s+delta;
        u.print(s);
    }
    public void dfs(WhileNode u,String s){
        s=s+delta;
        u.print(s);
        dfs(u.expr,s);
        dfs(u.stmt,s);
    }
}