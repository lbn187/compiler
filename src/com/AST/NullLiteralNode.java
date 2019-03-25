package com.AST;
import com.AST.LiteralNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class NullLiteralNode extends LiteralNode {
    public NullLiteralNode(){
        super();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<NullLiteralNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
