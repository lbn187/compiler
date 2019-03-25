package com.AST;
import com.AST.ExprNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class BinaryOpNode extends ExprNode {
    public String operator;
    public ExprNode exprl;
    public ExprNode exprr;
    public BinaryOpNode() {
        super();
        operator = null;
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(exprl);
        tmp.add(exprr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<BinaryOpNode> name:"+name+"type: "+type.toString()+" Operator: "+operator);
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
