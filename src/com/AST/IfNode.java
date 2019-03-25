package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class IfNode extends StmtNode {
    public ExprNode expr;
    public StmtNode ifstmt;
    public StmtNode elsestmt;
    public IfNode(){
        super();
        //expr=new ExprNode();
        //ifstmt=new StmtNode();
        //elsestmt=new StmtNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        tmp.add(ifstmt);
        tmp.add(elsestmt);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<IfNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
