package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ContinueNode extends StmtNode{
    public ContinueNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<ContinueNode> name:"+name+"type: "+type.toString());
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
