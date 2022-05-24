import java.io.IOException;

public class BINOP
{
    static String part = "(tok_eq)" + "(tok_larger)" +
            "(tok_or)(tok_and)(tok_add)(tok_sub)(tok_mult)";

    static void BinOp() throws IOException {
        Parser.treePrinter("BinOp");
        Parser.depthLevel++;
        Parser.treePrinter("binop: " + Parser.isolateliteral());
        Parser.oparen();
        if (Parser.inc())
            EXPR.Expr();
        else
            Parser.errorPrinter("Empty condition statement");
        Parser.comma();
        if (Parser.inc())
            EXPR.Expr();
        else
            Parser.errorPrinter("Empty condition statement");
        Parser.cparen();
        Parser.depthLevel--;
    }
}
