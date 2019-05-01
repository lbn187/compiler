package com.frontend;
import com.Type.Type;
import com.IR.Register;
public class Information {
    public Type type;
    public int offset;
    public Register register=null;
    public Information(Type t,int off){
        type=t;
        offset=off;
    }
}
