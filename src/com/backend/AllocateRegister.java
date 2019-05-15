package com.backend;
//import javafx.util.*;
import java.util.*;
import java.io.*;
import com.backend.LiveTester;
import com.nasm.*;
import static com.nasm.RegConst.*;
public class AllocateRegister {
    public Func func;
    public int K=0;
    public Set<Mov> CombineVRegMove = new LinkedHashSet<>();
    public Set<Mov> ConstrainMove = new LinkedHashSet<>();
    public Set<Mov> FrozenMove = new LinkedHashSet<>();
    public Set<Mov> WorklistMove = new LinkedHashSet<>();
    public Set<Mov> ActiveMove = new LinkedHashSet<>();
    public Set<EdgePair> EdgeSet = new HashSet<>();
    public Map<VReg, Set<VReg>> EdgelistMap = new HashMap<>();
    public Map<VReg, Integer> DegreeMap = new HashMap<>();
    public Map<VReg, Set<Mov>> MovelistMap = new HashMap<>();
    public Map<VReg, VReg> AliasMap = new HashMap<>();
    public Set<VReg> PreColor = new LinkedHashSet<>();
    public Set<VReg> Init = new LinkedHashSet<>();
    public Set<VReg> SimpleWork = new LinkedHashSet<>();
    public Set<VReg> FreezeWork = new LinkedHashSet<>();
    public Set<VReg> SpillWork = new LinkedHashSet<>();
    public Set<VReg> SpillNode = new LinkedHashSet<>();
    public Set<VReg> CombineVRegNode = new LinkedHashSet<>();
    public Set<VReg> ColoredNode = new LinkedHashSet<>();
    public Stack<VReg> StackSelect = new Stack<>();
    public Set<VReg> NodeSelect = new HashSet<>();
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
    public void run() throws IOException {
        for(int Step=0;true;){
            ++Step;
            PreColor.clear();
            Init.clear();
            SimpleWork.clear();
            FreezeWork.clear();
            SpillWork.clear();
            SpillNode.clear();
            CombineVRegNode.clear();
            ColoredNode.clear();
            StackSelect.clear();
            NodeSelect.clear();
            CombineVRegMove.clear();
            ConstrainMove.clear();
            FrozenMove.clear();
            WorklistMove.clear();
            ActiveMove.clear();
            EdgeSet.clear();
            EdgelistMap.clear();
            DegreeMap.clear();
            MovelistMap.clear();
            AliasMap.clear();
            for(Block block:func.Blocks){
                //  System.out.println("BLOCK: "+block.name);
                for(Inst inst:block.Insts){
                    Set<VReg> regs=new HashSet<>();
                    regs.addAll(inst.CalDefine());
                    regs.addAll(inst.CalUse());
                    for(VReg reg:regs){
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
                EdgelistMap.put(reg,new HashSet<>());
                if(PreColor.contains(reg))DegreeMap.put(reg,100000007);
                else DegreeMap.put(reg,0);
                MovelistMap.put(reg,new HashSet<>());
                AliasMap.put(reg,null);
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
                    for(int i=0;i<5;i++)define.add(VRegs.get(CalleeRegs[i]));
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
                if (DegreeMap.get(n)>=K){
                    SpillWork.add(n);
                }else if(MoveRelatedJudge(n)){
                    FreezeWork.add(n);
                }else{
                    //  System.out.println("FINDSIMPLE:"+n.toString());
                    SimpleWork.add(n);
                }
            }
            while(true){
                if(!SimpleWork.isEmpty()) {
                    VReg n = SimpleWork.iterator().next();
                    //System.out.println("SIMPLYFIY :"+n.toString());
                    SimpleWork.remove(n);
                    StackSelect.push(n);
                    NodeSelect.add(n);
                    for (VReg m:CalAdjacent(n))ConsumeDegreeMap(m);
                } else if(!WorklistMove.isEmpty()) {
                    Mov mov=WorklistMove.iterator().next();
                    VReg x=CalAliasMap((VReg) mov.dest);
                    VReg y=CalAliasMap((VReg) mov.src);
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
                        AddWorkListMove(u);
                    }else if((v.PrecolorFlag==true)||EdgeSet.contains(new EdgePair(u,v))){
                        ConstrainMove.add(mov);
                        AddWorkListMove(u);
                        AddWorkListMove(v);
                    }else{
                        Set<VReg> vAdj=CalAdjacent(v);
                        Set<VReg> uvAdj=CalAdjacent(u);
                        uvAdj.addAll(vAdj);
                        boolean cond1=u.PrecolorFlag==true;
                        if(cond1){
                            for (VReg t:vAdj)
                                if(!ok(t,u)){
                                    cond1=false;
                                    break;
                                }
                        }
                        boolean cond2=(u.PrecolorFlag==false)&&DegreeProtectJudge(uvAdj);
                        if (cond1||cond2) {
                            CombineVRegMove.add(mov);
                            CombineVReg(u,v);
                            AddWorkListMove(u);
                        }else{
                            ActiveMove.add(mov);
                        }
                    }
                } else if (!FreezeWork.isEmpty()) {
                    VReg u = FreezeWork.iterator().next();
                    FreezeWork.remove(u);
                    SimpleWork.add(u);
                    freezeMoves(u);
                } else if (!SpillWork.isEmpty()) {
                    Iterator<VReg> iter = SpillWork.iterator();
                    VReg m=iter.next();
                    while((m.TinyFlag==true)&&iter.hasNext()){
                        m=iter.next();
                    }
                    SpillWork.remove(m);
                    SimpleWork.add(m);
                    freezeMoves(m);
                }else break;
            }
            while (!StackSelect.isEmpty()) {
                VReg n=StackSelect.pop();
                // System.out.println("INSTACK: "+n.toString());
                NodeSelect.remove(n);
                Set<Integer>okColors=new HashSet<>();
                for(int i=0;i<14;i++){
                    //    System.out.println("+"+ColorRegs[i]);
                    okColors.add(ColorRegs[i]);
                }
                for (VReg w:EdgelistMap.get(n)) {
                    VReg aw=CalAliasMap(w);
                    if (ColoredNode.contains(aw)||PreColor.contains(aw)) {
                        //        System.out.println("-"+aw.PReg);
                        okColors.remove(aw.PReg);
                    }
                }
                //  System.out.println("GET: "+okColors.size());
                if (okColors.isEmpty()) {
                    SpillNode.add(n);
                } else {
                    ColoredNode.add(n);
                    n.PReg=okColors.iterator().next();
                    //    System.out.println(n.toString()+"------------PREG: "+Regs[n.PReg]);
                    //String c = okColors.iterator().next();
                    //n.setColor();
                }
            }
            for (VReg n:CombineVRegNode) {
                n.PReg=CalAliasMap(n).PReg;
                //  System.out.println(n.toString()+"------------PREG: "+Regs[n.PReg]);
            }
            if(SpillNode.isEmpty())break;
            func.RspOffset+=SpillNode.size()*8;
            SpillEditor spillEditor=new SpillEditor(SpillNode);
            List<VReg> newTemps=spillEditor.visit(func);
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
            SpillWork.remove(m);
            if (MoveRelatedJudge(m)){
                FreezeWork.add(m);
            }else{
                SimpleWork.add(m);
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
    public void AddWorkListMove(VReg u) {
        if ((u.PrecolorFlag==false)&&(MoveRelatedJudge(u)==false)&&DegreeMap.get(u)<K) {
            FreezeWork.remove(u);
            SimpleWork.add(u);
        }
    }
    public boolean ok(VReg t,VReg r){
        return DegreeMap.get(t)<K||t.PrecolorFlag==true||EdgeSet.contains(new EdgePair(t,r));
    }
    public boolean DegreeProtectJudge(Set<VReg> nodes) {
        int k=0;
        for(VReg n:nodes)
            if(DegreeMap.get(n)>=K)k++;
        return k<K;
    }
    public void CombineVReg(VReg u, VReg v) {
        if(FreezeWork.contains(v)){
            FreezeWork.remove(v);
        }else{
            SpillWork.remove(v);
        }
        CombineVRegNode.add(v);
        AliasMap.put(v,u);
        MovelistMap.get(u).addAll(MovelistMap.get(v));
        Set<VReg> nodes = new HashSet<>();
        nodes.add(v);
        EnableMoves(nodes);
        for (VReg t:CalAdjacent(v)) {
            AddEdge(t,u);
            ConsumeDegreeMap(t);
        }
        if (DegreeMap.get(u)>=K&&FreezeWork.contains(u)){
            FreezeWork.remove(u);
            SpillWork.add(u);
        }
    }
    public VReg CalAliasMap(VReg n) {
        if(CombineVRegNode.contains(n)){
            return CalAliasMap(AliasMap.get(n));
        }else return n;
    }
    public void freezeMoves(VReg u) {
        for (Mov mov : MoveNodes(u)) {
            VReg x=CalAliasMap((VReg)mov.dest);
            VReg y=CalAliasMap((VReg)mov.src);
            VReg v;
            if(y==CalAliasMap(u))v=x;else v=y;
            ActiveMove.remove(mov);
            FrozenMove.add(mov);
            if (FreezeWork.contains(v)&&MoveNodes(v).isEmpty()){
                FreezeWork.remove(v);
                SimpleWork.add(v);
            }
        }
    }
}