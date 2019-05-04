package com.frontend;
import com.Type.*;
import com.AST.*;
import com.frontend.ASTVisitor;
import com.parser.MxStarBaseVisitor;
import com.parser.MxStarParser;
import org.antlr.v4.runtime.tree.*;
public class ASTBuilder extends MxStarBaseVisitor<Node> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ProgramNode visitProgram(MxStarParser.ProgramContext ctx) {
        ProgramNode u=new ProgramNode();
        //System.out.println("VISIT PROGRAM");
        for (int i=0;i<ctx.getChildCount(); ++i){
            //System.out.println("VISIT");
            u.son.add(visit(ctx.getChild(i)));
        }
      /*  for(MxStarParser.VariableDefineContext o:ctx.variableDefine())
            u.variables.add((VariableDefNode)visit(o));
        for(MxStarParser.ClassPartContext o:ctx.classPart())
            u.classes.add((ClassDefNode)visit(o));
        for(MxStarParser.FunctionPartContext o:ctx.functionPart())
            u.functions.add((FunctionDefNode)visit(o));*/
        u.type=new VoidType();
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableDefNode visitVariableDefine(MxStarParser.VariableDefineContext ctx) {
        VariableDefNode u=new VariableDefNode();
        u.name=ctx.name.getText();
        u.type=TypeBuilder.getType(ctx.thetype().getText());
        //System.out.println("<VariableDefineNode> name="+u.name+" type="+u.type.typename);
        u.loc=new Location(ctx.start);
        if(ctx.expression()!=null)u.expr=(ExprNode)visit(ctx.expression());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ClassDefNode visitClassPart(MxStarParser.ClassPartContext ctx) {
        ClassDefNode u=new ClassDefNode();
        u.name=ctx.name.getText();
        u.loc=new Location(ctx.start);
        u.type=new ClassType(ctx.name.getText());
        //System.out.println("<ClassDefNode> name="+u.name+" type=class<"+u.type.typename+">");
        for(MxStarParser.VariableDefineContext o:ctx.variableDefine())
            u.variables.add((VariableDefNode)visit(o));
        for(MxStarParser.FunctionPartContext o:ctx.functionPart())
            u.functions.add((FunctionDefNode)visit(o));
        for(int i=0;i<u.functions.size();i++){
            Node o=u.functions.get(i);
            if(o.name==null)o.name=u.name;
        }
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public FunctionDefNode visitFunctionPart(MxStarParser.FunctionPartContext ctx) {
        FunctionDefNode u=new FunctionDefNode();
        u.name=ctx.name.getText();
        if(ctx.thetype()!=null) u.type=TypeBuilder.getType(ctx.thetype().getText());else u.type=new NullType();
        //System.out.println("<FunctionDefNode> name="+u.name);
        u.loc=new Location(ctx.start);
        for(MxStarParser.ParameterContext o:ctx.parameter())
            u.variables.add((VariableDefNode)visit(o));
        u.block=(BlockNode)visit(ctx.block());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    /*@Override public FunctionDefNode visitParameterList(MxStarParser.ParameterListContext ctx) {
        FunctionDefNode u=new FunctionDefNode();
        for(MxStarParser.ParameterContext o:ctx.parameter())
            u.variables.add((VariableDefNode)visit(o));
        u.loc=new Location(ctx.start);
        return u;
    }*/
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableDefNode visitParameter(MxStarParser.ParameterContext ctx) {
        VariableDefNode u=new VariableDefNode();
        u.name=ctx.name.getText();
        u.type=TypeBuilder.getType(ctx.thetype().getText());
        //System.out.println("<VariableDefNode> name="+u.name+" type="+u.type.typename);
        u.loc=new Location(ctx.start);
        u.expr=null;
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Node visitThetype(MxStarParser.ThetypeContext ctx) {
        //System.out.println("WHAT??? visitThetype");
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Node visitAllthetype(MxStarParser.AllthetypeContext ctx) {
        //System.out.println("WHAT??? visitAllthetype");
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Node visitBasicthetype(MxStarParser.BasicthetypeContext ctx) {
        //System.out.println("WHAT?? visitBasicthetype");
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BlockNode visitBlock(MxStarParser.BlockContext ctx) {
        BlockNode u=new BlockNode();
        //System.out.println("<BlockNode>");
        for(MxStarParser.StmtContext o:ctx.stmt())
            u.stmts.add((StmtNode)visit(o));
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BlockNode visitStmtblock(MxStarParser.StmtblockContext ctx) {
        return (BlockNode)visit(ctx.block());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public IfNode visitStmtif(MxStarParser.StmtifContext ctx) {
        //System.out.println("<IfNode>");
        IfNode u=new IfNode();
        u.loc=new Location(ctx.start);
        u.expr=(ExprNode)visit(ctx.expression());
        u.ifstmt=(StmtNode)visit(ctx.stmt(0));
        if(ctx.stmt().size()==2)u.elsestmt=(StmtNode)visit(ctx.stmt(1));
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public WhileNode visitStmtwhile(MxStarParser.StmtwhileContext ctx) {
        //System.out.println("<WhileNode>");
        WhileNode u=new WhileNode();
        u.loc=new Location(ctx.start);
        u.expr=(ExprNode)visit(ctx.expression());
        u.stmt=(StmtNode)visit(ctx.stmt());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ForNode visitStmtfor(MxStarParser.StmtforContext ctx) {
        //System.out.println("<ForNode>");
        ForNode u=new ForNode();
        u.loc=new Location(ctx.start);
        if(ctx.pre!=null)u.pre=(ExprNode)visit(ctx.pre);
        if(ctx.mid!=null)u.mid=(ExprNode)visit(ctx.mid);
        if(ctx.suc!=null)u.suc=(ExprNode)visit(ctx.suc);
        u.stmt=(StmtNode)visit(ctx.stmt());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BreakNode visitStmtbreak(MxStarParser.StmtbreakContext ctx) {
        //System.out.println("<BreakNode>");
        BreakNode u=new BreakNode();
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ContinueNode visitStmtcontinue(MxStarParser.StmtcontinueContext ctx) {
        //System.out.println("<ContinueNode>");
        ContinueNode u=new ContinueNode();
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ReturnNode visitStmtreturn(MxStarParser.StmtreturnContext ctx) {
        //System.out.println("<ReturnNode>");
        ReturnNode u=new ReturnNode();
        u.loc=new Location(ctx.start);
        if(ctx.expression()!=null)u.expr=(ExprNode)visit(ctx.expression());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ExprStmtNode visitStmtexpression(MxStarParser.StmtexpressionContext ctx) {
        // System.out.println("<ExprStmtNode>");
        ExprStmtNode u=new ExprStmtNode();
        u.loc=new Location(ctx.start);
        u.expr=(ExprNode)visit(ctx.expression());
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableDefNode visitStmtvariable(MxStarParser.StmtvariableContext ctx) {
        return (VariableDefNode)visit(ctx.variableDefine());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public NullLiteralNode visitExprnull(MxStarParser.ExprnullContext ctx) {
        //  System.out.println("<NullLiteralNode>");
        NullLiteralNode u=new NullLiteralNode();
        u.loc=new Location(ctx.start);
        u.type=new NullType();
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ExprNode visitExprbrackets(MxStarParser.ExprbracketsContext ctx) {
        return (ExprNode)visit(ctx.expression());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public StringLiteralNode visitExprstring(MxStarParser.ExprstringContext ctx) {
        StringLiteralNode u=new StringLiteralNode();
        String s=ctx.name.getText();
        String nowv="";
        for(int i=1;i<s.length()-1;)
            if(s.charAt(i)=='\\'){
                if(s.charAt(i+1)=='\\')nowv+="\\";else
                if(s.charAt(i+1)=='n')nowv+="\n";else
                if(s.charAt(i+1)=='"')nowv+="\"";else
                    nowv+=s.charAt(i+1);
                i+=2;
            }else{
                nowv+=s.charAt(i);
                i++;
            }
        u.name=nowv;
        // System.out.println("<StringLiteralNode> name="+u.name);
        u.loc=new Location(ctx.start);
        u.type=new StringType();
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public PrefixOpNode visitExprprefix(MxStarParser.ExprprefixContext ctx) {
        PrefixOpNode u=new PrefixOpNode();
        u.operator=ctx.op.getText();
        //   System.out.println("<PrefixOpNode> operator="+u.operator);
        u.expr=(ExprNode)visit(ctx.expression());
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * 	 * {@inheritDoc}
     * 	 *
     * 	 * <p>The default implementation returns the result of calling
     * 	 * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public AssignNode visitExprassign(MxStarParser.ExprassignContext ctx) {
        //System.out.println("<AssignNode>");
        AssignNode u=new AssignNode();
        u.exprl=(ExprNode)visit(ctx.expression(0));
        u.exprr=(ExprNode)visit(ctx.expression(1));
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableNode visitExprthis(MxStarParser.ExprthisContext ctx) {
        VariableNode u=new VariableNode();
        u.loc=new Location(ctx.start);
        u.name="this";
        //   System.out.println("<VariableNode> name="+u.name);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public SuffixOpNode visitExprsuffix(MxStarParser.ExprsuffixContext ctx) {
        SuffixOpNode u=new SuffixOpNode();
        u.operator=ctx.op.getText();
        //  System.out.println("SuffixOpNode  operatpr="+u.operator);
        u.expr=(ExprNode)visit(ctx.expression());
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BoolLiteralNode visitExprfalse(MxStarParser.ExprfalseContext ctx){
        //  System.out.println("<BoolLiteralNode> false");
        BoolLiteralNode u=new BoolLiteralNode();
        u.flag=false;
        u.loc=new Location(ctx.start);
        u.type=new BoolType();
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public MemberNode visitExprsmember(MxStarParser.ExprsmemberContext ctx) {
        MemberNode u=new MemberNode();
        u.loc=new Location(ctx.start);
        u.expr=(ExprNode)visit(ctx.expression());
        u.name=ctx.name.getText();
        //u.member=(ExprNode)visit(ctx.expression(1));
        //   System.out.println("<MemberNode>");
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public FuncExprNode visitExprfunction(MxStarParser.ExprfunctionContext ctx) {
        FuncExprNode u=new FuncExprNode();
        //u.name=ctx.name.getText();
        //   System.out.println("<FuncExprNode> name="+u.name);
        for(MxStarParser.ExpressionContext o:ctx.expression()){
            u.exprs.add((ExprNode)visit(o));
        }
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableNode visitExpridentifier(MxStarParser.ExpridentifierContext ctx) {
        VariableNode u=new VariableNode();
        u.name=ctx.name.getText();
        //   System.out.println("<VariableNode> name="+u.name);
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public IntLiteralNode visitExprinteger(MxStarParser.ExprintegerContext ctx) {
        IntLiteralNode u=new IntLiteralNode();
        u.name=ctx.name.getText();
        u.value=ctx.name.getText();
        //  System.out.println("<IntLiteralNode> name="+u.name);
        u.type=new IntType();
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BinaryOpNode visitExprbinary(MxStarParser.ExprbinaryContext ctx) {
        BinaryOpNode u=new BinaryOpNode();
        u.operator=ctx.op.getText();
        //   System.out.println("<BinaryOpNode> operator="+u.operator);
        u.exprl=(ExprNode)visit(ctx.expression(0));
        u.exprr=(ExprNode)visit(ctx.expression(1));
        u.loc=new Location(ctx.start);
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BoolLiteralNode visitExprtrue(MxStarParser.ExprtrueContext ctx) {
        // System.out.println("<BoolLiteralNode> true");
        BoolLiteralNode u=new BoolLiteralNode();
        u.flag=true;
        u.loc=new Location(ctx.start);
        u.type=new BoolType();
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public ArefNode visitExprexpr(MxStarParser.ExprexprContext ctx) {
        //   System.out.println("<ArefNode>");
        ArefNode u=new ArefNode();
        u.exprname=(ExprNode)visit(ctx.expression(0));
        u.exprexpr=(ExprNode)visit(ctx.expression(1));
        u.loc=new Location(ctx.start);
        u.name=u.exprname.name;
        return u;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public CreatorNode visitExprnew(MxStarParser.ExprnewContext ctx) {
        System.out.println(ctx.getText());
        return (CreatorNode)visit(ctx.creator());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public CreatorNode visitCreator(MxStarParser.CreatorContext ctx) {
        CreatorNode u=new CreatorNode();
        if(ctx.allthetype()!=null){
            Type basetype;
            TypeBuilder builder=new TypeBuilder();
            basetype=builder.getType(ctx.allthetype().getText());
            int dim=0;
            String s=ctx.getText();
            for(int i=0;i<s.length();i++)if(s.charAt(i)=='[')dim++;
            u.type=new ArrayType(basetype,dim);
        }else{
            u.type=new ClassType(ctx.name.getText());
        }
        for(MxStarParser.ExpressionContext o:ctx.expression())
            u.exprs.add((ExprNode)visit(o));
        u.loc=new Location(ctx.start);
        return u;
    }
    @Override public NullStmtNode visitStmtblank(MxStarParser.StmtblankContext ctx){
        //  System.out.println("<NullStmtNode>");
        NullStmtNode u=new NullStmtNode();
        return u;
    }
    /*private StmtNode getStmt(MxStarParser.StatementContext ctx) {
        if(ctx==null)return null;
        return (StmtNode)mp.get(ctx);
    }
    private ExprNode getExpr(MalicParser.ExpressionContext ctx) {
        if(ctx==null)return null;
        return (ExprNode)mp.get(ctx);
    }*/
}
