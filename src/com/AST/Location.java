package com.AST;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class Location {
    public int line;
    public int column;
    public Location(int l,int c){
        line=l;
        column=c;
    }
    public Location(Location a){
        line=a.line;
        column=a.column;
    }
    public Location(Token token){
        line=token.getLine();
        column=token.getCharPositionInLine();
    }
    public String toString(){
        return "line "+line+", column "+column;
    }
}