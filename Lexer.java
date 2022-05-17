import java.io.*;
import java.util.*;
import java.util.regex.*;


public class Lexer{
    public ArrayList<token> tokenList=new ArrayList<token>();
    public ArrayList<String> keywords=new ArrayList<String>();
    public ArrayList<Character> operators=new ArrayList<Character>();

    public void readInput(String filename) throws Exception
    {
        initializeKW();   //Initializing the operators and keywords
        File file=new File(filename);
        Scanner scan=new Scanner(file);

        int row=0;
        String line="";
        while(scan.hasNextLine())   //scanning txt line by line
        {
            int col=0;
            String data="";
            token tk;
            //int num;
            line=scan.nextLine();
            int i=0;
            String id;
            
            
            while(i<line.length())  //while not end of line
            {
                data="";
               // System.out.println(line.length());
                if(Character.isLetter(line.charAt(i)))  //Its a letter
                {
                    //System.out.println(operators.contains(':'));
                    while(line.charAt(i)!=' '  && !operators.contains(line.charAt(i)))   //add the name into the variable until u hit a separator/the assign operator
                    {
                        data+=line.charAt(i++);
                        if(!(i<line.length()))
                        {
                            break;//end of line
                        }
                    }
                    boolean userDefined=Pattern.matches("[a-z]([a-z]|[0-9])*",data);  //test if the data is a userDef

                    id=row+","+col;
                    if(keywords.contains(data))   //if the data word is a keyword
                    {       
                        tk=new token(id,"KeyW",data);
                        tokenList.add(tk);
                    }
                    else if(userDefined)   //Its a userDefined name
                    {
                        tk=new token(id,"var",data);
                        tokenList.add(tk);
                    }
                }
                else if(line.charAt(i)=='"') //If we find the start of the string
                {
                    //System.out.println("I got to a string at "+ i );
                    id=row+","+col;
                    i++;  //skip the " character
                    while(i<line.length() && line.charAt(i)!='"')  //while not end of line aand not end of string
                    {
                        data+=line.charAt(i++);
                    }

                    if(i<line.length() && line.charAt(i)=='"')    //The loop terminated because end of string not end of line
                    {
                        boolean stringLiteral=Pattern.matches("[A-Z0-9\s]*{0,15}",data);
                        if(stringLiteral)   //string acceptable
                        {
                            String data2='"'+data+'"';
                            tk=new token(id,"string",data2);
                            tokenList.add(tk);
                        }
                        else
                        {
                            System.out.println("TOKEN ERROR");
                            scan.close();
                            return;
                        }
                    }
                }
                else if(Character.isDigit(line.charAt(i)) || line.charAt(i)=='-')  //char is an interger
                {
                    while(i<line.length() && !operators.contains(line.charAt(i)))  //while not at the end of line or a separator
                    {
                        data+=line.charAt(i++);
                    }

                    if(data.length()==1)  //single digit int won't be accepted by the regex pattern add space
                    {
                        data+=" ";
                    }
                    boolean number=Pattern.matches("0|(([1-9]|(-.[1-9])).[0-9]*)",data);

                    if(number)  //the number comforms to our format
                    {
                        id=row+","+col;
                        data.trim();  //Removes whitespace
                        tk=new token(id, "num", data);
                        tokenList.add(tk);
                    }
                    else
                    {
                        System.out.println("TOKEN ERROR at: "+row+" ,col:"+i);
                        scan.close();
                        return;
                    }
                }
                else if(line.charAt(i)==':')   
                {
                   // System.out.println("I hit the operator");
                    id=row+","+col;
                    if(i+1<line.length() && line.charAt(i+1)=='=')  //The sign :=
                    {
                        data=":=";
                        tk=new token(id, "operator", data);
                        tokenList.add(tk);
                        i+=2;
                    }
                    else
                    {
                        System.out.println("TOKEN ERROR at: "+row+" ,col:"+i);
                        scan.close();
                        return;
                    }
                }
                else if(line.charAt(i)==';')   //end of instruction semicolon
                {
                    id=row+","+col;
                    data=Character.toString(';');
                    tk=new token(id, "operator", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)=='(')
                {
                    id=row+","+col;
                    data=Character.toString('(');
                    tk=new token(id, "lPar", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)==',')
                {
                    id=row+","+col;
                    data=Character.toString(',');
                    tk=new token(id, "comma", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)==')')
                {
                    id=row+","+col;
                    data=Character.toString(')');
                    tk=new token(id, "rPar", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)=='[')
                {
                    id=row+","+col;
                    data=Character.toString('[');
                    tk=new token(id, "lBra", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)==']')
                {
                    id=row+","+col;
                    data=Character.toString(']');
                    tk=new token(id, "rBra", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)=='{')
                {
                    id=row+","+col;
                    data=Character.toString('{');
                    tk=new token(id, "lCurly", data);
                    tokenList.add(tk);
                    i++;
                }
                else if(line.charAt(i)=='}')
                {
                    id=row+","+col;
                    data=Character.toString('}');
                    tk=new token(id, "rCurly", data);
                    tokenList.add(tk);
                    i++;
                }
                else
                {
                    System.out.println("TOKEN ERROR");
                    scan.close();
                    return;
                }

                //i++;
            }
            row++;
            
        }
        scan.close();
    }

    public void initializeKW()
    {
        keywords.add("main");
        keywords.add("halt");
        keywords.add("proc");
        keywords.add("return");
        keywords.add("if");
        keywords.add("then");
        keywords.add("else");
        keywords.add("do");
        keywords.add("while");
        keywords.add("until");
        keywords.add("output");
        keywords.add("call");
        keywords.add("input");
        keywords.add("true");
        keywords.add("false");
        keywords.add("add");
        keywords.add("mult");
        keywords.add("not");
        keywords.add("and");
        keywords.add("or");
        keywords.add("eq");
        keywords.add("larger");
        keywords.add("sub");
        keywords.add("arr");
        keywords.add("num");
        keywords.add("bool");
        keywords.add("string");

        //Operators
        operators.add(';');
        operators.add('"');
        operators.add('(');
        operators.add(')');
        operators.add('[');
        operators.add(']');
        operators.add('{');
        operators.add('}');
        operators.add(',');
        operators.add(':');
    }

    public void Tokenizer()
    {
        for(token i : tokenList)
        {
            System.out.println(i.Class+" "+i.contents);
        }
    }
}
