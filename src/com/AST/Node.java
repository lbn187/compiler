package com.AST;
import com.AST.Location;
import com.frontend.ASTVisitor;
import com.Type.*;
import com.frontend.Scope;
import java.util.List;
import java.util.ArrayList;
abstract public class Node {
    public Location loc;
    public Type type;
    public String name="";
    public Scope belong;
    public Node(){
        type=new VoidType();
    }
    public List<Node> getAll(){return null;}
    public void print(String s){
        System.out.println(s+"<Node> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}