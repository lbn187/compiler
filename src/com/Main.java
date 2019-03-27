package com;
import com.parser.MxStarLexer;
import com.parser.MxStarParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import com.frontend.*;
import com.AST.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        //String fileName = "5";
        //InputStream is = new FileInputStream ("example/" + fileName + ".txt");
        InputStream is=System.in;
        OutputStream os=System.out;
        ANTLRInputStream input=new ANTLRInputStream(is);
        MxStarLexer lexer=new MxStarLexer(input);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        MxStarParser parser=new MxStarParser(tokens);
        ParseTree tree=parser.program();
        // parser.removeErrorListeners();
        parser.setErrorHandler(new BailErrorStrategy());
        System.out.println(tree.toStringTree(parser));
        ASTBuilder astbuilder=new ASTBuilder();
        Node root=astbuilder.visit(tree);
        ScopeBuilder scopebuilder=new ScopeBuilder();
        scopebuilder.work(root);
        SemanticChecker semanticchecker=new SemanticChecker(scopebuilder.scoperoot);
        root.accept(semanticchecker);
    }
}
