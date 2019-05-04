package com.IR;

public class StringData extends Value{
    static public int StringCnt=0;
    public String value;
    public int id;
    public StringData(String s){
        //super("str");
        value=s;
        id=++StringCnt;
    }
    public String toString() {
        return "$str_" + id;
    }
}