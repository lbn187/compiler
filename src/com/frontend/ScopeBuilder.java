package com.frontend;
import com.frontend.Scope;
import com.Type.*;
import com.AST.*;
public class ScopeBuilder {
    public Scope scoperoot=new Scope(null,"");
    void init(){
        ClassDefineType stringtype=new ClassDefineType();
        stringtype.typename="string";
        stringtype.add("length",new FunctionDefineType(new IntType()));
        stringtype.add("substring",new FunctionDefineType(new StringType(),new IntType(),new IntType()));
        stringtype.add("parseInt",new FunctionDefineType(new IntType()));
        stringtype.add("ord",new FunctionDefineType(new IntType(),new IntType()));
        scoperoot.add("string",stringtype);
        FunctionDefineType printtype=new FunctionDefineType(new VoidType(),new StringType());
        scoperoot.add("print",printtype);
        FunctionDefineType printlntype=new FunctionDefineType(new VoidType(),new StringType());
        scoperoot.add("println",printlntype);
        FunctionDefineType getStringtype=new FunctionDefineType(new StringType());
        scoperoot.add("getString",getStringtype);
        FunctionDefineType getInttype=new FunctionDefineType(new IntType());
        scoperoot.add("getInt",getInttype);
        FunctionDefineType toStringtype=new FunctionDefineType(new StringType(),new IntType());
        scoperoot.add("toString",toStringtype);
    }
    void get(Scope curscope,Node curnode)throws Exception{
        curnode.belong=curscope;
        //System.out.println("<Node> name="+curnode.name+" scopename"+curnode.belong.name);
        for(Node o:curnode.getAll()){
            if(o instanceof ClassDefNode){
                ClassDefNode tmp=(ClassDefNode)o;
                Scope sonscope=curscope.addson();
                get(sonscope,tmp);
                ClassDefineType tmptype=new ClassDefineType(sonscope.map);
                if(!curscope.add(tmp.name,tmptype)){
                    throw new Exception("Redefine");
                }
            }else if(o instanceof FunctionDefNode) {
                FunctionDefNode tmp = (FunctionDefNode) o;
                Scope sonscope = curscope.addson();
                FunctionDefineType functype = new FunctionDefineType(tmp.type);
                for (VariableDefNode u : tmp.variables) {
                    VariableDefNode ttmp = u;
                    ttmp.belong = sonscope;
                    functype.add(ttmp.type);
                    //System.out.print("SONSCOPE name="+ttmp.name+" type="+ttmp.type.typename);
                    if (!sonscope.add(ttmp.name, ttmp.type)) {
                        throw new Exception("Redefine");
                    }
                }
                if (!curscope.add(tmp.name,functype)) {
                    throw new Exception("Redefine");
                }
                localresolver(sonscope,tmp.block);
            }else if(o instanceof VariableDefNode){
                VariableDefNode tmp=(VariableDefNode)o;
                get(curscope,tmp);
                if(!curscope.add(tmp.name,tmp.type)){
                    throw new Exception("Redefine");
                }
            }else{
                get(curscope,o);
            }
        }
    }
    void localresolver(Scope curscope,Node curnode)throws Exception{
        curnode.belong=curscope;
       // System.out.println("<Node> name="+curnode.name+" scopename="+curnode.belong.name);
        for(Node o:curnode.getAll()){
            if((o instanceof BlockNode)||((curnode instanceof ForNode)||(curnode instanceof WhileNode)||(curnode instanceof IfNode))&&(o instanceof StmtNode)){
                Scope sonscope=curscope.addson();
                localresolver(sonscope,o);
            }else if(o instanceof VariableDefNode){
                VariableDefNode tmp=(VariableDefNode)o;
                localresolver(curscope,tmp);
                if(!curscope.add(tmp.name,tmp.type)){
                    throw new Exception("Redefine");
                }
            }else localresolver(curscope,o);
        }
    }
    public void work(Node astroot) throws Exception {
        init();
        get(scoperoot,astroot);
    }
    /*
     * There is three things to do:
     * Matching the name of each var to their definition.
     * Matching the type of each var to their definition.
     * Check the validity of expressions
     * */
}
