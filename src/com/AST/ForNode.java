package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ForNode extends StmtNode {
    public ExprNode pre;
    public ExprNode mid;
    public ExprNode suc;
    public StmtNode stmt;
    public ForNode(){
        super();
        //pre=new ExprNode();
        //mid=new ExprNode();
        //suc=new ExprNode();
        //stmt=new StmtNode();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        tmp.add(pre);
        tmp.add(mid);
        tmp.add(suc);
        tmp.add(stmt);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<ForNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
