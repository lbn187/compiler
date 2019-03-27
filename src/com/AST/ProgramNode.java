package com.AST;

import com.frontend.ASTVisitor;
import com.parser.MxStarListener;
import com.parser.MxStarParser;
import com.parser.MxStarVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.util.List;
import java.util.ArrayList;
import com.AST.Node;
public class ProgramNode extends Node {
   // public List<VariableDefNode> variables;
   // public List<ClassDefNode> classes;
   // public List<FunctionDefNode> functions;
    public List<Node> son;
    public ProgramNode(){
        super();
        son=new ArrayList<Node>();
        //variables=new ArrayList<VariableDefNode>();
        //classes=new ArrayList<ClassDefNode>();
        //functions=new ArrayList<FunctionDefNode>();
    }
    public List<Node> getAll() {
        return son;
        /*List<Node> tmp = new ArrayList<Node>();
        for(VariableDefNode o:variables)tmp.add(o);
        for(ClassDefNode o:classes)tmp.add(o);
        for(FunctionDefNode o:functions)tmp.add(o);
        return tmp;*/
    }
    public void print(String s){
        System.out.println(s+"<ProgramNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}