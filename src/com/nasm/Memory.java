package com.nasm;
import java.util.List;
import java.util.ArrayList;
import com.IR.VirtualRegister;
import static com.nasm.RegConst.*;
public class Memory extends Var{
    public VReg base;
    public VReg index;
    public int offset;
    public Label label;
    public int scale;
    public boolean ok=false;
    public Memory(){
        ok=false;
    }
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
    public List<VReg> CalUse(){
        List<VReg> list=new ArrayList<>();
        if(ok==false||label!=null)return list;
        if(base!=rbp)list.add(base);
        if(index!=null)list.add(index);
        return list;
    }
    public void PushInStack(int off){
        base=rbp;
        offset=off;
        ok=true;
    }
    public String getname() {
        if (label != null) {
            return "qword [rel " + label.name + "]";
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
        assert ok;
        if (label != null) {
            return "qword [rel " + label + "]";
        } else {
            assert base != null;
            StringBuilder builder = new StringBuilder("qword [" + Regs[base.PReg]);
            if (index != null) {
                builder.append(" + ").append(Regs[index.PReg]);
                if (scale != 1) {
                    builder.append("*").append(scale);
                }
            }
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
}