import java.io.IOException;

public class CALC
{
    static String part = "(tok_add)(tok_sub)(tok_mult)";

    static void Calc() throws IOException {
        Parser.treePrinter("CALC");
        Parser.depthLevel++;
        Parser.treePrinter("calculation: "+Parser.isolateliteral());
        Parser.oparen();
        if(Parser.inc())
            NUMEXPR.Numexpr();
        else
            Parser.errorPrinter("Expected NUMEXPR in CALC");
        Parser.comma();
        if(Parser.inc())
            NUMEXPR.Numexpr();
        else
            Parser.errorPrinter("Expected NUMEXPR in CALC");
        Parser.cparen();
        Parser.depthLevel--;
    }

}
