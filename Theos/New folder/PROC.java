import java.io.IOException;

public class PROC
{
    static void Proc() throws IOException {
        Parser.depthLevel++;
        Parser.treePrinter("PROC" );

        if(Parser.isolate().equals("(tok_proc)"))
        {
            if(Parser.inc() && Parser.isolate().equals("(tok_userDefinedIdentifier)"))
            {
                Parser.depthLevel++;
                Parser.treePrinter("proc: " +Parser.isolateliteral() );
                Parser.scope++;
                Parser.obrace();
                if(Parser.inc())
                {
                    Parser.depthLevel--;
                    PROG.Prog();
                }
                else
                {
                    Parser.errorPrinter("Expect CODE after open brace");
                }
                Parser.cbrace();
            }
            else
                Parser.errorPrinter("Expected a user token userDefinedIdentifier");
        }
        else
            Parser.errorPrinter("Expected a user token proc");

        Parser.depthLevel--;
        Parser.scope--;
    }
}
