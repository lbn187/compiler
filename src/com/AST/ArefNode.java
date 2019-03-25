package com.AST;
import com.AST.LHSNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ArefNode extends LHSNode {
    public ExprNode exprname;
    public ExprNode exprexpr;
    public ArefNode() {
        super();
        //exprname = new ExprNode();
        //exprexpr = new ExprNode();
    }
    public void print(String s){
        System.out.println(s+"<ArefNode> name:"+name+"type: "+type.toString());
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(exprname);
        tmp.add(exprexpr);
        return tmp;
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
