package com.nasm;
import java.util.List;
import java.util.ArrayList;
public class RegConst {
    public static final String[] Regs={"rax","rbx","rcx","rdx","rsi","rdi","rbp","rsp","r8","r9","r10","r11","r12","r13","r14","r15"};
    public static List<VReg>VRegs=new ArrayList<>();
    public static final int[] Args={5,4,3,2,8,9};//then put to stack
    public static final int[] CalleeRegs={1,12,13,14,15};//dont include rbp
    public static final int[] CallerRegs={0,2,3,4,5,8,9,10,11};
    public static final int[] ColorRegs={0,2,3,1,4,5,8,9,10,11,12,13,14,15};
    public static final VReg rax;
    public static final VReg rbx;
    public static final VReg rcx;
    public static final VReg rdx;
    public static final VReg rsi;
    public static final VReg rdi;
    public static final VReg rbp;
    public static final VReg rsp;
    static{
        for(int i=0;i<16;i++){
            VRegs.add(new VReg(Regs[i],i));
        }
        rax=VRegs.get(0);
        rbx=VRegs.get(1);
        rcx=VRegs.get(2);
        rdx=VRegs.get(3);
        rsi=VRegs.get(4);
        rdi=VRegs.get(5);
        rbp=VRegs.get(6);
        rsp=VRegs.get(7);
    }
}
