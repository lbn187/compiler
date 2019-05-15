package com.IR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.frontend.ScopeBuilder;
public class IRRoot {
    public Map<String, Function> functions=new LinkedHashMap<>();
    public Map<String, StringData> strings=new LinkedHashMap<>();
    public List<GlobalRegister> globalregisters=new ArrayList<>();
    public IRRoot() {
        strings.put("\\n", new StringData("\\n"));
        Function global=new Function("__Global",false);
        ScopeBuilder.STRING_PRINT.ir=new Function("print",true);
        ScopeBuilder.STRING_PRINTLN.ir=new Function("println",true);
        ScopeBuilder.GETSTRING.ir=new Function("getString",true);
        ScopeBuilder.GETINT.ir=new Function("getInt",true);
        ScopeBuilder.TOSTRING.ir=new Function("toString",true);
        ScopeBuilder.STRING_ADD.ir=new Function("__StringADD",true);
        ScopeBuilder.STRING_EQ.ir=new Function("__StringEQ",true);
        ScopeBuilder.STRING_NEQ.ir=new Function("__StringNEQ",true);
        ScopeBuilder.STRING_LT.ir=new Function("__StringLT",true);
        ScopeBuilder.STRING_GT.ir=new Function("__StringGT",true);
        ScopeBuilder.STRING_LEQ.ir=new Function("__StringLEQ",true);
        ScopeBuilder.STRING_GEQ.ir=new Function("__StringGEQ",true);
        ScopeBuilder.STRING_LENGTH.ir=new Function("__StringLength",true);
        ScopeBuilder.STRING_SUBSTRING.ir=new Function("__StringSubstring",true);
        ScopeBuilder.STRING_PARSEINT.ir=new Function("__StringParseInt",true);
        ScopeBuilder.STRING_ORD.ir=new Function("__StringOrd",true);
        ScopeBuilder.ARRAY_SIZE.ir=new Function("__ArraySize",true);
        functions.put(global.name,global);
        functions.put(ScopeBuilder.STRING_PRINT.typename,ScopeBuilder.STRING_PRINT.ir);
        functions.put(ScopeBuilder.STRING_PRINTLN.typename,ScopeBuilder.STRING_PRINTLN.ir);
        functions.put(ScopeBuilder.GETSTRING.typename,ScopeBuilder.GETSTRING.ir);
        functions.put(ScopeBuilder.GETINT.typename,ScopeBuilder.GETINT.ir);
        functions.put(ScopeBuilder.TOSTRING.typename,ScopeBuilder.TOSTRING.ir);
        functions.put(ScopeBuilder.STRING_ADD.typename,ScopeBuilder.STRING_ADD.ir);
        functions.put(ScopeBuilder.STRING_EQ.typename,ScopeBuilder.STRING_EQ.ir);
        functions.put(ScopeBuilder.STRING_NEQ.typename,ScopeBuilder.STRING_NEQ.ir);
        functions.put(ScopeBuilder.STRING_LT.typename,ScopeBuilder.STRING_LT.ir);
        functions.put(ScopeBuilder.STRING_GT.typename,ScopeBuilder.STRING_GT.ir);
        functions.put(ScopeBuilder.STRING_LEQ.typename,ScopeBuilder.STRING_LEQ.ir);
        functions.put(ScopeBuilder.STRING_GEQ.typename,ScopeBuilder.STRING_GEQ.ir);
        functions.put(ScopeBuilder.STRING_LENGTH.typename,ScopeBuilder.STRING_LENGTH.ir);
        functions.put(ScopeBuilder.STRING_SUBSTRING.typename,ScopeBuilder.STRING_SUBSTRING.ir);
        functions.put(ScopeBuilder.STRING_PARSEINT.typename,ScopeBuilder.STRING_PARSEINT.ir);
        functions.put(ScopeBuilder.STRING_ORD.typename,ScopeBuilder.STRING_ORD.ir);
        functions.put(ScopeBuilder.ARRAY_SIZE.typename,ScopeBuilder.ARRAY_SIZE.ir);
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}