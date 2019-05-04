package com.frontend;
import com.Type.Type;
import com.IR.Register;
public class Information {
    public Type type;
    public int offset;
    public Register register=null;
    public boolean classflag;
    public Information(Type t,int off,boolean flag){
        type=t;
        offset=off;
        classflag=flag;
    }
}
