package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ReturnNode extends StmtNode {
    public ExprNode expr;
    public ReturnNode(){
        super();
        //expr=new ExprNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        if(expr!=null)tmp.add(expr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<ReturnNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
