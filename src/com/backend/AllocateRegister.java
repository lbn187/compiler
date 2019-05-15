package com.backend;
//import javafx.util.*;
import java.util.*;
import java.io.*;
import com.backend.LiveTester;
import com.nasm.*;
import static com.nasm.RegConst.*;
public class AllocateRegister {
    public Func func;
    public int K = 14;
    public Set<VReg> PreColor = new LinkedHashSet<>();
    public Set<VReg> Init = new LinkedHashSet<>();
    public Set<VReg> SimpleWork = new LinkedHashSet<>();
    public Set<VReg> FreezeWork = new LinkedHashSet<>();
    public Set<VReg> SpillWork = new LinkedHashSet<>();
    public Set<VReg> SpillNode = new LinkedHashSet<>();
    public Set<VReg> CombineNode = new LinkedHashSet<>();
    public Set<VReg> ColoredNode = new LinkedHashSet<>();
    public Stack<VReg> StackSelect = new Stack<>();
    public Set<VReg> NodeSelect = new HashSet<>();

    public Set<Mov> CombineMove = new LinkedHashSet<>();
    public Set<Mov> ConstrainMove = new LinkedHashSet<>();
    public Set<Mov> FrozenMove = new LinkedHashSet<>();
    public Set<Mov> WorklistMove = new LinkedHashSet<>();
    public Set<Mov> ActiveMove = new LinkedHashSet<>();

    public Set<EdgePair> EdgeSet = new HashSet<>();
    public Map<VReg, Set<VReg>> EdgelistMap = new HashMap<>();
    public Map<VReg, Integer> DegreeMap = new HashMap<>();
    public Map<VReg, Set<Mov>> MovelistMap = new HashMap<>();
    public Map<VReg, VReg> AliasMap = new HashMap<>();
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
    public void pre() {
        PreColor.clear();
        Init.clear();
        SimpleWork.clear();
        FreezeWork.clear();
        SpillWork.clear();
        SpillNode.clear();
        CombineNode.clear();
        ColoredNode.clear();
        StackSelect.clear();
        NodeSelect.clear();
        CombineMove.clear();
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
                Set<VReg> regs = new HashSet<>();
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
    }

    public void run() throws IOException {
        int epoch = 0;
        while(true){
            ++epoch;
            pre();
            LiveTester.visit(func);
            build();
            makeWorkList();
            do {
                if (!SimpleWork.isEmpty()) {
                    simplify();
                } else if (!WorklistMove.isEmpty()) {
                    coalesce();
                } else if (!FreezeWork.isEmpty()) {
                    freeze();
                } else if (!SpillWork.isEmpty()) {
                    selectSpill();
                }
            } while (!SimpleWork.isEmpty() || !WorklistMove.isEmpty() ||
                    !FreezeWork.isEmpty() || !SpillWork.isEmpty());
            assignColors();
            if (SpillNode.isEmpty()) break;
            rewriteProgram();
        }
        removeSelfMov();
        //System.out.println("!!!"+func.RspOffset);
    }

    public boolean isSelfMov(Inst inst) {
        if (!(inst instanceof Mov)) return false;
        Mov mov = (Mov) inst;
        if ((!(mov.dest instanceof VReg)) || (!(mov.src instanceof VReg))) return false;
        return ((VReg) mov.dest).PReg==((VReg) mov.src).PReg;
    }

    public void removeSelfMov() {
        for (Block block : func.Blocks) {
            LinkedList<Inst> Insts = new LinkedList<>();
            for (Inst inst : block.Insts) {
                if (isSelfMov(inst)) continue;
                Insts.add(inst);
            }
            block.Insts=Insts;
        }
    }

    public boolean isMove(Inst inst) {
        if (!(inst instanceof Mov)) return false;
        Mov mov = (Mov) inst;
        return (mov.dest instanceof VReg) && (mov.src instanceof VReg);
    }

    public void build() {
        for (Block block : func.Blocks) {
            Set<VReg> live = new HashSet<>(block.Dies);
            if (block.ReturnTest()==true) {
                for(int i=0;i<5;i++){
                    live.add(VRegs.get(CalleeRegs[i]));
                }
            }
            ListIterator li = block.Insts.listIterator(block.Insts.size());
            while (li.hasPrevious()) {
                Inst inst = (Inst) li.previous();
                List<VReg> define = inst.CalDefine();
                List<VReg> use = inst.CalUse();
                if (isMove(inst)) {
                    Mov mov = (Mov) inst;
                    live.removeAll(use);
                    for (VReg reg : define) {
                        MovelistMap.get(reg).add(mov);
                    }
                    for (VReg reg : use) {
                        MovelistMap.get(reg).add(mov);
                    }
                    WorklistMove.add(mov);
                }
                live.addAll(define);
                for (VReg d : define) {
                    for (VReg l : live) {
                        addEdge(l, d);
                    }
                }
                live.removeAll(define);
                live.addAll(use);
            }
            if (block.EntryTest()==true) {
                List<VReg> define = new ArrayList<>();
                for(int i=0;i<5;i++)define.add(VRegs.get(CalleeRegs[i]));
                live.addAll(define);
                for (VReg d : define) {
                    for (VReg l : live) {
                        addEdge(l, d);
                    }
                }
                live.removeAll(define);
            }
        }
    }

    public void addEdge(VReg u, VReg v) {
        if (u == v || EdgeSet.contains(new EdgePair(u,v))) return;
        EdgeSet.add(new EdgePair(u,v));
        EdgeSet.add(new EdgePair(v,u));
        if (u.PrecolorFlag==false) {
            EdgelistMap.get(u).add(v);
            int d = DegreeMap.get(u) + 1;
            DegreeMap.put(u, d);
        }
        if (v.PrecolorFlag==false) {
            EdgelistMap.get(v).add(u);
            int d = DegreeMap.get(v) + 1;
            DegreeMap.put(v, d);
        }
    }

    public void makeWorkList() {
        List<VReg> regs = new ArrayList<>(Init);
        for (VReg n : regs) {
            Init.remove(n);
            if (DegreeMap.get(n) >= K) {
                SpillWork.add(n);
            } else if (moveRelated(n)) {
                FreezeWork.add(n);
            } else {
                //  System.out.println("FINDSIMPLE:"+n.toString());
                SimpleWork.add(n);
            }
        }
    }

    public Set<VReg> adjacent(VReg n) {
        Set<VReg> neighbors = new HashSet<>();
        for (VReg neighbor : EdgelistMap.get(n)) {
            if (!NodeSelect.contains(neighbor) && !CombineNode.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    public Set<Mov> nodeMoves(VReg n) {
        Set<Mov> moves = new HashSet<>();
        for (Mov mov : MovelistMap.get(n)) {
            if (ActiveMove.contains(mov) || WorklistMove.contains(mov)) {
                moves.add(mov);
            }
        }
        return moves;
    }

    public boolean moveRelated(VReg n) {
        return !nodeMoves(n).isEmpty();
    }

    public void simplify() {
        VReg n = SimpleWork.iterator().next();
        //System.out.println("SIMPLYFIY :"+n.toString());
        SimpleWork.remove(n);
        StackSelect.push(n);
        NodeSelect.add(n);
        for (VReg m : adjacent(n)) {
            decrementDegreeMap(m);
        }
    }

    public void decrementDegreeMap(VReg m) {
        int d = DegreeMap.get(m);
        DegreeMap.put(m, d - 1);
        if (d == K) {
            Set<VReg> nodes = adjacent(m);
            nodes.add(m);
            enableMoves(nodes);
            SpillWork.remove(m);
            if (moveRelated(m)) {
                FreezeWork.add(m);
            } else {
                SimpleWork.add(m);
            }
        }
    }

    public void enableMoves(Set<VReg> nodes) {
        for (VReg n : nodes) {
            for (Mov m : nodeMoves(n)) {
                if (!(ActiveMove.contains(m))) continue;
                ActiveMove.remove(m);
                WorklistMove.add(m);
            }
        }
    }

    public void addWorkList(VReg u) {
        if ((u.PrecolorFlag==false) && (moveRelated(u)==false) && DegreeMap.get(u) < K) {
            FreezeWork.remove(u);
            SimpleWork.add(u);
        }
    }

    public boolean ok(VReg t, VReg r) {
        return DegreeMap.get(t) < K || t.PrecolorFlag==true || EdgeSet.contains(new EdgePair(t,r));
    }

    public boolean conservative(Set<VReg> nodes) {
        int k = 0;
        for (VReg n : nodes) {
            if (DegreeMap.get(n) >= K) ++k;
        }
        return k < K;
    }

    public void coalesce() {
        Mov mov = WorklistMove.iterator().next();
        //assert mov.dest instanceof VReg && mov.src instanceof VReg;
        VReg x = getAliasMap((VReg) mov.dest);
        VReg y = getAliasMap((VReg) mov.src);
        VReg u, v;
        if (y.PrecolorFlag==true) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }
        WorklistMove.remove(mov);
        if (u == v) {
            CombineMove.add(mov);
            addWorkList(u);
        } else if ((v.PrecolorFlag==true) || EdgeSet.contains(new EdgePair(u, v))) {
            ConstrainMove.add(mov);
            addWorkList(u);
            addWorkList(v);
        } else {
            Set<VReg> vAdj = adjacent(v);
            Set<VReg> uvAdj = adjacent(u);
            uvAdj.addAll(vAdj);
            boolean cond1 = u.PrecolorFlag==true;
            if (cond1) {
                for (VReg t : vAdj) {
                    if (!ok(t, u)) {
                        cond1 = false;
                        break;
                    }
                }
            }
            boolean cond2 = (u.PrecolorFlag==false) && conservative(uvAdj);
            if (cond1 || cond2) {
                CombineMove.add(mov);
                combine(u, v);
                addWorkList(u);
            } else {
                ActiveMove.add(mov);
            }
        }
    }

    public void combine(VReg u, VReg v) {
        if (FreezeWork.contains(v)) {
            FreezeWork.remove(v);
        } else {
            SpillWork.remove(v);
        }
        CombineNode.add(v);
        AliasMap.put(v, u);
        MovelistMap.get(u).addAll(MovelistMap.get(v));
        Set<VReg> nodes = new HashSet<>();
        nodes.add(v);
        enableMoves(nodes);
        for (VReg t : adjacent(v)) {
            addEdge(t, u);
            decrementDegreeMap(t);
        }
        if (DegreeMap.get(u) >= K && FreezeWork.contains(u)) {
            FreezeWork.remove(u);
            SpillWork.add(u);
        }
    }

    public VReg getAliasMap(VReg n) {
        if (CombineNode.contains(n)) {
            return getAliasMap(AliasMap.get(n));
        } else {
            return n;
        }
    }

    public void freeze() {
        VReg u = FreezeWork.iterator().next();
        FreezeWork.remove(u);
        SimpleWork.add(u);
        freezeMoves(u);
    }

    public void freezeMoves(VReg u) {
        for (Mov mov : nodeMoves(u)) {
            //assert mov.dest instanceof VReg && mov.src instanceof VReg;
            VReg x = getAliasMap((VReg) mov.dest);
            VReg y = getAliasMap((VReg) mov.src);
            VReg v;
            if (y == getAliasMap(u)) {
                v = x;
            } else {
                v = y;
            }
            ActiveMove.remove(mov);
            FrozenMove.add(mov);
            if (FreezeWork.contains(v) && nodeMoves(v).isEmpty()) {
                FreezeWork.remove(v);
                SimpleWork.add(v);
            }
        }
    }

    public void selectSpill() {
        Iterator<VReg> iter = SpillWork.iterator();
        VReg m = iter.next();
        while ((m.TinyFlag==true) && iter.hasNext()) {
            m = iter.next();
        }
        SpillWork.remove(m);
        SimpleWork.add(m);
        freezeMoves(m);
    }

    public void assignColors() {
        while (!StackSelect.isEmpty()) {
            VReg n = StackSelect.pop();
            // System.out.println("INSTACK: "+n.toString());
            NodeSelect.remove(n);
            Set<Integer>okColors=new HashSet<>();
            for(int i=0;i<14;i++){
                //    System.out.println("+"+ColorRegs[i]);
                okColors.add(ColorRegs[i]);
            }
            for (VReg w : EdgelistMap.get(n)) {
                VReg aw = getAliasMap(w);
                if (ColoredNode.contains(aw) || PreColor.contains(aw)) {
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
        for (VReg n : CombineNode) {
            n.PReg=getAliasMap(n).PReg;
            //  System.out.println(n.toString()+"------------PREG: "+Regs[n.PReg]);
        }
    }

    public void rewriteProgram() {
        func.RspOffset+=SpillNode.size()*8;
        SpillEditor spillEditor = new SpillEditor(SpillNode);
        List<VReg> newTemps = spillEditor.visit(func);
    }

}
