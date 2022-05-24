import javax.lang.model.util.ElementScanner6;
import java.io.IOException;

public class COND_LOOP
{
    static String part = "(tok_while)(tok_do)";

    static void Cond_loop() throws IOException {
        Parser.treePrinter("Loop");
        Parser.depthLevel++;
        Parser.treePrinter("Conditional loop " + Parser.isolateliteral());
        if(Parser.file.get(Parser.index).contains("(tok_do)"))
        {
            Parser.obrace();
            if (Parser.file.size()>++Parser.index)
                Algorithm.algorithm();
            else
                Parser.errorPrinter("Empty for condition statement");    
            Parser.cbrace();
            if (!(Parser.file.size()>++Parser.index) || !Parser.isolate().equals("(tok_until)"))
                Parser.errorPrinter("Expected until after closing par");
            
            Parser.oparen();  
            if (Parser.file.size()>++Parser.index)
                EXPR.Expr();
            else 
                Parser.errorPrinter("Empty for condition statement");
            Parser.cparen();
        }
        else if(Parser.file.get(Parser.index).contains("(tok_while)"))
        {
            Parser.oparen();
            if (Parser.inc())
                EXPR.Expr();
            else
                Parser.errorPrinter("Empty condition statement");
            Parser.cparen();
            if(!(Parser.file.size()>++Parser.index) || !Parser.isolate().equals("(tok_do)"))
                 Parser.errorPrinter("Expected do");
            Parser.obrace();
            if (Parser.inc())
                Algorithm.algorithm();
            else
                Parser.errorPrinter("Empty algorithm block");
            Parser.cbrace();
        }
        Parser.depthLevel--;
    }


}
