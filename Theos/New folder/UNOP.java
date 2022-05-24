import java.io.IOException;

public class UNOP
{
    static String part = "(tok_input)(tok_not)";

    static void Unop() throws IOException {
        Parser.treePrinter("UnOp");
        Parser.depthLevel++;
        Parser.treePrinter("unop: "+Parser.isolateliteral());
        Parser.oparen();
        if(Parser.inc())
            if(Parser.file.get(Parser.index).contains("(tok_userDefinedIdentifier)"))
                VAR.Var();
            else
                EXPR.Expr();
        else
            Parser.errorPrinter("Expected VAR or EXR in Unop");
        Parser.cparen();
        Parser.depthLevel--;
    }
}

