import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;



public class TreeBuilder
{
    static FileWriter symbol_table,as_tree;
    static File read = new File("cs_tree.txt");
    static File read2 = new File("scope_file.txt");
    static File read3 = new File("id_ast.txt");
    static File read4 = new File("symbol_table.txt");
    static ArrayList<Node> ast;
    static ArrayList<String> sym_table = new ArrayList<>();
    static Stack<String> type_s;
    static Stack<String> name_s;
    static String line = "";
    static String variable ="";


    public static void buildTree() throws IOException {

        symbol_table = new FileWriter("symbol_table.txt");
        as_tree = new FileWriter("as_tree.txt");
        Scanner sc = new Scanner(read);
        Scanner sc2 = new Scanner(read2);
        ast = new ArrayList<>();

        while (sc.hasNextLine() && sc2.hasNextLine())
        {

            int tab = 0;
            line = sc.nextLine();
            while (line.charAt(tab) == ' ')
                tab++;

            String number = line.substring(tab + 1, line.indexOf(":"));
            line = line.substring(line.indexOf(":") + 1);


            String variable_name = line.replace("variable: ","");
            String operation = line.replace("calculation: ","");
            String procedure_name = line.replace("proc: ","");
            String type = line.replace("type: ","");
            String data_value="";
            if(line.contains("stringLiteral"))
            {
                data_value = line.replace("stringLiteral: ","");
            }
            else if (line.contains("integerLiteral"))
                data_value = line.replace("integerLiteral: ","");
            else if (line.contains("bool"))
                data_value = line.replace("bool: ","");



            if(line.contains(":"))
                line = line.substring(0, line.indexOf(":"));

            String scope = sc2.nextLine();
            int id = Integer.parseInt(number);
            int scop = Integer.parseInt(scope.substring(scope.indexOf(":") + 1));
            switch (line) {
                case "proc":
                    ast.add(new Node(id, scop, tab, line, "N/A", procedure_name, "P", "N/A", "N/A","N/A"));
                    break;
                case "variable":
                    ast.add(new Node(id, scop, tab, line, variable_name, "N/A", "N/A", "N/A", "N/A","N/A"));
                    break;
                case "type":
                    ast.add(new Node(id, scop, tab, line, "N/A", "N/A", type, "N/A", "N/A","N/A"));
                    break;

                case "calculation":
                    ast.add(new Node(id, scop, tab, line, "N/A", "N/A", type, "N/A", "N/A",operation));
                    break;
                case "integerLiteral":
                case "stringLiteral":
                case "bool":
                    ast.add(new Node(id, scop, tab, line, "N/A", "N/A", type, "N/A", data_value,"N/A"));
                    break;
                default:
                    ast.add(new Node(id, scop, tab, line, "N/A", "N/A", "N/A", "N/A", "N/A","N/A"));
                    break;
            }
        }

        Scanner sx = new Scanner(read3);
        String children = "";
        int index = 0;

        while (sx.hasNextLine())
        {
            children = sx.nextLine();
            index = Integer.parseInt(children.substring(0,children.indexOf(":")));
            children = children.substring(children.indexOf(":") + 1);
            String child = "";

            Vector<Integer> children_list = new Vector<>();

            for (int i = 0; i < children.length(); i++)
            {
                if (children.charAt(i) != ',')
                    child += children.charAt(i);

                else
                    {
                        children_list.add(Integer.parseInt(child));
                        child = "";
                    }

                if (i + 1 == children.length())
                {
                    children_list.add(Integer.parseInt(child));

                    for (int s : children_list)
                        ast.get(index).AddChild(ast.get(s));
                }
            }
        }
    }

    public static ArrayList<Node> getAST()
    {
        return ast;
    }

    public static void printAST() throws IOException {

        as_tree = new FileWriter("as_tree.txt");
        Scanner sc = new Scanner(read);
        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            line = line.substring(line.indexOf("↳") + 1);
            symbol_table.write(line+'\n');
            sym_table.add(line);
        }
        ast.get(0).printed("",true,sym_table,true);
        symbol_table.close();
        as_tree.close();
    }

    static void print(String s)
    {
        System.out.println(s);
    }
    static ArrayList<Integer> check(Node node)
    {
        ArrayList<Integer> array = new ArrayList<>();
        for(int i = 0; i < node.ID; i++)
        {
            for (int j = 0 ; j < ast.get(i).children.size(); j++)
            {
                if(ast.get(i).children.get(j).ID!=node.ID && ast.get(i).children.get(j).indent<=node.indent)
                {
                    array.add(ast.get(i).indent);
                }
            }
        }
        return array;
    }


}
