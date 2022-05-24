import java.io.IOException;

public class ASSIGN
{
    static void Assign() throws IOException {
        Parser.treePrinter("ASSIGN");
        Parser.depthLevel++;
        LHS.Lhs();
        Parser.colon();
        if (Parser.file.size()>++Parser.index&&Parser.isolate().equals("(tok_assign)"))
            if(Parser.file.size()>++Parser.index)
                EXPR.Expr();
            else
                Parser.errorPrinter("Expected EXPR");   
        else
            Parser.errorPrinter("Expected ASSIGN operator");
        Parser.depthLevel--;
    }
}
