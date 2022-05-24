import java.io.IOException;

public class VAR
{
    static void Var() throws IOException {
        if(Parser.file.get(Parser.index).contains("(tok_userDefinedIdentifier)"))
        {
            Parser.treePrinter("Var");
            Parser.depthLevel++;
            Parser.treePrinter("variable: " + Parser.isolateliteral());
            Parser.depthLevel--;
        }
        else
            Parser.errorPrinter("Expected User Defined Identifier as VAR");
    }
}
