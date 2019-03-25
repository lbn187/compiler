package com.AST;
import com.AST.LiteralNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class StringLiteralNode extends LiteralNode{
    public StringLiteralNode(){
        super();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<StringLiteralNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
