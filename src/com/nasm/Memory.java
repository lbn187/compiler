package com.nasm;
import com.IR.VirtualRegister;
public class Memory extends Var{
    public VReg base;
    public VReg index;
    public int offset;
    public Label label;
    public boolean ok=false;
    public Memory(VReg reg){
        base=reg;
        ok=true;
    }
    public Memory(VReg reg,int off){
        base=reg;
        offset=off;
        ok=true;
    }
    public Memory(Label l){
        label=l;
        ok=true;
    }
    public String getname() {
        if (label != null) {
            return "qword [rel " + label + "]";
        } else {
//            assert base != null;
            if (base == null) return "qword [ ]";
            StringBuilder builder = new StringBuilder("qword [" + base.name);
            if (offset != 0) {
                if (offset > 0) {
                    builder.append(" + ").append(offset);
                } else {
                    builder.append(" - ").append(-offset);
                }
            }
            builder.append("]");
            return builder.toString();
        }
    }

    public String toString() {
        /*assert valid;
        if (label != null) {
            return "qword [rel " + label + "]";
        } else {
            assert base != null;
//            StringBuilder builder = new StringBuilder("qword [" + base.getName() + "$" + base.getColor());
            StringBuilder builder = new StringBuilder("qword [" + base.getColor());
            if (index != null) {
//                builder.append(" + ").append(index.getName() + "$" + index.getColor());
                builder.append(" + ").append(index.getColor());
                if (scale != 1) {
                    builder.append("*").append(scale);
                }
            }
            if (displacement != 0) {
                if (displacement > 0) {
                    builder.append(" + ").append(displacement);
                } else {
                    builder.append(" - ").append(-displacement);
                }
            }
            builder.append("]");
            return builder.toString();
        }*/
         return null;
    }
}