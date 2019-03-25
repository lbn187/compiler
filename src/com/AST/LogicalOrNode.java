package com.AST;
import com.AST.BinaryOpNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class LogicalOrNode extends BinaryOpNode {
    public LogicalOrNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<LogicalOrNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
