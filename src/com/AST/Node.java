package com.AST;
import com.AST.Location;
import com.frontend.ASTVisitor;
import com.Type.*;
import java.util.List;
import java.util.ArrayList;
abstract public class Node {
    public Location loc;
    public Type type;
    public String name;
    public Node(){
        type=new VoidType();
        name=null;
    }
    public void print(String s){
        System.out.println(s+"<Node> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}