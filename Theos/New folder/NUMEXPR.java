import java.io.IOException;

public class NUMEXPR
{
    static String part = "(tok_userDefinedIdentifier)(tok_int)(tok_zero)(tok_add)(tok_sub)(tok_mult)";

    static void Numexpr() throws IOException {
        Parser.treePrinter("NUMEXPR");
        Parser.depthLevel++;
        if (Parser.isolate().equals("(tok_int)")||Parser.isolate().equals("(tok_zero)"))
            Parser.treePrinter("integerLiteral: "+Parser.isolateliteral());
        else if(Parser.isolate().equals("(tok_userDefinedIdentifier)"))
            VAR.Var();
        else if(CALC.part.contains(Parser.isolate()))
            CALC.Calc();
        Parser.depthLevel--;
    }
}
