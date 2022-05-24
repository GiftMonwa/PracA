import java.io.IOException;

public class IO
{
    static String part = "(tok_input)(tok_not)";

    static void Unop() throws IOException {
        Parser.treePrinter("Unop");
        Parser.depthLevel++;
        Parser.treePrinter("io: "+Parser.isolateliteral());



        Parser.oparen();
        if(Parser.inc())
            VAR.Var();
        else
            Parser.errorPrinter("Expected VAR in IO");
        Parser.cparen();
        Parser.depthLevel--;
    }
}

