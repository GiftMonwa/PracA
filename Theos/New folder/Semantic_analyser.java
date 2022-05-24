import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Semantic_analyser
{
    static int sl;
    static int pcount =0;
    static ArrayList<Node> ast = new ArrayList<>();
    static ArrayList<renamedProc> renamedProcs = new ArrayList<>();
    static ArrayList<SemanticError> error = new ArrayList<>();
    static FileWriter symentic;
    public static void main(String [] args) throws IOException {

        symentic = new FileWriter("semantic_ast.txt");
        ast = TreeBuilder.ast;
        sl = Collections.max(ast, Comparator.comparing(P ->P.scope)).scope;

        System.out.println(ast.size());

        // rename_var();
        rename_proc();



        ast.get(0).printed("",true,TreeBuilder.sym_table,false);
        symentic.close();

    }

    static void rename_proc()
    {
        int count = 0;
        for(int j = 0 ; j < ast.size(); j++)
        {

            System.out.println(renamedProcs);


            if(ast.get(j).description.equals("ProcDefs"))
            {
                Node temp = ast.get(j).children.get(0).children.get(0);
                renamedProc newSymbol = new renamedProc(temp.procedure_name, "P"+pcount, temp.scope);
                renamedProcs.add(newSymbol);
                temp.new_name = "P"+pcount++;

            }
           /** if(ast.get(j).description.equals("PCALL"))
            {
                j++;
                String newname = "U";
                for(int i =0; i < renamedProcs.size(); i++)
                {
                    renamedProc ptr = renamedProcs.get(i);
                    if(ptr.oldname.contains( ast.get(j).variable_name) && ptr.scopeL == ast.get(j).scope)
                    {

                     /**
                      * newname = ptr.newname;

                    }

                }
                ast.get(j).new_name = newname;
            }*/
        }
    }

    static void rename_var()
    {
        ArrayList<renamedProc> renamedProcs = new ArrayList<>();
        int count = 0;
        for(int j = 0 ; j < ast.size(); j++)
        {
            if(!renamedProcs.isEmpty() && ast.get(j).description.equals("PROG"))
            {
                renamedProc temp = renamedProcs.get(renamedProcs.size()-1);
                if( ast.get(j).scope <= temp.scopeL)
                {
                    int s = temp.scopeL;
                    while (!renamedProcs.isEmpty()&&renamedProcs.get(renamedProcs.size()-1).scopeL -1 >= s)
                    {
                        renamedProcs.remove(renamedProcs.size()-1);
                    }
                }
            }
            if( ast.get(j).description.equals("DECL"))
            {
                j+=4;
                renamedProc newSymbol = new renamedProc( ast.get(j).variable_name, "V"+count++,  ast.get(j).scope);
                ast.get(j).new_name = newSymbol.newname;
                renamedProcs.add(newSymbol);
            }
            else
            {
                if( ast.get(j).description.contains("variable"))
                {
                    String newname = "U";
                    for(int i =0; i < renamedProcs.size(); i++)
                    {
                        renamedProc ptr = renamedProcs.get(i);
                        if(ptr.oldname.contains( ast.get(j).variable_name))
                            newname = ptr.newname;
                    }
                    ast.get(j).new_name = newname;
                }
            }
        }
    }


    public static void helper(Node curr, ArrayList<renamedProc> renamedProcs){

        while (curr!=null){



            if(curr.description.contains("SPL")){
                if(curr.children.size()!=0){
                    curr = curr.children.get(0);
                    if(curr.description.contains("ProcDefs"))
                    continue;
                }else {
                    curr=null;
                    continue;
                }

            }
            if(curr.description.contains("ProcDefs")){
                Node temp = curr.children.get(0).children.get(0);
                renamedProc newSymbol = new renamedProc(temp.procedure_name, "P"+pcount, temp.scope);
                renamedProcs.add(newSymbol);
                //System.out.println(renamedProcs.get(0).oldname);
                temp.new_name = "P"+pcount++;
                if(curr.children.size()!=0){
                    curr = curr.children.get(0);
                    continue;
                }else {
                    curr=null;
                    continue;
                }
            }

            if(curr.description.contains("PD")){
                if(curr.children.size()==2){
                    curr=curr.children.get(1);
                    Node temp = curr.children.get(0).children.get(0);
                    renamedProc newSymbol = new renamedProc(temp.procedure_name, "P"+pcount, temp.scope);
                    renamedProcs.add(newSymbol);
                    temp.new_name = "P"+pcount++;
                    if(curr.children.size()!=0){
                        curr = curr.children.get(0);
                        continue;
                    }else {
                        curr=null;
                        continue;
                    }
                }
                else{
                    curr=null;
                    continue;
                }

            }
        }
    }

}
