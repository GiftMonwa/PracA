import java.io.IOException;

public class COND_BRANCH
{
    static String part = "(tok_if)(tok_else)";

    static void Cond_Branch() throws IOException {
        Parser.treePrinter("Branch");
        Parser.depthLevel++;
        Parser.treePrinter("conditional branch: if");
        Parser.oparen();
        if (Parser.inc())
            EXPR.Expr();
        else
            Parser.errorPrinter("No expr statement provided");
        Parser.cparen();
        if (!(Parser.inc()) ||  !Parser.isolate().equals("(tok_then)"))
            Parser.errorPrinter("2. Expected then after condition");
        Parser.obrace();
    
        if (Parser.inc())
            Algorithm.algorithm();
        else
            Parser.errorPrinter("If statement without a body");
        Parser.cbrace();
        if ((Parser.inc()) && Parser.isolate().equals("(tok_else)"))
            ALTERNAT.Alternat();
        else
            Parser.index--;


        Parser.depthLevel--;
    }

}
