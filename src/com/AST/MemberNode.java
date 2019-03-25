package com.AST;
import com.AST.LHSNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class MemberNode extends LHSNode {
    public ExprNode expr;
    public String membername;
    public MemberNode(){
        super();
        //expr=new ExprNode();
        membername=null;
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(expr);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<MemberNode> name:"+name+"type: "+type.toString()+ " membername: "+membername);
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
