package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;

import java.util.List;
import java.util.ArrayList;

public class BlockNode extends StmtNode {
    public List<StmtNode> stmts;
    public BlockNode() {
        super();
        stmts=new ArrayList<StmtNode>();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        for(StmtNode o:stmts)tmp.add(o);
        return tmp;
    }
    public void print(String s){
        System.out.println(s+"<BlockNode> name:"+name+"type: "+type.toString());
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
