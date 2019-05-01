package com.AST;
import com.AST.LiteralNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class IntLiteralNode extends LiteralNode {
    public String value;
    public IntLiteralNode(){
        super();
        value=null;
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<IntLiteralNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}