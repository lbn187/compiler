package com.AST;
import com.AST.Node;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
import com.IR.IRBlock;
import com.IR.Value;
abstract public class ExprNode extends Node {
    public IRBlock trueblock;
    public IRBlock falseblock;
    public Value register;
    public Value address;
    public int offset;
    public ExprNode(){super();}
    public List<Node> getall(){return null;}
    public void print(String s){
        System.out.println(s+"<ExprNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
