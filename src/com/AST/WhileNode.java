package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class WhileNode extends StmtNode{
    public ExprNode expr;
    public StmtNode stmt;
    public WhileNode(){
        super();
        //expr=new ExprNode();
        //stmt=new StmtNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        tmp.add(stmt);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<WhileNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
