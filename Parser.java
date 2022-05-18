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

    public void Algorithm()
    {
        xml+="<Algorith>";
        
        xml+="</Algorithm>";
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
            ProcDefs();   
        }

        index++;
        isAlgorithm();  //Checks if we have an Algorithm before it passes
        index++;

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
    }

    public void Instr()
    {
        xml+="<Instr>";
        
        xml+="</Instr>";
    }

    public void Assign()
    {
        xml+="<Assign>";
        
        xml+="</Assign>";
    }

    public void Branch()
    {
        xml+="<Branch>";
        
        xml+="</Branch>";
    }

    public void Loop()
    {
        xml+="<Loop>";
        
        xml+="</Loop>";
    }

    public void PCall()
    {
        xml+="<PCall>";
        
        xml+="</PCall>";
    }

    public void LHS()
    {
        xml+="<LHS>";
        
        xml+="</LHS>";
    }

    public boolean Expr()
    {
        xml+="<Expr>";
        if(index<tList.size() && tList.get(index).Class=="string" || tList.get(index).Class=="num" || tList.get(index).contents=="true" || tList.get(index).Class=="false")   //Const production
        {
           if(Const()){}else{return false;}
           index++;
        }
        else if(index<tList.size() && tList.get(index).Class=="var")  //further check if its a field or Var
        {
            if(index+1<tList.size() && tList.get(index+1).contents=="[") //Its a field
            {
                if(Field()){}else{return false;}
            }
            else   //Has to be a variable
            {
                if(Var()){}else{return false;}
            }
            index++;
        }
        else if(index<tList.size() && tList.get(index).contents=="input" || tList.get(index).contents=="not")  //UnOp production
        {
            if(UnOp()){}else{return false;}
        }
        xml+="</Expr>";
        return true;
    }

    public void Alternat()
    {
        xml+="<Alternat>";
        
        xml+="</Alternat>";
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

    public void BinOp()
    {
        xml+="<BinOp>";
        
        xml+="</BinOp>";
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
    public void isAlgorithm()
    {
        if(index<tList.size() && tList.get(index).contents=="output" || tList.get(index).contents=="if" || tList.get(index).contents=="do" || tList.get(index).contents=="while" || tList.get(index).contents=="call" || tList.get(index).Class=="var")
        {
            Algorithm();;
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
