import java.io.IOException;

public class SPLPROGRAM{

    static void Start() throws IOException {
        Parser.depthLevel++;
        if(Parser.isolate().equals("(tok_proc)"))
        {
            Parser.treePrinter("SPL");
            Parser.depthLevel++;
            PROC_DEFS.Proc_Defs();
            if(!(Parser.inc()) || !(Parser.isolate().equals("(tok_main)")))
               Parser.errorPrinter("Expected main after the proc defs");
            Parser.obrace();
            if(Parser.inc())
                Algorithm.algorithm(); 
            else 
                Parser.errorPrinter("Expected algorithm after open brace");
            if(!(Parser.inc()) || !(Parser.isolate().equals("(tok_halt)")))
                Parser.errorPrinter("Expected halt after the algorithm");
            Parser.semiColon();
            if(Parser.inc())
                VARDECL.VarDecl();
            else 
                Parser.errorPrinter("Expected vardecl after the semi-colon");
            Parser.cbrace();
            Parser.depthLevel--;
        }
        else if(Parser.isolate().equals("(tok_main)"))
        {
            Parser.treePrinter("SPL");
            Parser.depthLevel++;
            Parser.obrace();
            if(Parser.inc())
                Algorithm.algorithm(); 
            else 
                Parser.errorPrinter("Expected algorithm after open brace");
            if(!(Parser.file.size()>++Parser.index) || !Parser.isolate().equals("(tok_halt)"))
                 Parser.errorPrinter("Expected halt after the algorithm");
            Parser.semiColon();
            if(Parser.inc())
                VARDECL.VarDecl();
            else 
                Parser.errorPrinter("Expected vardecl after the semi-colon");
            Parser.cbrace();
            Parser.depthLevel--;
////////////////
        }

    }









}