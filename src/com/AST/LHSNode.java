package com.AST;
import com.AST.ExprNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
abstract public class LHSNode extends ExprNode {
    public LHSNode(){
        super();
    }
    public List<Node> getAll(){return null;}
    public void print(String s){
        System.out.println(s+"<LHSNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
