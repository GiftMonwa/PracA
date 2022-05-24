import java.io.IOException;

public class PD
{
    static void Pd() throws IOException {

        Parser.treePrinter("PD");
        Parser.depthLevel++;
        if(Parser.isolate().equals("(tok_proc)"))
        {
            if(Parser.inc() && Parser.isolate().equals("(tok_userDefinedIdentifier)"))
            {
                Parser.treePrinter("proc: " +Parser.isolateliteral());
                Parser.scope++;
                Parser.obrace();
                if(Parser.inc())
                {
                    PROC_DEFS.Proc_Defs();
                    if(Parser.inc())
                        Algorithm.algorithm();
                    else 
                        Parser.errorPrinter("Expect algorithm after proc defs");
                }
                else
                {
                    Parser.errorPrinter("Expect CODE after open brace");
                }
                if(!(Parser.file.size()>++Parser.index) || !Parser.isolate().equals("(tok_return)"))
                    Parser.errorPrinter("Expected return after algorithm");
                Parser.semiColon();
                if(Parser.inc())
                    VARDECL.VarDecl();
                else
                    Parser.errorPrinter("Expected VarDecl after semi-colon");
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
