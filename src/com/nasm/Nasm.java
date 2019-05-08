package com.nasm;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
public class Nasm {
    public List<String>GlobalVariables=new ArrayList<>();
    public Map<String,String>StringLiterals=new HashMap<>();
    public List<Func>Functions=new ArrayList<>();
    public List<String>Builtins=new ArrayList<>();
    public List<String>Libs=new ArrayList<>();
}