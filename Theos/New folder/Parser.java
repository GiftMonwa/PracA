import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;


public class Parser
{
    static Vector<String> file = new Vector<>();
    static Vector<String> file_2 = new Vector<>();
    static FileWriter cs_tree , id_ast, scope_file;
    static int index = 0;
    static int depthLevel = -1;
    static int count = -1;
    static int scope = 0;
    public static void main(String [] args) throws Exception
    {
        File read = new File("output.txt");
        File read2 = new File("lineout.txt");

        Scanner sc = new Scanner(read);
        Scanner sc2 = new Scanner(read2);

        cs_tree = new FileWriter("cs_tree.txt");
        id_ast = new FileWriter("id_ast.txt");
        scope_file = new FileWriter("scope_file.txt");
        while (sc.hasNextLine())
        {
            file.add(sc.nextLine());
            file_2.add(sc2.nextLine());
        }

        //System.out.println(file);

        if (file.size()>index)
            SPLPROGRAM.Start();
        cs_tree.close();
        WriteID_AST();
        id_ast.close();
        scope_file.close();
        TreeBuilder.buildTree();
        TreeBuilder.printAST();
    }

    public static void WriteID_AST() throws IOException {

        File read = new File("cs_tree.txt");

        Scanner sc = new Scanner(read);
        Vector<String> file = new Vector<>();


        String append = "";
        while (sc.hasNextLine())
        {
            file.add(sc.nextLine());
        }

        for(int i= 0 ; i < file.size();i++)
        {
            append =(i+":");
            String parent = file.get(i);
            int Parent_tab = 0;
            while (parent.charAt(Parent_tab) == ' ')
                Parent_tab++;
            for (int j =  i + 1; j < file.size();j++)
            {
                String child = file.get(j);
                int Child_tab = 0;
                while (child.charAt(Child_tab) == ' ')
                    Child_tab++;

                if(Child_tab <= Parent_tab)
                    j = file.size();
                if(Parent_tab == (Child_tab - 2))
                    append += (j + ",");

            }
            if(append.indexOf(":") != append.length()-1)
                id_ast.write(append.substring(0,append.length()-1)+"\n");
        }

    }



    static void treePrinter(String type) throws IOException {
        cs_tree.write("  ".repeat(depthLevel) + "↳"+ ++count+":"+type+"\n");
        scope_file.write(count+":"+scope+"\n");
    }
    static void errorPrinter(String reason)
    {
        System.out.println( file_2.get(index) + ": " + reason);
        System.exit(0);
    }

    static void check() {
        System.out.println(Parser.isolate());
    }
    static void check2()
    {
        System.out.println(Parser.isolateliteral());
    }
    static void colon()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_colon)")))
             errorPrinter("Expected Colon"); 
    }
    static void obrace()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_obrace)")))
            errorPrinter("Expected Opening brace");
    }
    static void cbrace()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_cbrace)")))
            errorPrinter("Expected Closing brace");
    }
    static void oparen()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_oparen)")))
            errorPrinter("Expected Opening parentheses");
    }
    static void cparen()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_cparen)")))
            errorPrinter("Expected Closing parentheses");
    }
    static void obracket()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_obracket)")))
            errorPrinter("Expected Opening bracket");
 
    }
    static void cbracket()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_cbracket)")))
            errorPrinter("Expected Closing bracket");

    }

    static void comma()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_comma)")))
            errorPrinter("Expected comma");
    }
    static void semiColon()
    {
        if(!(file.size() > ++index && isolate().equals("(tok_semi-colon)")))
            errorPrinter("Expected semi-colon");
    }
    static boolean inc()
    {
        return (file.size() > ++index);
    }
    static String isolate()
    {
        return file.get(index).substring(file.get(index).indexOf("(tok_"));
    }
    static String isolateliteral()
    {
        return file.get(index).substring(file.get(index).indexOf(":")+1,file.get(index).indexOf(" ("));
    }
}