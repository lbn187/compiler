package com.frontend;
import com.AST.*;
import java.util.List;

abstract public class ASTVisitor {
    public void visit(Node u){
        if(u instanceof ExprNode)visit((ExprNode)u);
        else if(u instanceof StmtNode)visit((StmtNode)u);
        else if(u instanceof ProgramNode)visit((ProgramNode)u);
    }
    public void visit(ExprNode u){
        if(u instanceof AssignNode)visit((AssignNode)u);
        else if(u instanceof BinaryOpNode)visit((BinaryOpNode)u);
        else if(u instanceof FuncExprNode)visit((FuncExprNode)u);
        else if(u instanceof LHSNode)visit((LHSNode)u);
        else if(u instanceof LiteralNode)visit((LiteralNode)u);
        else if(u instanceof UnaryOpNode)visit((UnaryOpNode)u);
        else if(u instanceof CreatorNode)visit((CreatorNode)u);
    }
        public void visit(AssignNode u){
            visit(u.exprl);
            visit(u.exprr);
        }
        public void visit(BinaryOpNode u){
            if(u instanceof LogicalAndNode)visit((LogicalAndNode)u);
            else if(u instanceof LogicalOrNode)visit((LogicalOrNode)u);
            else{
                visit(u.exprl);
                visit(u.exprr);
            }
        }
            public void visit(LogicalAndNode u){
                visit(u.exprl);
                visit(u.exprr);
            }
            public void visit(LogicalOrNode u){
                visit(u.exprl);
                visit(u.exprr);
            }
        public void visit(FuncExprNode u){
            for(ExprNode o:u.exprs)
                visit(o);
        }
        public void visit(LHSNode u){
            if(u instanceof ArefNode)visit((ArefNode)u);
            else if(u instanceof MemberNode)visit((MemberNode)u);
            else if(u instanceof VariableNode)visit((VariableNode)u);
        }
            public void visit(ArefNode u){
                visit(u.exprname);
                visit(u.exprexpr);
            }
            public void visit(MemberNode u){
                visit(u.expr);
            }
            public void visit(VariableNode u){
            }
        public void visit(LiteralNode u){
            if(u instanceof IntLiteralNode)visit((IntLiteralNode)u);
            else if(u instanceof StringLiteralNode)visit((StringLiteralNode)u);
            else if(u instanceof BoolLiteralNode)visit((BoolLiteralNode)u);
            else if(u instanceof NullLiteralNode)visit((NullLiteralNode)u);
        }
            public void visit(BoolLiteralNode u){}
            public void visit(IntLiteralNode u){}
            public void visit(NullLiteralNode u){}
            public void visit(StringLiteralNode u){}
        public void visit(UnaryOpNode u){
            if(u instanceof PrefixOpNode)visit((PrefixOpNode)u);
            else if(u instanceof SuffixOpNode)visit((SuffixOpNode)u);
            else{
                visit(u.expr);
            }
        }
            public void visit(PrefixOpNode u){
                visit(u.expr);
            }
            public void visit(SuffixOpNode u){
                visit(u.expr);
            }
        public void visit(CreatorNode u){
            for(ExprNode o:u.exprs)
                visit(o);
        }
    public void visit(StmtNode u){
        if(u instanceof BlockNode)visit((BlockNode)u);
        else if(u instanceof BreakNode)visit((BreakNode)u);
        else if(u instanceof ContinueNode)visit((ContinueNode)u);
        else if(u instanceof ExprStmtNode)visit((ExprStmtNode)u);
        else if(u instanceof ForNode)visit((ForNode)u);
        else if(u instanceof IfNode)visit((IfNode)u);
        else if(u instanceof ReturnNode)visit((ReturnNode)u);
        else if(u instanceof WhileNode)visit((WhileNode)u);
        else if(u instanceof ClassDefNode)visit((ClassDefNode)u);
        else if(u instanceof FunctionDefNode)visit((FunctionDefNode)u);
        else if(u instanceof VariableDefNode)visit((VariableDefNode)u);
        else if(u instanceof NullStmtNode)visit((NullStmtNode)u);
    }
        public void visit(BlockNode u){
            for(StmtNode o:u.stmts)visit(o);
        }
        public void visit(BreakNode u){}
        public void visit(ContinueNode u){}
        public void visit(ExprStmtNode u){
            visit(u.expr);
        }
        public void visit(ForNode u){
            if(u.pre!=null)visit(u.pre);
            if(u.mid!=null)visit(u.mid);
            if(u.suc!=null)visit(u.suc);
            visit(u.stmt);
        }
        public void visit(IfNode u){
            visit(u.expr);
            visit(u.ifstmt);
            visit(u.elsestmt);
        }
        public void visit(ReturnNode u){
            if(u.expr!=null)visit(u.expr);
        }
        public void visit(WhileNode u){
            visit(u.expr);
            visit(u.stmt);
        }
        public void visit(ClassDefNode u){
            for(VariableDefNode o:u.variables)visit(o);
            for(FunctionDefNode o:u.functions)visit(o);
        }
        public void visit(FunctionDefNode u){
            for(VariableDefNode o:u.variables)visit(o);
            visit(u.block);
        }
        public void visit(VariableDefNode u){
            if(u.expr!=null)visit(u.expr);
        }
        public void visit(NullStmtNode u){}
    public void visit(ProgramNode u){
        for(VariableDefNode o:u.variables)visit(o);
        for(ClassDefNode o:u.classes)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);
    }
    /*void visit(Node u){
        if(u instanceof ExprNode)visit((ExprNode)u);
        else if(u instanceof StmtNode)visit((StmtNode)u);
        else if(u instanceof ProgramNode)visit((ProgramNode)u);
    }
    void visit(ExprNode u){
        if(u instanceof AssignNode)visit((AssignNode)u);
        else if(u instanceof BinaryOpNode)visit((BinaryOpNode)u);
        else if(u instanceof FuncExprNode)visit((FuncExprNode)u);
        else if(u instanceof LHSNode)visit((LHSNode)u);
        else if(u instanceof LiteralNode)visit((LiteralNode)u);
        else if(u instanceof UnaryOpNode)visit((UnaryOpNode)u);
    }
    void visit(LHSNode u){
        if(u instanceof ArefNode)visit((ArefNode)u);
        else if(u instanceof MemberNode)visit((MemberNode)u);
        else if(u instanceof VariableNode)visit((VatiableNode)u);
    }
    void visit(LiteralNode u){
        if(u instanceof IntegerLiteralNode)visit((IntegerLiteralNode)u);
        else if(u instanceof StringLiteralNode)visit((StringLiteralNode)u);
        else if(u instanceof BoolLiteralNode)visit((BoolLiteralNode)u);
        else if(u instanceof NullLiteralNode)visit((NullLiteralNode)u);
    }
    void visit(BinaryOpNode u){
        if(u instanceof LogicalAndNode)visit((LogicalAndNode)u);
        else if(u instanceof LogicalOrNode)visit((LogicalOrNode)u);
        else{

        }
    }
    void visit(UnaryOpNode u){
        if(u instanceof PrefixOpNode)visit((PrefixOpNode)u);
        else if(u instanceof SuffixOpNode)visit((SuffixOpNode)u);
        else{

        }
    }
    void visit(StmtNode u){
        if(u instanceof BlockNode)visit((BlockNode)u);
        else if(u instanceof BreakNode)visit((BreakNode)u);
        else if(u instanceof ContinueNode)visit((ContinueNode)u);
        else if(u instanceof ExprStmtNode)visit((ExprStmtNode)u);
        else if(u instanceof ForNode)visit((ForNode)u);
        else if(u instanceof IfNode)visit((IfNode)u);
        else if(u instanceof ReturnNode)visit((ReturnNode)u);
        else if(u instanceof WhileNode)visit((WhileNode)u);
        else if(u instanceof ClassDefNode)visit((ClassDefNode)u);
        else if(u instanceof FunctionDefNode)visit((FunctionDefNode)u);
        else if(u instanceof VariableDefNode)visit((VariableDefNode)u);
    }*/
}
