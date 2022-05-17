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
            if(tList.get(0).contents=="proc")
            {
                ProcDefs();
            }
            else if(tList.get(0).contents=="main")
            {
                
            }
        }
        xml+="</SPLProgr";
    }

    public void ProcDefs()
    {
        xml+="<ProcDefs>";

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
}
