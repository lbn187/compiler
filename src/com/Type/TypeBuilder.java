package com.Type;
import com.Type.*;
public class TypeBuilder {
    public static Type getType(String S){
        int dim=0;
        boolean flag=true;
        String name="";
        if(S.contains("[")){
            for(int i=0;i<S.length();i++) {
                char ch = S.charAt(i);
                if (ch == '[') dim++;
                if ((ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '_') && flag == true)
                    name = name + ch;
                else flag = false;
            }
            Type basetype=getType(name);
            return new ArrayType(basetype,dim);
        }else {
            if(S.equals("bool"))return new BoolType();
            if(S.equals("int"))return new IntType();
            if(S.equals("string"))return new StringType();
            if(S.equals("void"))return new VoidType();
            return new ClassType(S);
        }
    }
}
