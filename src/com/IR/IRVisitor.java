package com.IR;

public interface IRVisitor {
    void visit(IRRoot node);
    void visit(IRBlock node);
    void visit(Function node);
    void visit(BinaryOpIR node);
    void visit(UnaryOpIR node);
    void visit(CmpIR node);
    void visit(Immediate node);
    void visit(Call node);
    //void visit(SystemCall node);
    //void visit(PhiInstruction node);
    void visit(Branch node);
    void visit(Return node);
    void visit(Jump node);
    void visit(VirtualRegister node);
    //void visit(PhysicalRegister node);
    //void visit(StackSlot node);
    void visit(Allocation node);
    void visit(Load node);
    void visit(Store node);
    void visit(Move node);
    void visit(StringData node);
    void visit(ArraySpace node);
    //void visit(Register node);
}