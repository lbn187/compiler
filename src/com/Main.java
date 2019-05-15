package com;
import com.parser.MxStarLexer;
import com.parser.MxStarParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import com.frontend.*;
import com.AST.*;
import com.IR.*;
import com.nasm.*;
import com.backend.StackBuilder;
import com.backend.AllocateRegister;
import com.backend.*;
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
        if(astbuilder.isok==3){
            File file = new File("lib/ex.asm");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            return;
        }
        ScopeBuilder scopebuilder=new ScopeBuilder();
        scopebuilder.work(root);
        SemanticChecker semanticchecker=new SemanticChecker(scopebuilder.scoperoot);
        root.accept(semanticchecker);
        IRBuilder irbuilder=new IRBuilder();
        irbuilder.getIR(root);
        IRRoot irroot=irbuilder.irroot;
        //FunctionInliner inliner = new FunctionInliner(irroot);
        //inliner.run();
        //System.out.println("-----------START PRINT-----------------");
        //irroot.accept(new IRPrinter(out));
        //System.out.println("-----------END PRINT-----------------");
        Translator translator=new Translator();
        Nasm nasm=translator.getNasm(irroot);
        AllocateRegister.visit(nasm);
        StackBuilder.visit(nasm);
        NasmPrinter nasmprinter=new NasmPrinter();
        nasmprinter.visit(nasm);
    }
}