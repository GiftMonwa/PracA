import java.io.IOException;
import java.util.ArrayList;

public class Node
{
    int ID;
    int scope;
    int indent;
    String description;
    String variable_name;
    String procedure_name;
    String operation;
    String data_type;
    String position;
    String new_name;
    String data_value;


    ArrayList<Node> children = new ArrayList<>();

    public Node(int ID, int scope, int indent, String description, String variable_name,String procedure_name,String data_type,String position,String data_value, String operation) {
        this.ID = ID;
        this.scope = scope;
        this.indent = indent;
        this.description = description;
        this.variable_name = variable_name;
        this.procedure_name = procedure_name;
        this.data_type = data_type;
        this.position = position;
        this.data_value = data_value;
        this.operation = operation;
        this.new_name = "P";
    }
    public void AddChild(Node child)
    {
        children.add(child);
    }
    public ArrayList<Node> getChildren()
    {
        return children;
    }
    public Node getChildAtIndex(int i)
    {
        return children.get(i);
    }

    public void printed(String indentation ,boolean isLast, ArrayList<String> symbolTable,boolean ast) throws IOException {
        if(ast)
            TreeBuilder.as_tree.write(indentation);
        else
            Semantic_analyser.symentic.write(indentation);

            if(isLast)
            {
                if(ast)
                    TreeBuilder.as_tree.write("└─");
                else
                    Semantic_analyser.symentic.write("└─");

                indentation += " ";
            }
            else
            {
                if(ast)
                    TreeBuilder.as_tree.write("├─");
                else
                    Semantic_analyser.symentic.write("├─");
                indentation += "│ ";
            }
            if(ast)
                TreeBuilder.as_tree.write(symbolTable.get(this.ID)+'\n');
            else
                Semantic_analyser.symentic.write(symbolTable.get(this.ID) + "        scope level: "+scope
                        +((variable_name.equals("N/A"))?"":"       variable name: "+((new_name.equals("P"))?variable_name:new_name))
                        +((procedure_name.equals("N/A"))?"":"       proc name: "+((new_name.equals("P"))?procedure_name:new_name))
                        +((operation.equals("N/A"))?"": "        operation: "+operation)
                        +((data_value.equals("N/A"))?"": "       data: "+data_value)
                        +((data_type.equals("N/A"))?"": "       type: "+data_type)+"\n");

            for(int  i = 0; i < children.size();i++)
            {
                children.get(i).printed(indentation, i == children.size() - 1, symbolTable,ast);
            }
        }

}

