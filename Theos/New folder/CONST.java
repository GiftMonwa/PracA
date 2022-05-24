import java.io.IOException;

public class CONST
{
    static String part = "(tok_int)(tok_str)(tok_true)(tok_false)(tok_zero)";
    static void Const() throws IOException {
        Parser.treePrinter("Const");
        Parser.depthLevel++;
        if (Parser.isolate().equals("(tok_int)")||Parser.isolate().equals("(tok_zero)"))
            Parser.treePrinter("integerLiteral: "+Parser.isolateliteral());
        else if(Parser.isolate().equals("(tok_str)"))
             Parser.treePrinter("shortString : "+Parser.isolateliteral());
        else if(Parser.isolate().equals("(tok_true)"))
                Parser.treePrinter("Const: true");
        else if(Parser.isolate().equals("(tok_false)"))
                Parser.treePrinter("Const: false");
        Parser.depthLevel--;
    }
}