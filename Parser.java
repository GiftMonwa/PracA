import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;





public class Parser {
    ArrayList<token> tList=new ArrayList<token>();
    //String TK;  //holds the token
    String xml="";   //The xml buffer
    static int index=0;

    Parser(ArrayList<token> e)
    {   
        for(token i: e)
        {
            token tk=new token(i.ID,i.Class,i.contents); 
            tList.add(tk);
        }
    }

    public void parser()  //The parser
    {
        if(SPLProgr())  //No error
        {
            try 
            {
                FileWriter myWriter = new FileWriter("Parser.xml");
                myWriter.write(xml);
                myWriter.close();
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
        else{return;}

    }

    public boolean SPLProgr()
    {
        xml+="<SPLProgr>";
        if(!tList.isEmpty())
        {
            if(index<tList.size() && tList.get(index).contents.equals("proc"))  //checking if Procdef is null
            {
                if(ProcDefs()){}else{return false;}
                index++;
            }

            if(index<tList.size() && tList.get(index).contents.equals("main"))
            {
                xml+="main";
                index++; //The next token that follows main
                if(lCurly()){}  //if there's no curly bracket its a syntax error
                else{return false;};   //left Curly bracket

                index++;
                if(isAlgorithm())
                {
                    //System.out.println("I got here");
                    if(Algorithm()){}else{return false;}
                    //index++;
                }
                
                if(index<tList.size() && tList.get(index).contents.equals("halt"))
                {
                    xml+="halt";
                }
                else{
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected ; after halt");
                    return false;
                }
                index++;
                

                if(semiColon()){}else{return false;};
                index++;
               // System.out.println("I got here");

                if(Declaration())
                {
                    if(VarDecl()){index++;}
                }

                if(Rcurly()){}else{return false;};
            }
            else
            {
                System.out.print("SYNTAX ERROR at "+ tList.get(index).ID+"Invalid start to a program,expected proc or main");
                return false;
            }
        }
        xml+="</SPLProgr>";
        return true;
    }

    public boolean ProcDefs()
    {
        xml+="<ProcDefs>";
        
        if(PD()){}else{return false;};

        index++;
        if(Comma()){}else{return false;};  //check if the next input is  a comma

        index++;
        if(index<tList.size() && tList.get(index).contents.equals("proc"))
        {
            if(ProcDefs()){}else{return false;};
        } //If its null we continue
        else
        {
            index--;
        }
        xml+="</ProcDefs>";
        
        return true;
    }

    public boolean Algorithm()
    {
        xml+="<Algorithm>";
        if(Instr()){}else{return false;}
        
        index++;
        if(index<tList.size() && tList.get(index).contents.equals(";"))
        {
            
            xml+=";";
            index++;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected a ; after instruction");
            return false;
        }

        if(isAlgorithm())
        {
            if(Algorithm()){}else{return false;}
            //index++;
        }
        xml+="</Algorithm>";
        return true;
    }

    public boolean VarDecl()  //Needs revisiting
    {
        xml+="<VarDecl>";
        if(Dec()){}else{return false;}
        index++;
        if(semiColon()){}else{return false;}

        index++;
        if(Declaration())  //can be null
        {
            VarDecl();
        }
        else
        {
            index--;
        }
        xml+="</VarDecl>";
        return true;
    }

    public boolean PD()
    {
        xml+="<PD>";
        if(index<tList.size() && tList.get(index).contents.equals("proc"))
        {
            xml+="proc";   
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" PD begins with proc");
            return false;
        }
        
        index++;
        if(Variable()){}else{return false;};  //Adding a userDefined variable

        index++;
        if(lCurly()){}else{return false;};

        index++;
        if(index<tList.size() && tList.get(index).contents.equals("proc"))  //Check if procdefs is null
        {
            if(ProcDefs()){}else{return false;}
            index++;
        }

        //System.out.println("i WILL Graduate");
        if(isAlgorithm())
        {
            if(Algorithm()){}else{return false;}
            //System.out.println("i WILL Graduate");
            //index++;
        }

        if(index<tList.size() && tList.get(index).contents.equals("return"))
        {
            xml+="return";
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected return kw");
        }
        index++;

        if(semiColon()){}
        else{
            System.out.println("SYNTAX ERROR AT "+tList.get(index).ID+" Expected a ; after return");
        };
        
        index++;
        if(Declaration())
        {
            
            if(VarDecl()){}else{return false;};
            index++;
        }
        Rcurly();
        xml+="</PD>";
        return true;
    }

    public boolean Instr()
    {
        xml+="<Instr>";
        if(index<tList.size())
        {
            if(tList.get(index).contents.equals("output") || tList.get(index).Class.equals("var"))  //The Assign Production
            {
                if(Assign()){}else{return false;}
            }
            else if(tList.get(index).contents.equals("if"))   //Branch Production
            {
                if(Branch()){}else{return false;}
            }
            else if(tList.get(index).contents.equals("while") || tList.get(index).contents.equals("do"))  //The Loop Production
            {
                if(Loop()){}else{return false;}
            }
            else if(tList.get(index).contents.equals("call"))  //The Pcall Production
            {
                if(PCall()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid start to an Instruction");
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" index out of bounds in Instr");
            return false;
        }
        xml+="</Instr>";
        return true;
    }

    public boolean Assign()
    {
        xml+="<Assign>";
        if(index<tList.size())
        {
            if(LHS()){}else{return false;}
            index++;
            if(index<tList.size() && tList.get(index).contents.equals(":="))
            {
                xml+=":=";
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
            index++;
            if(Expr()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" index Out of bounds ins Assign");
            return false;
        }
        xml+="</Assign>";
        return true;
    }

    public boolean Branch()
    {
        xml+="<Branch>";
        if(index<tList.size() && tList.get(index).contents.equals("if"))
        {
            xml+="if";
            index++;
            if(lParenth()){}else{return false;}
            index++;
            if(Expr()){}else{return false;}
            index++;
            if(rParenth()){}else{return false;}
            index++;
            if(index<tList.size() && tList.get(index).contents.equals("then"))
            {
                xml+="then";
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected a then statement");
                return false;
            }
            index++;
            if(lCurly()){}else{return false;}
            index++;
            if(isAlgorithm())
            {

                if(Algorithm()){}else{return false;}
                //index++;
            }
            if(Rcurly()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Index out of bounds in Branch");
            return false;
        }
        
        xml+="</Branch>";
        return true;
    }

    public boolean Loop()
    {
        xml+="<Loop>";
        if(index<tList.size())
        {
            if(tList.get(index).contents.equals("do"))
            {
                xml+="do";
                index++;
                if(lCurly()){}else{return false;}
                index++;
                if(isAlgorithm())
                {
                    if(Algorithm()){}else{return false;}
                    //index++;
                }
                if(Rcurly()){}else{return false;}
                index++;
                if(index<tList.size() && tList.get(index).contents.equals("until"))
                {
                    xml+="until";
                    index++;
                    if(lParenth()){}else{return false;}
                    index++;
                    if(Expr()){}else{return false;}
                    index++;
                    if(rParenth()){}else{return false;}
                }
                else{
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected the until kw");
                    return false;
                }
            }
            else if(tList.get(index).contents.equals("while"))
            {
                xml+="while";
                index++;
                if(lParenth()){}else{return false;}
                index++;
                if(Expr()){}else{return false;}
                index++;
                if(rParenth()){}else{return false;}
                index++;
                if(index<tList.size() && tList.get(index).contents.equals("do"))
                {
                    xml+="do";
                    
                }
                else{
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected the do keyword");
                    return false;   
                }
                index++;
                if(lCurly()){}else{return false;}
                
                index++;
                if(isAlgorithm())
                {
                    if(Algorithm()){}else{return false;}
                    //index++;
                }
                //System.out.println(tList.get(index-1).contents);
                if(Rcurly()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid Loop,expected Do or while");
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" index out of bounds in Loop");
            return false;
        }

        xml+="</Loop>";
        return true;
    }

    public boolean PCall()
    {
        xml+="<PCall>";
        if(index<tList.size() && tList.get(index).contents.equals("call"))
        {
            xml+="call";
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" EXPECTED KW call");
            return false;
        }

        index++;
        if(Var()){}else{return false;}
        xml+="</PCall>";
        return true;
    }

    public boolean LHS()
    {
        xml+="<LHS>";
        if(index<tList.size())
        {
            if(tList.get(index).contents.equals("output"))
            {
                xml+="output";
            }
            else if(index+1<tList.size() && tList.get(index).Class.equals("var") && tList.get(index+1).contents.equals("[")) //Its a field
            {
                if(Field()){}else{return false;}
            }
            else if(tList.get(index).Class.equals("var"))  //Has to be a variable
            {
                if(Var()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid LHS");
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+ "index Out of bounds in LHS");
            return false;
        }
        xml+="</LHS>";
        return true;
    }

    public boolean Expr()
    {
        xml+="<Expr>";
        if(index<tList.size() && tList.get(index).Class.equals("string") || tList.get(index).Class.equals("num") || tList.get(index).contents.equals("true") || tList.get(index).Class.equals("false"))   //Const production
        {
           if(Const()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).Class.equals("var"))  //further check if its a field or Var
        {
            if(index+1<tList.size() && tList.get(index+1).contents.equals("[")) //Its a field
            {
                if(Field()){}else{return false;}
            }
            else  if(tList.get(index).Class.equals("var")) //Has to be a variable
            {
                if(Var()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID +" Invalid Expr");
                return false;
            }
        }
        else if(index<tList.size() && tList.get(index).contents.equals("input") || tList.get(index).contents.equals("not"))  //UnOp production
        {
            if(UnOp()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).contents.equals("and")||tList.get(index).contents.equals("or") || tList.get(index).contents.equals("eq") || tList.get(index).contents.equals("larger") || tList.get(index).contents.equals("add") || tList.get(index).contents.equals("sub") || tList.get(index).contents.equals("mult"))
        {
            //BinOp production
            if(BinOp()){}else{return true;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" index out of bounds in Expr");
            return false;
        }
        xml+="</Expr>";
        return true;
    }

    public boolean Alternat()
    {
        xml+="<Alternat>";
        xml+="else";
        index++;
        if(lCurly()){}else{return false;}
        index++;
        if(isAlgorithm())
        {
            if(Algorithm()){}else{return false;}
            //index++;
        }
        if(Rcurly()){}else{return false;}
        xml+="</Alternat>";
        return true;
    }

    public boolean Var()
    {
        xml+="<Var>";
        if(Variable()){}else{return false;}
        xml+="</Var>";
        return true;
    }

    public boolean Field()
    {
        xml+="<Field>";
        if(index<tList.size() && tList.get(index).Class.equals("var"))
        {
            xml+=tList.get(index).contents;

            index++;
            if(lBracket()){}else{return false;}

            index++;
            if(index<tList.size() && tList.get(index).Class.equals("var"))  //The Var production
            {
                if(Var()){}else{return false;}
            }
            else if(index<tList.size() && (tList.get(index).Class.equals("num") || tList.get(index).Class.equals("string") || tList.get(index).contents.equals("true")||tList.get(index).contents.equals("false"))) //The Const production
            {
                if(Const()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid details inside []");
                return false;
            }
            index++;
            if(rBracket()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Index out of bounds in Field");
            return false;
        }
        
        xml+="</Field>";
        return true;
    }

    public boolean Const()
    {
        xml+="<Const>";
        if(index<tList.size() && tList.get(index).Class.equals("num"))
        {
            xml+=tList.get(index).contents;
        }
        else if(index<tList.size() && tList.get(index).Class.equals("string"))
        {
            xml+=tList.get(index).contents;
        }
        else if(index<tList.size() && tList.get(index).contents.equals("true"))
        {
            xml+="true";
        }
        else if(index<tList.size() && tList.get(index).contents.equals("false"))
        {
            xml+="false";
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid Type");
            return false;
        }
        
        xml+="</Const>";
        return true;
    }

    public boolean UnOp()
    {
        xml+="<UnOp>";
        if(index<tList.size() && tList.get(index).contents.equals("input"))
        {
            xml+="input";
            index++;
            if(lParenth()){}else{return false;}
            index++;
            if(Var()){}else{return false;}
            index++;
            if(rParenth()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).contents.equals("not"))
        {
            xml+="not";
            index++;
            if(lParenth()){}else{return false;}
            index++;
            if(Expr()){}else{return false;}  //To be fixed
            index++;
            if(rParenth()){}else{return false;}
        }
        xml+="</UnOp>";
        return true;
    }

    public boolean BinOp()
    {
        xml+="<BinOp>";
        if(index<tList.size()){

            if(tList.get(index).contents.equals("and"))
            {
                xml+="and";
            }
            else if(tList.get(index).contents.equals("or"))
            {
                xml+="or";
            }
            else if(tList.get(index).contents.equals("eq"))
            {
                xml+="eq";
            }
            else if(tList.get(index).contents.equals("larger"))
            {
                xml+="larger";
            }
            else if(tList.get(index).contents.equals("add"))
            {
                xml+="add";
            } 
            else if(tList.get(index).contents.equals("sub"))
            {
                xml+="sub";
            }
            else if(tList.get(index).contents.equals("mult"))
            {
                xml+="and";
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Invalid binary operator");
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Index out of bounds in BinOp");
            return false;
        }

        index++;
        if(lParenth()){}else{return false;}

        index++;
        if(Expr()){}else{return false;}

        index++;
        if(Comma()){}else {return false;}

        index++;
        if(Expr()){}else {return false;}

        index++;
        if(rParenth()){}else{return false;}

        xml+="</BinOp>";
        return true;
    }

    public boolean Dec()
    {
        xml+="<Dec>";
        if(index<tList.size() && tList.get(index).contents.equals("num") || tList.get(index).contents.equals("bool") || tList.get(index).contents.equals("string"))//choose first production
        {
            //The production Dec->TYP Var
            if(TYP()){}else{return false;}  //If Dec doesnt start with num/bool/string
            index++;
            if(Var()){}else{return false;}

        }
        else if(index<tList.size() && tList.get(index).contents.equals("arr"))   //We choose the second  production
        {
            //The Production Dec->arr TYP[const]
            xml+="arr";
            index++;
            if(TYP()){}else{return false;}
            index++;
            if(lBracket()){}else{return false;}
            index++;
            if(Const()){}else{return false;}
            index++;
            if(rBracket()){}else{return false;}
            index++;
            if(Var()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+ "Index out of bounds in Dec");
            return false;
        }
        xml+="</Dec>";
        return true;
    }

    public boolean TYP()
    {
        xml+="<TYP>";
        if(index<tList.size() && tList.get(index).contents.equals("num"))
        {
            xml+="num";
        }
        else if(index<tList.size() && tList.get(index).contents.equals("string"))
        {
            xml+="string";
        }
        else if(index<tList.size() && tList.get(index).contents.equals("bool"))
        {
            xml+="bool";
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Error in TYP");
            return false;
        }
        xml+="</TYP>";
        return true;
    }



    //Adding Terminals
    public boolean lCurly()
    {
        if(index<tList.size() && tList.get(index).contents.equals("{"))  //Left curly
        {
            xml+="{";
            return true;
        }
        else{
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" EXPECTED {");
                return false;
        }
    }

    public boolean Rcurly()
    {
        if(index<tList.size() && tList.get(index).contents.equals("}"))  //Left curly
        {
            xml+="}";
            return true;
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+" Expected }");
            return false;
        }
    }

    public boolean Comma()
    {
        if(index<tList.size() && tList.get(index).contents.equals(","))  //Left curly
        {
            xml+=",";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a ,");
            return false;
        }
    }

    public boolean lParenth()
    {
        if(index<tList.size() && tList.get(index).contents.equals("("))  //Left curly
        {
            xml+="(";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a (");
            return false;
        }
    }

    public boolean rParenth()
    {
        if(index<tList.size() && tList.get(index).contents.equals(")"))  //Left curly
        {
            xml+=")";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a )");
            return false;
        }
    }

    public boolean lBracket()
    {
        if(index<tList.size() && tList.get(index).contents.equals("["))  //Left curly
        {
            xml+="[";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a [");
            return false;
        }
    }

    public boolean rBracket()
    {
        if(index<tList.size() && tList.get(index).contents.equals("]"))  //Left curly
        {
            xml+="]";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a ]");
            return false;
        }
    }

    public boolean semiColon()
    {
        if(index<tList.size() && tList.get(index).contents.equals(";"))  //Left curly
        {
            xml+=";";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID+"Expected a ;");
            return false;
        }
    }

    public boolean Variable()
    {
        if(index<tList.size() && tList.get(index).Class.equals("var"))  //A variable name
        {
            xml+=tList.get(index).contents;
            return true;
        }
        else
        {
            //System.out.print("SYNTAX ERROR");
            return false;
        }
    }


    //Helper Functions
    public boolean isAlgorithm()
    {
        if(index<tList.size() && tList.get(index).contents.equals("output") || tList.get(index).contents.equals("if") || tList.get(index).contents.equals("do") || tList.get(index).contents.equals("while") || tList.get(index).contents.equals("call") || tList.get(index).Class.equals("var"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean Declaration()
    {
        if(index<tList.size() && tList.get(index).contents.equals("string") || tList.get(index).contents.equals("num") || tList.get(index).contents.equals("bool") || tList.get(index).contents.equals("arr"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

  

}
