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
    }

    public void Algorithm()
    {

    }

    public void VarDecl()
    {

    }

    public void PD()
    {

    }

    public void Instr()
    {

    }

    public void Assign()
    {

    }

    public void Branch()
    {

    }

    public void Loop()
    {

    }

    public void PCall()
    {

    }

    public void LHS()
    {

    }

    public void Expr()
    {

    }

    public void Alternat()
    {

    }

    public void Var()
    {

    }

    public void Field()
    {

    }

    public void Const()
    {

    }

    public void UnOp()
    {

    }

    public void BinOp()
    {

    }

    public void Dec()
    {

    }

    public void TYP()
    {

    }
}
