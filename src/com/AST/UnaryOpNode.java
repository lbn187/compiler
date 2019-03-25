package com.AST;
import com.AST.ExprNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class UnaryOpNode extends ExprNode {
    public String operator;
    public ExprNode expr;
    public UnaryOpNode(){
        super();
        operator=null;
        //expr=new ExprNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<UnaryOpNode> name:"+name+"type: "+type.toString()+" Operator: "+operator);
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
