import java.io.IOException;

public class LHS
{
    static String part = "(tok_output)(tok_userDefinedIdentifier)";
    static void Lhs() throws IOException {
        Parser.treePrinter("LHS");
        Parser.depthLevel++;
         if(Parser.isolate().equals("(tok_output)"))
                Parser.treePrinter("LHS: output");
        else if(Parser.isolate().equals("(tok_userDefinedIdentifier)"))
                if (Parser.file.size()>++Parser.index && Parser.isolate().equals("(tok_obracket)"))
                    FIELD.Field();
                else
                {
                    Parser.index--;
                    VAR.Var();
                }
       Parser.depthLevel--;
    }
}