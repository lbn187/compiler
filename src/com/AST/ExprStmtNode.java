package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ExprStmtNode extends StmtNode {
    public ExprNode expr;
    public ExprStmtNode(){
        super();
        //expr=new ExprNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<ExprStmtNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
