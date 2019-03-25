package com.AST;
import com.AST.LiteralNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
abstract public class LiteralNode extends ExprNode{
    public LiteralNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<LiteralNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
