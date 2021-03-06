package com.frontend;
import com.frontend.Scope;
import com.Type.*;
import com.AST.*;
public class ScopeBuilder {
    public static Scope scoperoot;
    public final static FunctionDefineType STRING_PRINT=new FunctionDefineType("print",new VoidType(),new StringType());
    public final static FunctionDefineType STRING_PRINTLN=new FunctionDefineType("println",new VoidType(),new StringType());
    public final static FunctionDefineType GETSTRING=new FunctionDefineType("getString",new StringType());
    public final static FunctionDefineType GETINT=new FunctionDefineType("getInt",new IntType());
    public final static FunctionDefineType TOSTRING=new FunctionDefineType("toString",new StringType(),new IntType());
    public final static FunctionDefineType STRING_ADD=new FunctionDefineType("_ADD",new StringType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_EQ=new FunctionDefineType("_EQ",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_NEQ=new FunctionDefineType("_NEQ",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_LT=new FunctionDefineType("_LT",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_GT=new FunctionDefineType("_GT",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_LEQ=new FunctionDefineType("_LEQ",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_GEQ=new FunctionDefineType("_GEQ",new BoolType(),new StringType(),new StringType());
    public final static FunctionDefineType STRING_LENGTH=new FunctionDefineType("string.length",new IntType());
    public final static FunctionDefineType STRING_SUBSTRING=new FunctionDefineType("string.substring",new StringType(),new IntType(),new IntType());
    public final static FunctionDefineType STRING_PARSEINT=new FunctionDefineType("string.parseInt",new IntType());
    public final static FunctionDefineType STRING_ORD=new FunctionDefineType("string.ord",new IntType(),new IntType());
    public final static FunctionDefineType ARRAY_SIZE=new FunctionDefineType("array.size",new IntType());
    void init(){
        /*
        ClassDefineType stringtype=new ClassDefineType();
        stringtype.typename="string";
        stringtype.add("length",new FunctionDefineType(new IntType()));
        stringtype.add("substring",new FunctionDefineType(new StringType(),new IntType(),new IntType()));
        stringtype.add("parseInt",new FunctionDefineType(new IntType()));
        stringtype.add("ord",new FunctionDefineType(new IntType(),new IntType()));
        scoperoot.add("string",stringtype);*/

        scoperoot.add("string.length",STRING_LENGTH);
        scoperoot.add("string.substring",STRING_SUBSTRING);
        scoperoot.add("string.parseInt",STRING_PARSEINT);
        scoperoot.add("string.ord",STRING_ORD);
        scoperoot.add("array.size",ARRAY_SIZE);
        scoperoot.add("print",STRING_PRINT);
        scoperoot.add("println",STRING_PRINTLN);
        scoperoot.add("getString",GETSTRING);
        scoperoot.add("getInt",GETINT);
        scoperoot.add("toString",TOSTRING);
        scoperoot.add("_ADD",STRING_ADD);
        scoperoot.add("_EQ",STRING_EQ);
        scoperoot.add("_NEQ",STRING_NEQ);
        scoperoot.add("_LT",STRING_LT);
        scoperoot.add("_GT",STRING_GT);
        scoperoot.add("_LEQ",STRING_LEQ);
        scoperoot.add("_GEQ",STRING_GEQ);
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
                ClassDefineType tmptype=new ClassDefineType(sonscope.map,sonscope.offset);
                tmptype.typename=o.name;
                //map.put(tmp.name,sonscope);
                if(!curscope.add(tmp.name,tmptype)){
                    throw new Exception("Redefine"+curnode.loc.toString());
                }
            }else if(o instanceof FunctionDefNode) {
                o.belong=curscope;
                FunctionDefNode tmp = (FunctionDefNode) o;
                Scope sonscope = curscope.addson();
                sonscope.classflag=false;
                //sonscope.classflag=false;
                String name=tmp.name;
                if(o.belong.name.startsWith("."))name="__"+o.belong.name.substring(1)+"_" + tmp.name;
                FunctionDefineType functype = new FunctionDefineType(name,tmp.type);
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
