import java.io.IOException;

public class BOOL
{
    static String part = "(tok_eq)" + "(tok_larger)" +
            "(tok_or)(tok_and)(tok_add)(tok_sub)(tok_mult)";

    static void Bool() throws IOException {

        Parser.treePrinter("BinOp");
        Parser.depthLevel++;
        if(Parser.isolate().equals("(tok_eq)"))
        {
            Parser.treePrinter("bool: eq");
            Parser.oparen();
            if (Parser.inc())
                VAR.Var();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.comma();
            if (Parser.inc())
                VAR.Var();
            else
                Parser.errorPrinter("Empty condition statement");
                Parser.cparen();
        }
        else if(Parser.isolate().equals("(tok_oparen)"))
        {
            Parser.treePrinter("bool: compare");
            if (Parser.inc()) {
                VAR.Var();
            }else
                Parser.errorPrinter("Empty condition statement");
            if(Parser.inc() && Parser.isolate().equals("(tok_lthan)")||Parser.isolate().equals("(tok_gthan)"))
                Parser.treePrinter("operator: "+Parser.isolateliteral());
            else Parser.errorPrinter("Expected comparison symbol");
            if (Parser.inc())
                VAR.Var();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.cparen();
        }
        else if(Parser.isolate().equals("(tok_not)"))
        {
            Parser.treePrinter("bool: not");
            if (Parser.inc())
                BOOL.Bool();
            else
                Parser.errorPrinter("Empty condition statement");
        }
        else if(Parser.isolate().equals("(tok_and)"))
        {
            Parser.treePrinter("bool: and");
            Parser.oparen();
            if (Parser.inc())
                BOOL.Bool();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.comma();
            if (Parser.inc())
                BOOL.Bool();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.cparen();
        }
        else if(Parser.isolate().equals("(tok_or)"))
        {
            Parser.treePrinter("bool: or");
            Parser.oparen();
            if (Parser.inc())
                BOOL.Bool();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.comma();
            if(Parser.inc())
                BOOL.Bool();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.cparen();
        }
        else if(Parser.isolate().equals("(tok_T)"))
            Parser.treePrinter("bool: T");
        else if(Parser.isolate().equals("(tok_F)"))
            Parser.treePrinter("bool: F");
        else
            VAR.Var();
        Parser.depthLevel--;
    }
}
