package com.AST;
import com.AST.Node;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
abstract public class StmtNode extends Node{
    public StmtNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<StmtNode> name:"+name+"type: "+type.toString());
    }
    //abstract public <S,E> S accept(ASTVisitor<S,E> visitor);
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
