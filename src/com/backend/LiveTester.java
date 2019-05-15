package com.backend;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.nasm.*;
import static com.nasm.RegConst.*;
public class LiveTester {
    public Func func;
    public Map<Block,Set<VReg>> ExVarMap=new HashMap<>();
    public Map<Block,Set<VReg>> KillMap=new HashMap<>();
    public Map<Block,Set<VReg>> DiesMap=new HashMap<>();
    public static void visit(Func func){
        LiveTester tester=new LiveTester(func);
        tester.run();
    }
    public LiveTester(Func fun) {
        func=fun;
    }
    public void run() {
        for (Block block:func.Blocks) {
            ExVarMap.put(block, new HashSet<>());
            KillMap.put(block, new HashSet<>());
            if (block.EntryTest()==true) {
                for (int i = 0; i < 5; i++)
                    KillMap.get(block).add(VRegs.get(CalleeRegs[i]));
            }
            for(Inst inst:block.Insts){
                for(VReg use:inst.CalUse()){
                    if(KillMap.get(block).contains(use))continue;
                    ExVarMap.get(block).add(use);
                }
                for(VReg define:inst.CalDefine())
                    KillMap.get(block).add(define);
            }
        }
        for (Block block:func.Blocks) {
            DiesMap.put(block, new HashSet<>());
            for(Block succ:block.Succs) {
                //System.out.println("SUCCBLOCK:"+succ.name);
                DiesMap.get(block).addAll(ExVarMap.get(succ));
            }
        }
        for(boolean changeFlag=true;changeFlag;){
            changeFlag=false;
            for (Block block:func.Blocks)
                for (Block succ:block.Succs){
                    for (VReg reg:DiesMap.get(succ)){
                        if(KillMap.get(succ).contains(reg))continue;
                        if(DiesMap.get(block).contains(reg))continue;
                        DiesMap.get(block).add(reg);
                        changeFlag=true;
                    }
                }
        }
        for (Block block : func.Blocks)
            block.Dies=DiesMap.get(block);
    }
}