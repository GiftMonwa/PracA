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
            if(tList.get(index).contents=="proc")  //checking if Procdef is null
            {
                ProcDefs();
            }
            else if(tList.get(index).contents=="main")
            {
                xml+="main";
                index++; //The next token that follows main
                lCurly();   //left Curly bracket
                index++;
                isAlgorithm();  //Parse Algorithm
                index++;
                if(tList.get(index).contents=="halt")
                {
                    xml+="halt";
                }
                else{
                    System.out.println("SYNTAX ERROR");
                    return;
                }

                index++;

                semiColon();
                index++;
                VarDecl();
                index++;
                Rcurly();
            }
            else
            {
                System.out.print("SYNTAX ERROR");
                return;
            }
        }
        xml+="</SPLProgr";
    }

    public void ProcDefs()
    {
        xml+="<ProcDefs>";
        PD();
        
        index++;
        Comma();  //check if the next input is  a comma

        index++;
        if(tList.get(index).contents=="proc")
        {
            ProcDefs();
        } //If its null we continue

        xml+="</ProcDefs>";
    }

    public void Algorithm()
    {
        xml+="<Algorith>";
        
        xml+="</Algorithm>";
    }

    public void VarDecl()
    {
        xml+="<VarDecl>";
        
        xml+="</VarDecl>";
    }

    public void PD()
    {
        xml+="<PD>";
        if(tList.get(index).contents=="proc")
        {
            xml+="proc";   
        }
        else
        {
            System.out.println("SYNTAX ERROR");
        }
        
        index++;
        Variable();  //Adding a userDefined variable
        
        index++;
        lCurly();


        index++;
        if(tList.get(index).contents=="proc")  //Check if procdefs is null
        {
            ProcDefs();   
        }

        index++;
        isAlgorithm();  //Checks if we have an Algorithm before it passes
        index++;

        if(tList.get(index).contents=="return")
        {
            xml+="return";
        }
        else{
            System.out.println("SYNTAX ERROR");
        }

        index++;
        semiColon();
        index++;

        
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

    public void Expr()
    {
        xml+="<Expr>";
        
        xml+="</Expr>";
    }

    public void Alternat()
    {
        xml+="<Alternat>";
        
        xml+="</Alternat>";
    }

    public void Var()
    {
        xml+="<Var>";
        
        xml+="</Var>";
    }

    public void Field()
    {
        xml+="<Field>";
        
        xml+="</Field>";
    }

    public void Const()
    {
        xml+="<Const>";
        
        xml+="</Const>";
    }

    public void UnOp()
    {
        xml+="<UnOp>";
        
        xml+="</UnOp>";
    }

    public void BinOp()
    {
        xml+="<BinOp>";
        
        xml+="</BinOp>";
    }

    public void Dec()
    {
        xml+="<Dec>";
        
        xml+="</Dec>";
    }

    public void TYP()
    {
        xml+="<TYP>";
        
        xml+="</TYP>";
    }



    //Adding Terminals
    public void lCurly()
    {
        if(tList.get(index).contents=="{")  //Left curly
        {
            xml+="{";
        }
        else{
                System.out.println("SYNTAX ERROR");
                return;
        }
    }

    public void Rcurly()
    {
        if(tList.get(index).contents=="}")  //Left curly
        {
            xml+="}";
        }
        else{
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void Comma()
    {
        if(tList.get(index).contents==",")  //Left curly
        {
            xml+=",";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void lParenth()
    {
        if(tList.get(index).contents=="(")  //Left curly
        {
            xml+="(";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void rParenth()
    {
        if(tList.get(index).contents==")")  //Left curly
        {
            xml+=")";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void lBracket()
    {
        if(tList.get(index).contents=="[")  //Left curly
        {
            xml+="[";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void rBracket()
    {
        if(tList.get(index).contents=="]")  //Left curly
        {
            xml+="]";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void semiColon()
    {
        if(tList.get(index).contents==";")  //Left curly
        {
            xml+=";";
        }
        else
        {
            System.out.println("SYNTAX ERROR");
            return;
        }
    }

    public void Variable()
    {
        if(tList.get(index).Class=="var")  //A variable name
        {
            xml+=tList.get(index).contents;
            index++;
        }
        else
        {
            System.out.print("SYNTAX ERROR");
            return;
        }
    }


    //Helper Functions
    public void isAlgorithm()
    {
        if(tList.get(index).contents=="output" || tList.get(index).contents=="if" || tList.get(index).contents=="do" || tList.get(index).contents=="while" || tList.get(index).contents=="call" || tList.get(index).Class=="var")
        {
            Algorithm();;
        }
    }

    public void isDeclaration()
    {
        if(tList.get(index).contents=="string" || tList.get(index).contents=="num" || tList.get(index).contents=="bool" || tList.get(index).contents=="arr")
        {
            VarDecl();
        }
    }


}
