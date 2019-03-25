package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;

public class FunctionDefNode extends StmtNode {
    public List<VariableDefNode> variables;
    public BlockNode block;
    public FunctionDefNode(){
        super();
        variables=new ArrayList<VariableDefNode>();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        for(VariableDefNode o:variables)tmp.add(o);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<FunctionDefNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
