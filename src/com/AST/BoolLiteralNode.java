package com.AST;
import com.AST.LiteralNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class BoolLiteralNode extends LiteralNode {
    public boolean flag;
    public BoolLiteralNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<BoolLiteralNode> name:"+name+"type: "+type.toString());
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
