package com.AST;
import com.AST.ExprNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;

public class AssignNode extends ExprNode{
    public ExprNode exprl;
    public ExprNode exprr;
    public AssignNode() {
        super();
        //exprl=new ExprNode();
        //exprr=new ExprNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(exprl);
        tmp.add(exprr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<AssignNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
