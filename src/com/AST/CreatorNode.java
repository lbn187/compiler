package com.AST;
import com.AST.ExprNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;

public class CreatorNode extends ExprNode {
    public List<ExprNode> exprs;
    public CreatorNode(){
        super();
        exprs=new ArrayList<ExprNode>();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        for(ExprNode o:exprs)tmp.add(o);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<CreatorNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
