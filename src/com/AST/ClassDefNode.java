package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;
import java.util.List;
import java.util.ArrayList;
public class ClassDefNode extends StmtNode {
    public List<VariableDefNode> variables;
    public List<FunctionDefNode> functions;
    public ClassDefNode(){
        super();
        variables=new ArrayList<VariableDefNode>();
        functions=new ArrayList<FunctionDefNode>();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        for(VariableDefNode o:variables)tmp.add(o);
        for(FunctionDefNode o:functions)tmp.add(o);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<ClassDefNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
