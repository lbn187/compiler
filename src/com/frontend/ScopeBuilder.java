package com.frontend;
import com.frontend.Scope;
import com.Type.*;
import com.AST.*;
public class ScopeBuilder {
    public Scope scoperoot;
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
    void get(Scope curscope,Node curnode,int ok)throws Exception{
        curnode.belong=curscope;
        //System.out.println("<Node> name="+curnode.name+" scopename"+curnode.belong.name);
        for(Node o:curnode.getAll()){
            if(o instanceof ClassDefNode){
                ClassDefNode tmp=(ClassDefNode)o;
                Scope sonscope=curscope.addson();
                sonscope.classflag=true;
                get(sonscope,tmp,1);
                ClassDefineType tmptype=new ClassDefineType(sonscope.map);
                //map.put(tmp.name,sonscope);
                if(!curscope.add(tmp.name,tmptype)){
                    throw new Exception("Redefine"+curnode.loc.toString());
                }
            }else if(o instanceof FunctionDefNode) {
                o.belong=curscope;
                FunctionDefNode tmp = (FunctionDefNode) o;
                Scope sonscope = curscope.addson();
                sonscope.classflag=false;
                FunctionDefineType functype = new FunctionDefineType(tmp.type);
                for (VariableDefNode u : tmp.variables) {
                    VariableDefNode ttmp = u;
                    ttmp.belong = sonscope;
                    functype.add(ttmp.type);
                    //System.out.print("SONSCOPE name="+ttmp.name+" type="+ttmp.type.typename);
                    //if (!sonscope.add(ttmp.name, ttmp.type)) {
                    //    throw new Exception("Redefine"+curnode.loc.toString());
                    //}
                }
                if (!curscope.add(tmp.name,functype)) {
                    throw new Exception("Redefine"+curnode.loc.toString());
                }
          //      if (!sonscope.add(tmp.name,functype)) {
            //        throw new Exception("Redefine"+curnode.loc.toString());
              //  }
                localresolver(sonscope,tmp.block);
            }else if(o instanceof VariableDefNode){
                VariableDefNode tmp=(VariableDefNode)o;
                get(curscope,tmp,ok);
                if(ok==1){
                    o.inclass=true;
                    //System.out.println("ADD name="+tmp.name+" type="+tmp.type.typename);
                    if(!curscope.add(tmp.name,tmp.type)){
                        throw new Exception("Redefine"+curnode.loc.toString());
                    }
                }
            }else{
                get(curscope,o,ok);
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
            }else localresolver(curscope,o);
                /*if(o instanceof VariableDefNode){
                VariableDefNode tmp=(VariableDefNode)o;
                localresolver(curscope,tmp);
                if(!curscope.add(tmp.name,tmp.type)){
                    throw new Exception("Redefine");
                }
            }else localresolver(curscope,o);*/
        }
    }
    public void work(Node astroot) throws Exception {
        scoperoot=new Scope();
        if(scoperoot==null)System.out.println("!!!");
        init();
        if(scoperoot==null)System.out.println("!!!");
        get(scoperoot,astroot,0);
    }
}
