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
        if(a instanceof ArrayType)a=((ArrayType)a).deeptype;
        if(b instanceof ArrayType)b=((ArrayType)b).deeptype;
        if(Complex(a)==true||Complex(b)==true)return false;
        if(a.typename.equals(b.typename))return true;
        return false;
    }
    boolean AssignMatching(Type a,Type b) throws Exception{
        if(a instanceof ArrayType)if(b instanceof NullType)return true;
        if(a instanceof ArrayType)a=((ArrayType)a).deeptype;
        if(b instanceof ArrayType)b=((ArrayType)b).deeptype;
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
            throw new Exception("赋值语句左端不是左值");
            //System.out.println("赋值语句左端不是左值 "+u.loc.toString());
        }
        if(u.exprl.name.equals("this")){
            throw new Exception("赋值语句左端为this");
            //System.out.println("赋值语句左端为this "+u.loc.toString());
        }
        if(!AssignMatching(u.exprl.type,u.exprr.type)){
            throw new Exception("赋值语句类型不匹配");
            //System.out.println("赋值语句类型不匹配 "+u.loc.toString());
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
                throw new Exception("双目运算符类型不匹配");
                //System.out.println("双目运算符类型不匹配 operator="+op+" "+u.loc.toString());
            }
        }
        if(op.equals("-")||op.equals("*")||op.equals("/")||op.equals("%")||op.equals("&")||op.equals("|")||op.equals("^")||op.equals(">>")||op.equals("<<")){
            if(!ExprMatching(u.exprl.type,new IntType())||!ExprMatching(u.exprr.type,new IntType())){
                throw new Exception("双目运算符类型不匹配");
                //System.out.println("双目运算符类型不匹配 operator="+op+" "+u.loc.toString());
            }
            u.type=new IntType();
        }
        if(op.equals("<")||op.equals("<=")||op.equals(">")||op.equals(">=")){
            if(!ExprMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("双目运算符类型不匹配");
                //System.out.println("双目运算符类型不匹配 operator="+op+" "+u.loc.toString());
            }
            u.type=new BoolType();
        }
        if(op.equals("==")||op.equals("!=")){
            if(!CompareEqualMatching(u.exprl.type,u.exprr.type)){
                throw new Exception("双目运算符类型不匹配");
                //System.out.println("双目运算符类型不匹配 operator="+op+" "+u.loc.toString());
            }
            u.type=new BoolType();
        }
        if(op.equals("&&")||op.equals("||")){
            if(!ExprMatching(u.exprl.type,new BoolType())||!ExprMatching(u.exprr.type,new BoolType())){
                throw new Exception("双目运算符类型不匹配");
                //System.out.println("双目运算符类型不匹配 operator="+op+" "+u.loc.toString());
            }
            u.type=new BoolType();
        }
    }
    public void visit(FuncExprNode u)throws Exception{
        for(ExprNode o:u.exprs)
            visit(o);
        Type w=scoperoot.get(u.name);
        if(w==null){
            throw new Exception("函数没有定义");
            //System.out.println("函数没有定义 name="+u.name+" "+u.loc.toString());
        }else if(!(w instanceof FunctionDefineType)){
            throw new Exception("不是函数");
            //System.out.println("不是函数 name="+u.name+" "+u.loc.toString());
        }else{
            FunctionDefineType p=(FunctionDefineType)w;
            if(u.exprs.size()!=p.parameters.size()){
                throw new Exception("函数个数不匹配");
                //System.out.println("函数个数不匹配 name="+u.name+" "+u.loc.toString());
            }else{
                boolean flag=true;
                for(int i=0;i<u.exprs.size();i++)
                    if(!ExprMatching(u.exprs.get(i).type,p.parameters.get(i)))flag=false;
                if(flag==false){
                    throw new Exception("函数类型不匹配");
                    //System.out.println("函数类型不匹配 name=" + u.name + " " + u.loc.toString());
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
            throw new Exception("未定义的数组类型");
            //System.out.println("未定义的数组类型 name="+u.exprname.type.typename+" "+u.loc.toString());
        }else u.type=((ArrayType)u.exprname.type).basetype;
    }
    public void visit(MemberNode u)throws Exception{
        visit(u.expr);
        if(u.expr.type instanceof ArrayType){
            if(!u.member.name.equals("size")||u.member.getAll().size()!=0){
                throw new Exception("数组的Size函数错误");
                //System.out.println("数组的Size函数错误 "+u.loc.toString());
            }
            u.type=new IntType();
        }else{
            for(Node o:u.member.getAll())
                visit(o);
            if(!(u.expr.type instanceof ClassType)){
                throw new Exception("A.x中的A应该是一个类");
                //System.out.println("A.x中的A应该是一个类 "+u.loc.toString());
            }else{
                ClassDefineType classtype=(ClassDefineType)scoperoot.get(u.expr.type.typename);
                Type insidetype=classtype.get(u.member.name);
                if(insidetype==null){
                    throw new Exception("A.func()函数没有定义");
                    //System.out.println("A.func()函数没有定义 name="+u.name+" "+u.loc.toString());
                }
                if(u.member instanceof FuncExprNode){
                    FuncExprNode uu=(FuncExprNode)u.member;
                    if(!(insidetype instanceof FunctionDefineType)){
                        throw new Exception("A.func()不是函数");
                        //System.out.println("A.func()不是函数 name="+u.name+" "+u.loc.toString());
                    }else{
                        FunctionDefineType p=(FunctionDefineType)insidetype;
                        if(uu.exprs.size()!=p.parameters.size()){
                            throw new Exception("A.func()函数个数不匹配");
                            //System.out.println("A.func()函数个数不匹配 name="+u.name+" "+u.loc.toString());
                        }else{
                            boolean flag=true;
                            for(int i=0;i<uu.exprs.size();i++)
                                if(!ExprMatching(uu.exprs.get(i).type,p.parameters.get(i)))flag=false;
                            if(flag==false){
                                throw new Exception("A.func()函数类型不匹配");
                                //System.out.println("A.func()函数类型不匹配 name=" + u.name + " " + u.loc.toString());
                            }
                        }
                        u.type=p.variable;
                    }
                }else if(u.member instanceof ArefNode){
                    if(!(insidetype instanceof ArrayType)){
                        throw new Exception("A.a[]期望是数组类型");
                        //System.out.println("A.a[]期望是数组类型 "+u.loc.toString());
                    }
                    u.type=ArrayType((ArrayType)insidetype,(ArefNode)u.member);
                }else{
                    //   if(!(insidetype instanceof VariableType)){
                    //     System.out.println("A.x未定义的x name=" + u.name + " " + u.loc.toString());
                    // }
                    u.type=insidetype;
                }
            }
        }
    }
    public void visit(VariableNode u)throws Exception{
        if(u.name.equals("this")){
            if(ClassName.equals("main")){
                throw new Exception("THIS不存在");
                //System.out.println("THIS不存在 "+u.loc.toString());
            }
            u.type=new ClassType(ClassName);
            return;
        }
        Scope o=u.belong;
        u.type=o.get(u.name);
        if(u.type!=null)System.out.println("Variablename="+u.name+" Variabletype="+u.type.typename);
        if(u.type==null){
            throw new Exception("变量不存在");
            //System.out.println("变量不存在 name="+u.name+" "+u.loc.toString());
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
            throw new Exception("不是左值");
            //System.out.println("不是左值 operator="+u.operator+" "+u.loc.toString());
        }
        if(op.equals("~")||op.equals("+")||op.equals("-")||op.equals("++")||op.equals("--")){
            if(!ExprMatching(u.expr.type,new IntType())){
                throw new Exception("类型不匹配");
                //System.out.println("类型不匹配 operator="+u.operator+" "+u.loc.toString());
            }
        }
        if(op.equals("!")){
            if(!ExprMatching(u.expr.type,new BoolType())){
                throw new Exception("类型不匹配");
                //System.out.println("类型不匹配 operator="+u.operator+" "+u.loc.toString());
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
    /*public void visit(CreatorNode u){
        for(ExprNode o:u.exprs)
            visit(o);
    }*/
    //public void visit(BlockNode u){
    //    for(StmtNode o:u.stmts)visit(o);
    //}
    public void visit(BreakNode u)throws Exception{
        if(loopcnt==0){
            throw new Exception("Break语句不在循环中");
            //System.out.println("Break语句不在循环中 "+u.loc.toString());
        }
    }
    public void visit(ContinueNode u)throws Exception{
        if(loopcnt==0){
            throw new Exception("Continue语句不在循环中");
            //System.out.println("Continue语句不在循环中 "+u.loc.toString());
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
                throw new Exception("For语句里的判断表达式不是BOOL类型");
                //System.out.println("For语句里的判断表达式不是BOOL类型"+u.loc.toString());
            }
        }
        loopcnt--;
    }
    public void visit(IfNode u)throws Exception{
        visit(u.expr);
        visit(u.ifstmt);
        visit(u.elsestmt);
        if(!ExprMatching(u.expr.type,new BoolType())){
            throw new Exception("IF语句里的表达式不是BOOL类型");
            //System.out.println("IF语句里的表达式不是BOOL类型 "+u.loc.toString());
        }
    }
    public void visit(ReturnNode u)throws Exception{
        if(u.expr!=null)visit(u.expr);
        if(u.expr==null){
            if(!ExprMatching(returntype,new VoidType())){
                throw new Exception("Return语句返回值类型错误");
                //System.out.println("Return语句返回值类型错误 "+u.loc.toString());
            }
        }else{
            if(u.expr.type==null){
                if(!(returntype instanceof ArrayType)&&!(returntype instanceof ClassType)){
                    throw new Exception("Return语句返回值类型错误");
                    //System.out.println("Return语句返回值类型错误 "+u.loc.toString());
                }
            }else{
                if(!ExprMatching(returntype,u.expr.type)){
                    throw new Exception("Return语句返回值类型错误");
                    //System.out.println("Return语句返回值类型错误 "+u.loc.toString());
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
            throw new Exception("While语句里的判断表达式不是BOOL类型");
            //System.out.println("While语句里的判断表达式不是BOOL类型"+u.loc.toString());
        }
    }
    public void visit(ClassDefNode u)throws Exception{
        ClassName=u.name;
        for(VariableDefNode o:u.variables)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);
        ClassName="main";
    }
    public void visit(FunctionDefNode u)throws Exception{
        if(u.type.typename.equals("null")){//构造函数
            if(!u.name.equals(ClassName)){
                throw new Exception("构造函数函数名与类名不等");
                //System.out.println("构造函数函数名与类名不等 "+u.loc.toString());
            }
        }else if(!TypeChecker(u.type)){
            throw new Exception("类型未定义");
            //System.out.println("类型未定义 name="+u.type.typename+" "+u.loc.toString());
        }
        returntype=u.type;
        for(VariableDefNode o:u.variables)visit(o);
        visit(u.block);
        returntype=null;
    }
    public void visit(VariableDefNode u)throws Exception{
        Type type=u.type;
        if(type instanceof VoidType){
            throw new Exception("定义了Void变量");
            //System.out.println("定义了Void变量 "+u.loc.toString());
        }
        if(type instanceof ArrayType)
            if(((ArrayType)type).deeptype instanceof VoidType){
                throw new Exception("定义了Void变量");
                //System.out.println("定义了Void变量 "+u.loc.toString());
            }
        if(!TypeChecker(type)){
            throw new Exception("没有定义这种类型");
            //System.out.println("没有定义这种类型 name="+type.typename+" ,"+u.loc.toString());
        }
        if(u.expr!=null){
            visit(u.expr);
            if(!AssignMatching(type,u.expr.type)){
                throw new Exception("赋值类型不匹配");
                //System.out.println("赋值类型不匹配 "+u.loc.toString());
            }
        }
    }
    public void visit(ProgramNode u)throws Exception{
        Scope tmp=u.belong;
        Type w=u.belong.get("main");
        if(!(w instanceof FunctionDefineType)||!(((FunctionDefineType)w).variable instanceof IntType)){
            throw new Exception("Main函数错误");
            //System.out.println("Main函数错误");
        }
        for(VariableDefNode o:u.variables)visit(o);
        for(ClassDefNode o:u.classes)visit(o);
        for(FunctionDefNode o:u.functions)visit(o);
    }
}