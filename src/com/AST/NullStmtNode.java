package com.AST;
import com.AST.StmtNode;
import com.frontend.ASTVisitor;

import java.util.List;
import java.util.ArrayList;
public class NullStmtNode extends StmtNode{
    public NullStmtNode(){
        super();
    }
    public List<Node> getAll() {
        List<Node> tmp = new ArrayList<Node>();
        return tmp;
    }
    public void accept(ASTVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
