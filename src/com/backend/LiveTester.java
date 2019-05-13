package com.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.nasm.*;
import static com.nasm.RegConst.*;
public class LiveTester {
    public Func func;
    public Map<Block, Set<VReg>> ueVarMap = new HashMap<>();
    public Map<Block, Set<VReg>> varKillMap = new HashMap<>();
    public Map<Block, Set<VReg>> liveOutMap = new HashMap<>();
    public static void visit(Func func){
        LiveTester tester=new LiveTester(func);
        tester.run();
    }
    public LiveTester(Func fun) {
        func=fun;
    }
    public void run() {
        for (Block block : func.Blocks) {
            ueVarMap.put(block, new HashSet<>());
            varKillMap.put(block, new HashSet<>());
            if (block.EntryTest()==true) {
                for(int i=0;i<5;i++)varKillMap.get(block).add(VRegs.get(CalleeRegs[i]));
            }
            for(Inst inst:block.Insts){
                for(VReg use:inst.CalUse()){
                    if(varKillMap.get(block).contains(use))continue;
                    ueVarMap.get(block).add(use);
                }
                for(VReg define:inst.CalDefine())
                    varKillMap.get(block).add(define);
            }
        }

        for (Block block : func.Blocks) {
            liveOutMap.put(block, new HashSet<>());
            for(Block succ:block.Succs){
                liveOutMap.get(block).addAll(ueVarMap.get(succ));
            }
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : func.Blocks) {
                for (Block succ : block.Succs) {
                    for (VReg reg : liveOutMap.get(succ)) {
                        if (varKillMap.get(succ).contains(reg) || liveOutMap.get(block).contains(reg)) {
                            continue;
                        }
                        liveOutMap.get(block).add(reg);
                        changed = true;
                    }
                }
            }
        }

        for (Block block : func.Blocks) {
            block.Dies=liveOutMap.get(block);
        }
    }
}
