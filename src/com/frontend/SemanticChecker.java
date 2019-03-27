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
        if(a.equals(b))return true;
        return false;
    }
    boolean AssignMatching(Type a,Type b) throws Exception{
        if(a instanceof ArrayType)if(b instanceof NullType)return true;
        if((b instanceof NullType)&&(a instanceof ClassType)&&!(a instanceof StringType))return true;
        if(a.equals(b))return true;
        return false;
    }
    boolean IsLeft(Node a) throws Exception{
        if(a instanceof LHSNode)return true;
        return false;
    }
    boolean CompareEqualMatching(Type a,Type b) throws Exception{
        if(a.equals(b))return true;
        if((b instanceof NullType)&&(((a instanceof ClassType)&&!(a instanceof StringType))||(a instanceof ArrayType)))return true;
        if((a instanceof NullType)&&(((b instanceof ClassType)&&!(b instanceof StringType))||(b instanceof ArrayType)))return true;
        return false;
    }
    Type ArrayType(ArrayType type,ArefNode node)throws Exception{
        return new NullType();
    }
    Type ArrayChecker(ArrayType type,ArefNode nod){
        Type tmptype=type.basetype;
        Node tmpnode=nod.exprname;
        if(tmptype instanceof ArrayType){
            if(!(tmpnode instanceof ArefNode))return null;
            return ArrayChecker((ArrayType)tmptype,(ArefNode)tmpnode);
        }else{
            if(tmptype.equals(tmpnode.type))return tmptype;
            return null;
        }
    }
    Type FunctionChecker(FunctionDefineType type,FuncExprNode node){
        if(type.parameters.size()!=node.exprs.size())return null;
        for(int i=0;i<type.parameters.size();i++)
            if(!type.parameters.get(i).equals(node.exprs.get(i).type))return null;
        return type.variable;
    }
    public void visit(AssignNode u) throws Exception{
        visit(u.exprl);
        visit(u.exprr);
        if(!IsLeft(u.exprl)){
            throw new Exception("assign's left not LHS"+u.loc.toString());
        }
        if(u.exprl.name.equals("this")){
            throw new Exception("assign's left is this"+u.loc.toString());
        }
        if(!AssignMatching(u.exprl.type,u.exprr.type)){
            throw new Exception("assign unmatching"+u.loc.toString());
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
                throw new Exception("binaryop not matching"+u.loc.toString());
            }
        }
        if(op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals("&")||op.equals("|")||op.equals("^")||op.equals(">>")||op.equals("<<")){
            if(!ExprMatching(u.exprl.type,new IntType())||!ExprMatching(u.exprr.type,new IntType())){
                throw new Exception("binaryop not matching"+u.loc.toString());
            }
            u.type=new IntType();
        }
        if(op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")){
            if(!ExprMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("binaryop not matching"+u.loc.toString());
            }
            u.type=new BoolType();
        }
        if(op.equals("==")||op.equals("!=")){
            if(!CompareEqualMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("binaryop not matching"+u.loc.toString());
            }
            u.type=new BoolType();
        }
        if(op.equals("&&")||op.equals("||")){
            if(!ExprMatching(u.exprl.type,new BoolType())||!ExprMatching(u.exprr.type,new BoolType())){
                throw new Exception("binaryop not matching"+u.loc.toString());
            }
            u.type=new BoolType();
        }
    }
    public void visit(FuncExprNode u)throws Exception{
        for(ExprNode o:u.exprs)
            visit(o);
        Type type=u.belong.get(u.name);
        if(type==null){
            throw new Exception("NoDefineFunc"+u.loc.toString());
        }
        if(!(type instanceof FunctionDefineType)){
            throw new Exception("NoDefineFunc"+u.loc.toString());
        }
        Type tmp=FunctionChecker((FunctionDefineType)type,u);
        if(tmp==null){
            throw new Exception("function don't matching"+u.loc.toString());
        }
        u.type=tmp;
        /*FunctionDefineType p=(FunctionDefineType)type;
        if(u.exprs.size()!=p.parameters.size()){
            throw new Exception("function number wrong"+u.loc.toString());
        }
        for(int i=0;i<u.exprs.size();i++){
            if(!ExprMatching(u.exprs.get(i).type,p.parameters.get(i))){
                throw new Exception("function don't matching"+u.loc.toString());
            }
        }
        u.type=p.variable;*/
        /*if(ClassName.equals("main")) {//problem
            Type w=scoperoot.get(u.name);
            if(w==null){
                throw new Exception("function undefine"+u.loc.toString());
            }else if(!(w instanceof FunctionDefineType)){
                throw new Exception("not a function"+u.loc.toString());
            }else{
                FunctionDefineType p=(FunctionDefineType)w;
                if(u.exprs.size()!=p.parameters.size()){
                    throw new Exception("function number wrong"+u.loc.toString());
                }else{
                    boolean flag=true;
                    for(int i=0;i<u.exprs.size();i++)
                        if(!ExprMatching(u.exprs.get(i).type,p.parameters.get(i)))flag=false;
                    if(flag==false){
                        throw new Exception("function don't matching"+u.loc.toString());
                    }
                }
                u.type=p.variable;
            }
        }else{

            for(Scope o:scoperoot.son){
                if(o.name.equals(ClassName)) {
                    Type w = o.get(u.name);
                    if(w==null){
                        throw new Exception("function undefine"+u.loc.toString());
                    }else if(!(w instanceof FunctionDefineType)){
                        throw new Exception("not a function"+u.loc.toString());
                    }else{
                        FunctionDefineType p=(FunctionDefineType)w;
                        if(u.exprs.size()!=p.parameters.size()){
                            throw new Exception("function number wrong"+u.loc.toString());
                        }else{
                            boolean flag=true;
                            for(int i=0;i<u.exprs.size();i++)
                                if(!ExprMatching(u.exprs.get(i).type,p.parameters.get(i)))flag=false;
                            if(flag==false){
                                throw new Exception("function don't matching"+u.loc.toString());
                            }
                        }
                        u.type=p.variable;
                    }
                }
            }
        }*/

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
            throw new Exception("UnDefine ArrayType"+u.loc.toString());
        }else u.type=((ArrayType)u.exprname.type).basetype;
    }
    public void visit(MemberNode u)throws Exception{
        visit(u.expr);
        if(u.expr.type instanceof ArrayType){
            if(!u.member.name.equals("size")||u.member.getAll().size()!=0){
                throw new Exception("Wrong Array's Size"+u.loc.toString());
            }
            u.type=new IntType();
            return;
        }
        for(Node o:u.member.getAll())visit(o);
        if(!(u.expr.type instanceof ClassType)){
            throw new Exception("MemberWrong1"+u.loc.toString());
        }
        ClassDefineType classtype=(ClassDefineType)scoperoot.get(u.expr.type.typename);
        Type type=classtype.get(u.member.name);
        if(type==null){
            throw new Exception("MemberWrong2"+u.loc.toString());
        }
        if(u.member instanceof FuncExprNode){
            if(!(type instanceof FunctionDefineType)){
                throw new Exception("MemberWrong3"+u.loc.toString());
            }
            Type tmp=FunctionChecker((FunctionDefineType)type,(FuncExprNode)u.member);
            if(tmp==null){
                throw new Exception("MemberWrong4"+u.loc.toString());
            }
            u.type=tmp;
        }else if(u.member instanceof ArefNode){
            if(!(type instanceof ArrayType)){
                throw new Exception("MemberWrong5"+u.loc.toString());
            }
            Type arraytype=ArrayChecker((ArrayType)type,(ArefNode)u.member);
            if(arraytype==null){
                throw new Exception("MemberWrong6"+u.loc.toString());
            }
            u.type=arraytype;
        }else{
            if((type instanceof FunctionDefineType)||(type instanceof ClassDefineType)){
                throw new Exception("MemberWrong7"+u.loc.toString());
            }
            u.type=type;
        }
    }
    public void visit(VariableNode u)throws Exception{
        if(u.name.equals("this")){
            if(ClassName.equals("main")){
                throw new Exception("THIS not exist"+u.loc.toString());
            }
            u.type=new ClassType(ClassName);
            return;
        }
        Scope o=u.belong;
        u.type=o.get(u.name);
        if(u.type!=null)System.out.println("Variablename="+u.name+" Variabletype="+u.type.typename);
        if(u.type==null){
            throw new Exception("variable not exist"+u.loc.toString());
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
            throw new Exception("not a LHSNode"+u.loc.toString());
        }
        if(op.equals("~")||op.equals("+")||op.equals("-")||op.equals("++")||op.equals("--")){
            if(!ExprMatching(u.expr.type,new IntType())){
                throw new Exception("Type Unmatching"+u.loc.toString());
            }
        }
        if(op.equals("!")){
            if(!ExprMatching(u.expr.type,new BoolType())){
                throw new Exception("Type Unmatching"+u.loc.toString());
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
            throw new Exception("BreakWrong"+u.loc.toString());
        }
    }
    public void visit(ContinueNode u)throws Exception{
        if(loopcnt==0){
            throw new Exception("ContinueWrong"+u.loc.toString());
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
                throw new Exception("ForWrong"+u.loc.toString());
            }
        }
        loopcnt--;
    }
    public void visit(IfNode u)throws Exception{
        visit(u.expr);
        visit(u.ifstmt);
        visit(u.elsestmt);
        if(!ExprMatching(u.expr.type,new BoolType())){
            throw new Exception("IFWrong"+u.loc.toString());
        }
    }
    public void visit(ReturnNode u)throws Exception{
        if(u.expr!=null)visit(u.expr);
        if(u.expr==null){
            if(!ExprMatching(returntype,new VoidType())){
                throw new Exception("ReturnWrong"+u.loc.toString());
            }
        }else{
            if(u.expr.type==null){
                if(!(returntype instanceof ArrayType)&&!(returntype instanceof ClassType)){
                    throw new Exception("ReturnWrong"+u.loc.toString());
                }
            }else{
                if(!ExprMatching(returntype,u.expr.type)){
                    throw new Exception("ReturnWrong"+u.loc.toString());
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
            throw new Exception("WhileWrong"+u.loc.toString());
        }
    }
    public void visit(ClassDefNode u)throws Exception{
        ClassName=u.name;
        for(VariableDefNode o:u.variables){
            visit(o);
            if(o.type instanceof ClassType&&scoperoot.get(o.type.typename)==null){
                throw new Exception("Undefined"+u.loc.toString());
            }
        }
        for(FunctionDefNode o:u.functions)visit(o);
        u.type=scoperoot.get(u.name);
        ClassName="main";
    }
    public void visit(FunctionDefNode u)throws Exception{
        if(u.type.typename.equals("null")){
            if(!u.name.equals(ClassName)){
                throw new Exception("Construct Name Wrong"+u.loc.toString());
            }
        }else if(!TypeChecker(u.type)){
            throw new Exception("Type Undefined"+u.loc.toString());
        }
        returntype=u.type;
        for(VariableDefNode o:u.variables)visit(o);
        visit(u.block);
        returntype=null;
    }
    public void visit(VariableDefNode u)throws Exception{
        Type type=u.type;
        if(type instanceof VoidType){
            throw new Exception("Void Defination"+u.loc.toString());
        }
        if(type instanceof ArrayType)
            if(((ArrayType)type).deeptype instanceof VoidType){
                throw new Exception("Void Defination"+u.loc.toString());
            }
        if(!TypeChecker(type)){
            throw new Exception("Type Undefined"+u.loc.toString());
        }
        if(u.expr!=null){
            visit(u.expr);
            if(!AssignMatching(type,u.expr.type)){
                throw new Exception("AssignTypeUnmatching"+u.loc.toString());
            }
        }
        //if(u.belong.classflag==false) {
        //    System.out.println("LOC="+u.loc.toString());
            if (!u.belong.add(u.name, type)) {
                throw new Exception("Redefine"+u.loc.toString());
            }
        //}
    }
    public void visit(ProgramNode u)throws Exception{
        Scope tmp=u.belong;
        Type w=u.belong.get("main");
        if(!(w instanceof FunctionDefineType)||!(((FunctionDefineType)w).variable instanceof IntType)){
            throw new Exception("MainWrong"+u.loc.toString());
        }
        for(Node o:u.son)visit(o);
        /*for(VariableDefNode o:u.variables)visit(o);
        for(ClassDefNode o:u.classes)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);*/
    }
}