package com.AST;
import com.AST.BinaryOpNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class LogicalAndNode extends BinaryOpNode {
    public LogicalAndNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<LogicalAndNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
