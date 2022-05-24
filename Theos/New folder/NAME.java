import java.io.IOException;

public class NAME
{
    static void Name() throws IOException {
        if(Parser.file.get(Parser.index).contains("(tok_userDefinedIdentifier"))
        {
            Parser.treePrinter("NAME");
            Parser.depthLevel++;
            Parser.treePrinter("variable: " + Parser.isolateliteral());
            Parser.depthLevel--;
        }
        else
            Parser.errorPrinter("Expected User Defined Identifier as NAME");
    }
}
