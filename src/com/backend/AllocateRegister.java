package com.backend;
//import javafx.util.*;
import java.util.*;
import java.io.*;
import com.backend.LiveTester;
import com.nasm.*;
import static com.nasm.RegConst.*;
public class AllocateRegister {
    public Func func;
    public int AllocCnt;
    public Set<Mov> CombineVRegMove = new LinkedHashSet<>();
    public Set<Mov> ConstrainMove = new LinkedHashSet<>();
    public Set<Mov> FrozenMove = new LinkedHashSet<>();
    public Set<Mov> WorklistMove = new LinkedHashSet<>();
    public Set<Mov> ActiveMove = new LinkedHashSet<>();
    public Set<EdgePair> EdgeSet = new HashSet<>();
    public Map<VReg, Set<VReg>> EdgelistMap = new HashMap<>();
    public Map<VReg, Integer> DegreeMap = new HashMap<>();
    public Map<VReg, Set<Mov>> MovelistMap = new HashMap<>();
    public Map<VReg, VReg> DoubleMap = new HashMap<>();
    public Set<VReg> PreColor = new LinkedHashSet<>();
    public Set<VReg> Init = new LinkedHashSet<>();
    public Set<VReg> SimplyWork = new LinkedHashSet<>();
    public Set<VReg> FreezeWork = new LinkedHashSet<>();
    public Set<VReg> ExtraWork = new LinkedHashSet<>();
    public Set<VReg> ExtraNode = new LinkedHashSet<>();
    public Set<VReg> ExtraColor=new LinkedHashSet<>();
    public Set<VReg> CombineVRegNode = new LinkedHashSet<>();
    public Set<VReg> ColoredNode = new LinkedHashSet<>();
    public Stack<VReg> StackSelect = new Stack<>();
    public Set<VReg> NodeSelect = new HashSet<>();
    public int RegNumConst=10000000;
    public static void visit(Nasm nasm) throws IOException {
        //  System.out.println("VISIT-------------");
        for(Func func:nasm.Functions){
            AllocateRegister allocator=new AllocateRegister(func);
            allocator.run();
        }
        nasm.AllocateFlag=true;
    }
    public AllocateRegister(Func fun) {
        func=fun;
    }
    public int K=0;
    public void run() throws IOException {
        for(;true;){
            CombineVRegMove.clear();
            ConstrainMove.clear();
            FrozenMove.clear();
            WorklistMove.clear();
            ActiveMove.clear();
            EdgeSet.clear();
            EdgelistMap.clear();
            DegreeMap.clear();
            MovelistMap.clear();
            DoubleMap.clear();
            PreColor.clear();
            Init.clear();
            SimplyWork.clear();
            FreezeWork.clear();
            ExtraWork.clear();
            ExtraNode.clear();
            CombineVRegNode.clear();
            ColoredNode.clear();
            ExtraColor.clear();
            StackSelect.clear();
            NodeSelect.clear();
            for(Block block:func.Blocks){
                //  System.out.println("BLOCK: "+block.name);
                for(Inst inst:block.Insts){
                    Set<VReg> regs=new HashSet<>();
                    regs.addAll(inst.CalDefine());
                    regs.addAll(inst.CalUse());
                    for(VReg reg:regs){
                        ExtraColor.add(reg);
                        //       System.out.println("REG: "+regs.toString());
                        if(reg.PrecolorFlag==true)PreColor.add(reg);
                        else Init.add(reg);
                    }
                }
            }
            Set<VReg>allRegs=new HashSet<>();
            allRegs.addAll(PreColor);
            allRegs.addAll(Init);
            for(VReg reg:allRegs){
                ExtraColor.add(reg);
                //       System.out.println("REG: "+regs.toString());
                EdgelistMap.put(reg,new HashSet<>());
                if(PreColor.contains(reg))DegreeMap.put(reg,RegNumConst);
                else DegreeMap.put(reg,0);
                MovelistMap.put(reg,new HashSet<>());
                DoubleMap.put(reg,null);
            }
            LiveTester.visit(func);
            for (Block block:func.Blocks) {
                Set<VReg>live=new HashSet<>(block.Dies);
                if (block.ReturnTest()==true)
                    for(int i=0;i<5;i++)
                        live.add(VRegs.get(CalleeRegs[i]));
                for(ListIterator li=block.Insts.listIterator(block.Insts.size());li.hasPrevious();) {
                    Inst inst=(Inst) li.previous();
                    List<VReg>define=inst.CalDefine();
                    List<VReg>use=inst.CalUse();
                    if(MoveJudge(inst)){
                        //  System.out.println("INMOVE-----------");
                        Mov mov=(Mov)inst;
                        live.removeAll(use);
                        for(VReg reg:define)MovelistMap.get(reg).add(mov);
                        for(VReg reg:use)MovelistMap.get(reg).add(mov);
                        WorklistMove.add(mov);
                    }
                    live.addAll(define);
                    for (VReg a:define)
                        for (VReg b:live)
                            AddEdge(a,b);
                    live.removeAll(define);
                    live.addAll(use);
                }
                if (block.EntryTest()==true) {
                    List<VReg> define=new ArrayList<>();
                    for(int i=0;i<5;i++)
                        define.add(VRegs.get(CalleeRegs[i]));
                    live.addAll(define);
                    for (VReg a:define)
                        for (VReg b:live)
                            AddEdge(a,b);
                    live.removeAll(define);
                }
            }
            List<VReg> regs=new ArrayList<>(Init);
            for (VReg n:regs) {
                Init.remove(n);
                ExtraWork.add(n);
            }
            while(true){
                if(!SimplyWork.isEmpty()) {
                    VReg n=SimplyWork.iterator().next();
                    //System.out.println("SIMPLYFIY :"+n.toString());
                    SimplyWork.remove(n);
                    StackSelect.push(n);
                    NodeSelect.add(n);
                    for (VReg m:CalAdjacent(n))ConsumeDegreeMap(m);
                } else if(!WorklistMove.isEmpty()) {
                    Mov mov=WorklistMove.iterator().next();
                    //System.out.println("Work :"+n.toString());
                    VReg x=CalDoubleMap((VReg) mov.dest);
                    VReg y=CalDoubleMap((VReg) mov.src);
                    VReg u,v;
                    if(y.PrecolorFlag==true){
                        u=y;
                        v=x;
                    }else{
                        u=x;
                        v=y;
                    }
                    WorklistMove.remove(mov);
                    if(u==v){
                        CombineVRegMove.add(mov);
                    }else if((v.PrecolorFlag==true)||EdgeSet.contains(new EdgePair(u,v))){
                        ConstrainMove.add(mov);
                    }else{
                        Set<VReg> vAdj=CalAdjacent(v);
                        boolean flag=u.PrecolorFlag==true;
                        if(flag){
                            for (VReg t:vAdj)
                                if(!ok(t,u)){
                                    flag=false;
                                    break;
                                }
                            //System.out.println("---------------------");
                        }
                        if(flag){
                            CombineVRegMove.add(mov);
                            CombineVReg(u,v);
                        } else{
                            ActiveMove.add(mov);
                        }
                    }
                }else if(!FreezeWork.isEmpty()){
                    VReg u = FreezeWork.iterator().next();
                    FreezeWork.remove(u);
                    SimplyWork.add(u);
                    FreezeMove(u);
                } else if(!ExtraWork.isEmpty()){
                    Iterator<VReg> iter = ExtraWork.iterator();
                    VReg m=iter.next();
                    while((m.TinyFlag==true)&&iter.hasNext()){
                        m=iter.next();
                    }
                    ExtraWork.remove(m);
                    SimplyWork.add(m);
                    FreezeMove(m);
                }else break;
            }
            while (!StackSelect.isEmpty()) {
                VReg n = StackSelect.pop();
                NodeSelect.remove(n);
                Set<String> okColors = new HashSet<>(Arrays.asList(ColorRegs));
                for (VReg w : EdgelistMap.get(n)) {
                    VReg aw = CalDoubleMap(w);
                    if (ColoredNode.contains(aw) || PreColor.contains(aw)) {
                        okColors.remove(String.valueOf(aw.PReg));
                    }
                }
                if(!okColors.isEmpty()){
                    ColoredNode.add(n);
                    n.PReg=Integer.valueOf(okColors.iterator().next());
                    //System.out.println(n.PReg);
                }else{
                    ExtraNode.add(n);
                }
            }
            for (VReg n:CombineVRegNode)n.PReg=CalDoubleMap(n).PReg;
            if(ExtraNode.isEmpty())break;
            func.RspOffset+=ExtraNode.size()*8;
            ExtraSolver solver=new ExtraSolver(ExtraNode);
            List<VReg> tmp=solver.visit(func);
        }
        for (Block block:func.Blocks) {
            LinkedList<Inst> Insts=new LinkedList<>();
            for (Inst inst:block.Insts) {
                if (SelfMoveJudge(inst))continue;
                Insts.add(inst);
            }
            block.Insts=Insts;
        }
    }
    public boolean SelfMoveJudge(Inst inst) {
        if (!(inst instanceof Mov))return false;
        Mov mov=(Mov)inst;
        if ((!(mov.dest instanceof VReg))||(!(mov.src instanceof VReg))) return false;
        return ((VReg) mov.dest).PReg==((VReg) mov.src).PReg;
    }
    public boolean MoveJudge(Inst inst) {
        if (!(inst instanceof Mov)) return false;
        Mov mov=(Mov) inst;
        return (mov.dest instanceof VReg)&&(mov.src instanceof VReg);
    }
    public void AddEdge(VReg u, VReg v) {
        if(u==v||EdgeSet.contains(new EdgePair(u,v)))return;
        EdgeSet.add(new EdgePair(u,v));
        EdgeSet.add(new EdgePair(v,u));
        if(u.PrecolorFlag==false) {
            EdgelistMap.get(u).add(v);
            int d=DegreeMap.get(u)+1;
            DegreeMap.put(u, d);
        }
        if(v.PrecolorFlag==false){
            EdgelistMap.get(v).add(u);
            int d=DegreeMap.get(v)+1;
            DegreeMap.put(v, d);
        }
    }
    public Set<VReg> CalAdjacent(VReg n) {
        Set<VReg> neighbors = new HashSet<>();
        for (VReg neighbor:EdgelistMap.get(n))
            if (!NodeSelect.contains(neighbor)&&!CombineVRegNode.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        return neighbors;
    }
    public Set<Mov> MoveNodes(VReg n) {
        Set<Mov> moves = new HashSet<>();
        for (Mov mov:MovelistMap.get(n))
            if(ActiveMove.contains(mov)||WorklistMove.contains(mov)){
                moves.add(mov);
            }
        return moves;
    }
    public boolean MoveRelatedJudge(VReg n) {
        return !MoveNodes(n).isEmpty();
    }
    public void ConsumeDegreeMap(VReg m) {
        int d=DegreeMap.get(m);
        DegreeMap.put(m, d-1);
        if(d==K){
            Set<VReg> nodes=CalAdjacent(m);
            nodes.add(m);
            EnableMoves(nodes);
            ExtraWork.remove(m);
            if (MoveRelatedJudge(m)){
                FreezeWork.add(m);
            }else{
                SimplyWork.add(m);
            }
        }
    }
    public void EnableMoves(Set<VReg> nodes) {
        for (VReg n:nodes)
            for (Mov m:MoveNodes(n)){
                if(!(ActiveMove.contains(m)))continue;
                ActiveMove.remove(m);
                WorklistMove.add(m);
            }
    }
    public boolean ok(VReg t,VReg r){
        return t.PrecolorFlag==true||EdgeSet.contains(new EdgePair(t,r));
    }
    public void CombineVReg(VReg u, VReg v) {
        if(FreezeWork.contains(v)){
            FreezeWork.remove(v);
        }else{
            ExtraWork.remove(v);
        }
        CombineVRegNode.add(v);
        DoubleMap.put(v,u);
        MovelistMap.get(u).addAll(MovelistMap.get(v));
        Set<VReg> nodes = new HashSet<>();
        nodes.add(v);
        EnableMoves(nodes);
        for (VReg t:CalAdjacent(v)) {
            AddEdge(t,u);
            ConsumeDegreeMap(t);
        }
        if(FreezeWork.contains(u)){
            FreezeWork.remove(u);
            ExtraWork.add(u);
        }
    }
    public VReg CalDoubleMap(VReg n) {
        if(CombineVRegNode.contains(n)){
            return CalDoubleMap(DoubleMap.get(n));
        }else return n;
    }
    public void FreezeMove(VReg u) {
        for (Mov mov : MoveNodes(u)) {
            VReg x=CalDoubleMap((VReg)mov.dest);
            VReg y=CalDoubleMap((VReg)mov.src);
            VReg v;
            if(y==CalDoubleMap(u))v=x;else v=y;
            ActiveMove.remove(mov);
            FrozenMove.add(mov);
            if (FreezeWork.contains(v)&&MoveNodes(v).isEmpty()){
                FreezeWork.remove(v);
                SimplyWork.add(v);
            }
        }
    }
}