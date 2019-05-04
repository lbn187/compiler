package com.IR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IRRoot {
    public Map<String, Function> functions=new LinkedHashMap<>();
    public Map<String, StringData> strings=new LinkedHashMap<>();
    public List<GlobalRegister> globalregisters=new ArrayList<>();
    public IRRoot() {
        strings.put("\\n", new StringData("\\n"));
        Function global=new Function("__Global",false);
        functions.put(global.name,global);
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}