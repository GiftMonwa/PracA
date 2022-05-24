import java.io.IOException;

public class FIELD
{
    static void Field() throws IOException {
        Parser.index--;
        Parser.treePrinter("Field");
        Parser.depthLevel++;
        Parser.obracket();
        if(Parser.file.size()>++Parser.index)
            if(Parser.file.get(Parser.index).contains("(tok_userDefinedIdentifier"))
                VAR.Var();
            else
                CONST.Const();
        else 
            Parser.errorPrinter("Expected VAR or CONST after opening bracket");
        Parser.cbracket();        
        Parser.depthLevel--;
    }
}