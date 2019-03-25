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
        String fileName = "4";
        InputStream is = new FileInputStream ("example/" + fileName + ".txt");
        ANTLRInputStream input=new ANTLRInputStream(is);
        MxStarLexer lexer=new MxStarLexer(input);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        MxStarParser parser=new MxStarParser(tokens);
        ParseTree tree=parser.program();//以program为规则开始语法分析
        parser.removeErrorListeners();
        System.out.println(tree.toStringTree(parser));//以文本形式打印树
        ASTBuilder builder=new ASTBuilder();
        Node root=builder.visit(tree);
    }
}
