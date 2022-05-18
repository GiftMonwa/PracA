import java.util.ArrayList;
//package com.mkyong.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;





public class Parser {
    ArrayList<token> tList;
    String TK;  //holds the token
    DocumentBuilderFactory dFactory;
    DocumentBuilder dBuilder;
    Document doc;
    ArrayList<Element> elements=new ArrayList<>();
    String xml;   //The xml buffer
    static int index=0;

    Parser(ArrayList<token> e) throws Exception
    {  
        dFactory=DocumentBuilderFactory.newInstance();
        dBuilder=dFactory.newDocumentBuilder();
        doc = dBuilder.newDocument();
         
        for(token i: e)
        {
            tList.add(i);
        }
    }

    public void SPLProgr()
    {
        xml+="<SPLProgr>";
        if(!tList.isEmpty())
        {
            if(index<tList.size() && tList.get(index).contents=="proc")  //checking if Procdef is null
            {
                ProcDefs();
            }

            index++;

            if(index<tList.size() && tList.get(index).contents=="main")
            {
                xml+="main";
                index++; //The next token that follows main
                if(lCurly()){}  //if there's no curly bracket its a syntax error
                else{return;};   //left Curly bracket

                index++;
                isAlgorithm();  //Parse Algorithm
                index++;
                if(index<tList.size() && tList.get(index).contents=="halt")
                {
                    xml+="halt";
                }
                else{
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                    return;
                }

                index++;

                if(semiColon()){}else{return;};
                index++;
                if(Variable()){}else{return;};
                index++;
                if(Rcurly()){}else{return;};
            }
            else
            {
                System.out.print("SYNTAX ERROR");
                return;
            }
        }
        xml+="</SPLProgr";
    }

    public boolean ProcDefs()
    {
        xml+="<ProcDefs>";
        if(PD()){}else{return false;};
        
        index++;
        if(Comma()){}else{return false;};  //check if the next input is  a comma

        index++;
        if(index<tList.size() && tList.get(index).contents=="proc")
        {
            ProcDefs();
        } //If its null we continue
        xml+="</ProcDefs>";
        return true;
    }

    public boolean Algorithm()
    {
        xml+="<Algorith>";
        if(Instr()){}else{return false;}
        index++;
        if(index<tList.size() && tList.get(index).contents==";")
        {
            xml+=";";
            index++;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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

    public boolean VarDecl()
    {
        xml+="<VarDecl>";
        if(Dec()){}else{return false;}
        index++;
        if(semiColon()){}else{return false;}
        index++;
        if(VarDecl()){};
        xml+="</VarDecl>";
        return true;
    }

    public boolean PD()
    {
        xml+="<PD>";
        if(index<tList.size() && tList.get(index).contents=="proc")
        {
            xml+="proc";   
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        
        index++;
        if(Variable()){}else{return false;};  //Adding a userDefined variable
        
        index++;
        if(lCurly()){}else{return false;};

        index++;
        if(index<tList.size() && tList.get(index).contents=="proc")  //Check if procdefs is null
        {
            if(ProcDefs()){}else{return false;}
            index++;
        }

        if(isAlgorithm())
        {
            if(Algorithm()){}else{return false;}
            index++;
        }

        if(index<tList.size() && tList.get(index).contents=="return")
        {
            xml+="return";
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
        }
        index++;
        semiColon();
        index++;
        Declaration();
        index++;
        Rcurly();
        xml+="</PD>";
        return true;
    }

    public boolean Instr()
    {
        xml+="<Instr>";
        if(index<tList.size())
        {
            if(tList.get(index).contents=="output" || tList.get(index).Class=="var")  //The Assign Production
            {
                if(Assign()){}else{return false;}
            }
            else if(tList.get(index).contents=="if")   //Branch Production
            {
                if(Branch()){}else{return false;}
            }
            else if(tList.get(index).contents=="while" || tList.get(index).contents=="do")  //The Loop Production
            {
                if(Loop()){}else{return false;}
            }
            else if(tList.get(index).contents=="call")  //The Pcall Production
            {
                if(PCall()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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
            if(index<tList.size() && tList.get(index).contents==":=")
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
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        xml+="</Assign>";
        return true;
    }

    public boolean Branch()
    {
        xml+="<Branch>";
        if(index<tList.size() && tList.get(index).contents=="if")
        {
            xml+="if";
            index++;
            if(lParenth()){}else{return false;}
            index++;
            if(Expr()){}else{return false;}
            index++;
            if(rParenth()){}else{return false;}
            index++;
            if(index<tList.size() && tList.get(index).contents=="then")
            {
                xml+="then";
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
            index++;
            if(lCurly()){}else{return false;}
            index++;
            if(isAlgorithm())
            {
                if(Algorithm()){}else{return false;}
                index++;
            }
            if(Rcurly()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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
            if(tList.get(index).contents=="do")
            {
                xml+="do";
                index++;
                if(lCurly()){}else{return false;}
                index++;
                if(isAlgorithm())
                {
                    if(Algorithm()){}else{return false;}
                    index++;
                }
                if(Rcurly()){}else{return false;}
                index++;
                if(index<tList.size() && tList.get(index).contents=="until")
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
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                    return false;
                }
            }
            else if(tList.get(index).contents=="do")
            {
                xml+="while";
                index++;
                if(lParenth()){}else{return false;}
                index++;
                if(Expr()){}else{return false;}
                index++;
                if(rParenth()){}else{return false;}
                index++;
                if(index<tList.size() && tList.get(index).contents=="do")
                {
                    xml+="do";
                }
                else{
                    System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                    return false;   
                }
                index++;
                if(lCurly()){}else{return false;}
                index++;
                if(isAlgorithm())
                {
                    if(Algorithm()){}else{return false;}
                    index++;
                }
                if(Rcurly()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }

        xml+="</Loop>";
        return true;
    }

    public boolean PCall()
    {
        xml+="<PCall>";
        if(index<tList.size() && tList.get(index).contents=="call")
        {
            xml+="call";
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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
            if(tList.get(index).contents=="output")
            {
                xml+="output";
            }
            else if(index+1<tList.size() && tList.get(index).Class=="var" && tList.get(index+1).contents=="[") //Its a field
            {
                if(Field()){}else{return false;}
            }
            else if(tList.get(index).Class=="var")  //Has to be a variable
            {
                if(Var()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        xml+="</LHS>";
        return true;
    }

    public boolean Expr()
    {
        xml+="<Expr>";
        if(index<tList.size() && tList.get(index).Class=="string" || tList.get(index).Class=="num" || tList.get(index).contents=="true" || tList.get(index).Class=="false")   //Const production
        {
           if(Const()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).Class=="var")  //further check if its a field or Var
        {
            if(index+1<tList.size() && tList.get(index).Class=="var" && tList.get(index+1).contents=="[") //Its a field
            {
                if(Field()){}else{return false;}
            }
            else  if(tList.get(index).Class=="var") //Has to be a variable
            {
                if(Var()){}else{return false;}
            }
            else
            {
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
            }
        }
        else if(index<tList.size() && tList.get(index).contents=="input" || tList.get(index).contents=="not")  //UnOp production
        {
            if(UnOp()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).contents=="and"||tList.get(index).contents=="or" || tList.get(index).contents=="eq" || tList.get(index).contents=="larger" || tList.get(index).contents=="add" || tList.get(index).contents=="sub" || tList.get(index).contents=="mult")
        {
            //BinOp production
            if(BinOp()){}else{return true;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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
            index++;
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
        if(index<tList.size() && tList.get(index).Class=="var")
        {
            xml+=tList.get(index).contents;
            index++;
            if(lBracket()){}else{return false;}
            index++;
            if(index<tList.size() && tList.get(index).Class=="var")  //The Var production
            {
                if(Var()){}else{return false;}
            }
            else  //The Const production
            {
                if(Const()){}else{return false;}
            }
            index++;
            if(rBracket()){}else{return false;}
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        
        xml+="</Field>";
        return true;
    }

    public boolean Const()
    {
        xml+="<Const>";
        if(index<tList.size() && tList.get(index).Class=="num")
        {
            xml+=tList.get(index).contents;
        }
        else if(index<tList.size() && tList.get(index).Class=="string")
        {
            xml+=tList.get(index).contents;
        }
        else if(index<tList.size() && tList.get(index).contents=="true")
        {
            xml+="true";
        }
        else if(index<tList.size() && tList.get(index).contents=="false")
        {
            xml+="false";
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        
        xml+="</Const>";
        return true;
    }

    public boolean UnOp()
    {
        xml+="<UnOp>";
        if(index<tList.size() && tList.get(index).contents=="input")
        {
            xml+="input";
            index++;
            if(lParenth()){}else{return false;}
            index++;
            if(Var()){}else{return false;}
            index++;
            if(rParenth()){}else{return false;}
        }
        else if(index<tList.size() && tList.get(index).contents=="not")
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

            if(tList.get(index).contents=="and")
            {
                xml+="and";
            }
            else if(tList.get(index).contents=="or")
            {
                xml+="or";
            }
            else if(tList.get(index).contents=="eq")
            {
                xml+="eq";
            }
            else if(tList.get(index).contents=="larger")
            {
                xml+="larger";
            }
            else if(tList.get(index).contents=="add")
            {
                xml+="add";
            } 
            else if(tList.get(index).contents=="sub")
            {
                xml+="sub";
            }
            else if(tList.get(index).contents=="mult")
            {
                xml+="and";
            }
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
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
        if(index<tList.size() && tList.get(index).contents=="num" || tList.get(index).contents=="bool" || tList.get(index).contents=="string")//choose first production
        {
            //The production Dec->TYP Var
            if(TYP()){}else{return false;}  //If Dec doesnt start with num/bool/string
            index++;
            if(Var()){}else{return false;}

        }
        else if(index<tList.size() && tList.get(index).contents=="arr")   //We choose the second  production
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
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        xml+="</Dec>";
        return true;
    }

    public boolean TYP()
    {
        xml+="<TYP>";
        if(index<tList.size() && tList.get(index).contents=="num")
        {
            xml+="num";
        }
        else if(index<tList.size() && tList.get(index).contents=="string")
        {
            xml+="string";
        }
        else if(index<tList.size() && tList.get(index).contents=="bool")
        {
            xml+="bool";
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
        xml+="</TYP>";
        return true;
    }



    //Adding Terminals
    public boolean lCurly()
    {
        if(index<tList.size() && tList.get(index).contents=="{")  //Left curly
        {
            xml+="{";
            return true;
        }
        else{
                System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
                return false;
        }
    }

    public boolean Rcurly()
    {
        if(index<tList.size() && tList.get(index).contents=="}")  //Left curly
        {
            xml+="}";
            return true;
        }
        else{
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean Comma()
    {
        if(index<tList.size() && tList.get(index).contents==",")  //Left curly
        {
            xml+=",";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean lParenth()
    {
        if(index<tList.size() && tList.get(index).contents=="(")  //Left curly
        {
            xml+="(";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean rParenth()
    {
        if(index<tList.size() && tList.get(index).contents==")")  //Left curly
        {
            xml+=")";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean lBracket()
    {
        if(index<tList.size() && tList.get(index).contents=="[")  //Left curly
        {
            xml+="[";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean rBracket()
    {
        if(index<tList.size() && tList.get(index).contents=="]")  //Left curly
        {
            xml+="]";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean semiColon()
    {
        if(index<tList.size() && tList.get(index).contents==";")  //Left curly
        {
            xml+=";";
            return true;
        }
        else
        {
            System.out.println("SYNTAX ERROR at "+ tList.get(index).ID);
            return false;
        }
    }

    public boolean Variable()
    {
        if(index<tList.size() && tList.get(index).Class=="var")  //A variable name
        {
            xml+=tList.get(index).contents;
            return true;
        }
        else
        {
            System.out.print("SYNTAX ERROR");
            return false;
        }
    }


    //Helper Functions
    public boolean isAlgorithm()
    {
        if(index<tList.size() && tList.get(index).contents=="output" || tList.get(index).contents=="if" || tList.get(index).contents=="do" || tList.get(index).contents=="while" || tList.get(index).contents=="call" || tList.get(index).Class=="var")
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void Declaration()
    {
        if(index<tList.size() && tList.get(index).contents=="string" || tList.get(index).contents=="num" || tList.get(index).contents=="bool" || tList.get(index).contents=="arr")
        {
            VarDecl();
        }
    }


}
