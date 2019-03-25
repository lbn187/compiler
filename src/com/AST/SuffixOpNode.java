package com.AST;
import com.AST.UnaryOpNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class SuffixOpNode extends UnaryOpNode{
    public SuffixOpNode(){
        super();
    }
    public void print(String s){
        System.out.println(s+"<SuffixOpNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
