package com.IR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IRRoot {
    public Map<String, Function> functions=new LinkedHashMap<>();
    public Map<String, StringData> strings=new LinkedHashMap<>();
    public List<StaticData> datas=new ArrayList<>();

    public IRRoot() {
        strings.put("\\n", new StringData("\\n"));
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
