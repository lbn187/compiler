package com;
import com.parser.MxStarLexer;
import com.parser.MxStarParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import com.frontend.*;
import com.AST.*;
import com.IR.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        //String fileName = "5";
        //InputStream is = new FileInputStream ("example/" + fileName + ".txt");
        InputStream is=System.in;
        OutputStream os=System.out;
        PrintStream out=System.out;
        ANTLRInputStream input=new ANTLRInputStream(is);
        MxStarLexer lexer=new MxStarLexer(input);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        MxStarParser parser=new MxStarParser(tokens);

        // parser.removeErrorListeners();
        parser.setErrorHandler(new BailErrorStrategy());
        ParseTree tree=parser.program();
        //System.out.println(tree.toStringTree(parser));
        ASTBuilder astbuilder=new ASTBuilder();
        Node root=astbuilder.visit(tree);
        ScopeBuilder scopebuilder=new ScopeBuilder();
        scopebuilder.work(root);
        SemanticChecker semanticchecker=new SemanticChecker(scopebuilder.scoperoot);
        root.accept(semanticchecker);
        IRBuilder irbuilder=new IRBuilder();
        irbuilder.getIR(root);
        IRRoot irroot=irbuilder.irroot;
        //System.out.println("-----------START PRINT-----------------");
        //irroot.accept(new IRPrinter(out));
        //System.out.println("-----------END PRINT-----------------");
    }
}