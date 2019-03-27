package com.AST;
import com.AST.LHSNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class MemberNode extends LHSNode {
    public ExprNode expr;
    public ExprNode member;
    public MemberNode(){
        super();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        tmp.add(member);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<MemberNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
