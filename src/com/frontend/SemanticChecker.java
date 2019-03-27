package com.frontend;
import com.frontend.ASTVisitor;
import com.AST.*;
import com.Type.*;
public class SemanticChecker extends ASTVisitor {
    public Scope scoperoot;
    Type returntype;
    String ClassName;
    int loopcnt;
    public SemanticChecker(Scope rt) throws Exception{
        scoperoot=rt;
        loopcnt=0;
        ClassName="main";
        returntype=null;
    }
    boolean TypeChecker(Type type)throws Exception{
        if(type instanceof ArrayType)type=((ArrayType)type).deeptype;
        if(type instanceof ClassType)
            if(scoperoot.get(type.typename)==null)return false;
        return true;
    }
    boolean Complex(Type a) throws Exception{
        if((a instanceof ClassDefineType)||(a instanceof FunctionDefineType))return true;
        return false;
    }
    boolean ExprMatching(Type a,Type b) throws Exception{
        if(Complex(a)==true||Complex(b)==true)return false;
        if(a.typename.equals(b.typename))return true;
        return false;
    }
    boolean AssignMatching(Type a,Type b) throws Exception{
        if(a instanceof ArrayType)if(b instanceof NullType)return true;
        if(Complex(a)==true||Complex(b)==true)return false;
        if(a.typename.equals(b.typename))return true;
        if((b instanceof NullType)&&(a instanceof ClassType)&&!(a instanceof StringType))return true;
        return false;
    }
    boolean IsLeft(Node a) throws Exception{
        if(a instanceof LHSNode)return true;
        return false;
    }
    boolean CompareEqualMatching(Type a,Type b) throws Exception{
        if(ExprMatching(a,b))return true;
        if((a instanceof NullType)&&(b instanceof ClassType)&&!(b instanceof StringType))return true;
        if((b instanceof NullType)&&(a instanceof ClassType)&&!(a instanceof StringType))return true;
        return false;
    }
    Type ArrayType(ArrayType type,ArefNode node)throws Exception{
        return new NullType();
    }
    public void visit(AssignNode u) throws Exception{
        visit(u.exprl);
        visit(u.exprr);
        if(!IsLeft(u.exprl)){
            throw new Exception("assign's left not LHS");
        }
        if(u.exprl.name.equals("this")){
            throw new Exception("assign's left is this");
        }
        if(!AssignMatching(u.exprl.type,u.exprr.type)){
            throw new Exception("assign unmatching");
        }
        u.type=new VoidType();
    }
    public void visit(BinaryOpNode u)throws Exception{
        visit(u.exprl);
        visit(u.exprr);
        String op=u.operator;
        u.type=u.exprl.type;

        if(op.equals("+")){
            if(ExprMatching(u.exprl.type,new StringType())&&ExprMatching(u.exprr.type,new StringType())){
                u.type=new StringType();
            }else if(ExprMatching(u.exprl.type,new IntType())&&ExprMatching(u.exprr.type,new IntType())){
                u.type=new IntType();
            }else{
                throw new Exception("binaryop not matching");
            }
        }
        if(op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals("&")||op.equals("|")||op.equals("^")||op.equals(">>")||op.equals("<<")){
            if(!ExprMatching(u.exprl.type,new IntType())||!ExprMatching(u.exprr.type,new IntType())){
                throw new Exception("binaryop not matching");
            }
            u.type=new IntType();
        }
        if(op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")){
            if(!ExprMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("binaryop not matching");
            }
            u.type=new BoolType();
        }
        if(op.equals("==")||op.equals("!=")){
            if(!CompareEqualMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("binaryop not matching");
            }
            u.type=new BoolType();
        }
        if(op.equals("&&")||op.equals("||")){
            if(!ExprMatching(u.exprl.type,new BoolType())||!ExprMatching(u.exprr.type,new BoolType())){
                throw new Exception("binaryop not matching");
            }
            u.type=new BoolType();
        }
    }
    public void visit(FuncExprNode u)throws Exception{
        for(ExprNode o:u.exprs)
            visit(o);
        Type w=scoperoot.get(u.name);
        if(w==null){
            throw new Exception("function undefine");
        }else if(!(w instanceof FunctionDefineType)){
            throw new Exception("not a function");
        }else{
            FunctionDefineType p=(FunctionDefineType)w;
            if(u.exprs.size()!=p.parameters.size()){
                throw new Exception("function number wrong");
            }else{
                boolean flag=true;
                for(int i=0;i<u.exprs.size();i++)
                    if(!ExprMatching(u.exprs.get(i).type,p.parameters.get(i)))flag=false;
                if(flag==false){
                    throw new Exception("function don't matching");
                }
            }
            u.type=p.variable;
        }
    }
    public void visit(LHSNode u)throws Exception{
        if(u instanceof ArefNode)visit((ArefNode)u);
        else if(u instanceof MemberNode)visit((MemberNode)u);
        else if(u instanceof VariableNode)visit((VariableNode)u);
    }
    public void visit(ArefNode u)throws Exception{
        visit(u.exprname);
        visit(u.exprexpr);
        if(!(u.exprname.type instanceof ArrayType)){
            throw new Exception("UnDefine ArrayType");
        }else u.type=((ArrayType)u.exprname.type).basetype;
    }
    public void visit(MemberNode u)throws Exception{
        visit(u.expr);
        if(u.expr.type instanceof ArrayType){
            if(!u.member.name.equals("size")||u.member.getAll().size()!=0){
                throw new Exception("Wrong Array's Size");
            }
            u.type=new IntType();
        }else{
            for(Node o:u.member.getAll())
                visit(o);
            if(!(u.expr.type instanceof ClassType)){
                throw new Exception("MemberWrong");
            }else{
                ClassDefineType classtype=(ClassDefineType)scoperoot.get(u.expr.type.typename);
                Type insidetype=classtype.get(u.member.name);
                if(insidetype==null){
                    throw new Exception("MemberWrong");
                }
                if(u.member instanceof FuncExprNode){
                    FuncExprNode uu=(FuncExprNode)u.member;
                    if(!(insidetype instanceof FunctionDefineType)){
                        throw new Exception("MemberWrong");
                    }else{
                        FunctionDefineType p=(FunctionDefineType)insidetype;
                        if(uu.exprs.size()!=p.parameters.size()){
                            throw new Exception("MemberWrong");
                        }else{
                            boolean flag=true;
                            for(int i=0;i<uu.exprs.size();i++)
                                if(!ExprMatching(uu.exprs.get(i).type,p.parameters.get(i)))flag=false;
                            if(flag==false){
                                throw new Exception("MemberWrong");
                            }
                        }
                        u.type=p.variable;
                    }
                }else if(u.member instanceof ArefNode){
                    if(!(insidetype instanceof ArrayType)){
                        throw new Exception("MemberWrong");
                    }
                    u.type=ArrayType((ArrayType)insidetype,(ArefNode)u.member);
                }else{
                    u.type=insidetype;
                }
            }
        }
    }
    public void visit(VariableNode u)throws Exception{
        if(u.name.equals("this")){
            if(ClassName.equals("main")){
                throw new Exception("THIS not exist");
            }
            u.type=new ClassType(ClassName);
            return;
        }
        Scope o=u.belong;
        u.type=o.get(u.name);
        if(u.type!=null)System.out.println("Variablename="+u.name+" Variabletype="+u.type.typename);
        if(u.type==null){
            throw new Exception("variable not exist");
        }
    }
    //public void visit(BoolLiteralNode u){}
    //public void visit(IntLiteralNode u){}
    //public void visit(NullLiteralNode u){}
    //public void visit(StringLiteralNode u){}
    public void visit(UnaryOpNode u)throws Exception{
        String op=u.operator;
        visit(u.expr);
        if((op.equals("++")||op.equals("--"))&&!IsLeft(u.expr)){
            throw new Exception("not a LHSNode");
        }
        if(op.equals("~")||op.equals("+")||op.equals("-")||op.equals("++")||op.equals("--")){
            if(!ExprMatching(u.expr.type,new IntType())){
                throw new Exception("Type Unmatching");
            }
        }
        if(op.equals("!")){
            if(!ExprMatching(u.expr.type,new BoolType())){
                throw new Exception("Type Unmatching");
            }
        }
        if(op.equals("++")||op.equals("--")||op.equals("~")||op.equals("+")||op.equals("-"))u.type=new IntType();
        if(op.equals("!"))u.type=new BoolType();
    }
    public void visit(PrefixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }
    public void visit(SuffixOpNode u)throws Exception{
        visit((UnaryOpNode)u);
    }
    public void visit(CreatorNode u)throws Exception{
        for(ExprNode o:u.exprs)
            visit(o);
    }
    //public void visit(BlockNode u){
    //    for(StmtNode o:u.stmts)visit(o);
    //}
    public void visit(BreakNode u)throws Exception{
        if(loopcnt==0){
            throw new Exception("BreakWrong");
        }
    }
    public void visit(ContinueNode u)throws Exception{
        if(loopcnt==0){
            throw new Exception("ContinueWrong");
        }
    }
    public void visit(ForNode u)throws Exception{
        loopcnt++;
        if(u.pre!=null)visit(u.pre);
        if(u.mid!=null)visit(u.mid);
        if(u.suc!=null)visit(u.suc);
        visit(u.stmt);
        if(u.mid!=null){
            if(!ExprMatching(u.mid.type,new BoolType())){
                throw new Exception("ForWrong");
            }
        }
        loopcnt--;
    }
    public void visit(IfNode u)throws Exception{
        visit(u.expr);
        visit(u.ifstmt);
        visit(u.elsestmt);
        if(!ExprMatching(u.expr.type,new BoolType())){
            throw new Exception("IFWrong");
        }
    }
    public void visit(ReturnNode u)throws Exception{
        if(u.expr!=null)visit(u.expr);
        if(u.expr==null){
            if(!ExprMatching(returntype,new VoidType())){
                throw new Exception("ReturnWrong");
            }
        }else{
            if(u.expr.type==null){
                if(!(returntype instanceof ArrayType)&&!(returntype instanceof ClassType)){
                    throw new Exception("ReturnWrong");
                }
            }else{
                if(!ExprMatching(returntype,u.expr.type)){
                    throw new Exception("ReturnWrong");
                }
            }
        }
    }
    public void visit(WhileNode u)throws Exception{
        loopcnt++;
        visit(u.expr);
        visit(u.stmt);
        loopcnt--;
        if(!ExprMatching(u.expr.type,new BoolType())){
            throw new Exception("WhileWrong");
        }
    }
    public void visit(ClassDefNode u)throws Exception{
        ClassName=u.name;
        for(VariableDefNode o:u.variables)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);
        ClassName="main";
    }
    public void visit(FunctionDefNode u)throws Exception{
        if(u.type.typename.equals("null")){
            if(!u.name.equals(ClassName)){
                throw new Exception("Construct Name Wrong");
            }
        }else if(!TypeChecker(u.type)){
            throw new Exception("Type Undefined");
        }
        returntype=u.type;
        for(VariableDefNode o:u.variables)visit(o);
        visit(u.block);
        returntype=null;
    }
    public void visit(VariableDefNode u)throws Exception{
        Type type=u.type;
        if(type instanceof VoidType){
            throw new Exception("Void Defination");
        }
        if(type instanceof ArrayType)
            if(((ArrayType)type).deeptype instanceof VoidType){
                throw new Exception("Void Defination");
            }
        if(!TypeChecker(type)){
            throw new Exception("Type Undefined");
        }
        if(u.expr!=null){
            visit(u.expr);
            if(!AssignMatching(type,u.expr.type)){
                throw new Exception("AssignTypeUnmatching");
            }
        }
    }
    public void visit(ProgramNode u)throws Exception{
        Scope tmp=u.belong;
        Type w=u.belong.get("main");
        if(!(w instanceof FunctionDefineType)||!(((FunctionDefineType)w).variable instanceof IntType)){
            throw new Exception("MainWrong");
        }
        for(VariableDefNode o:u.variables)visit(o);
        for(ClassDefNode o:u.classes)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);
    }
}