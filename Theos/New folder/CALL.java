import java.io.IOException;

public class CALL
{
    static void Call() throws IOException {
        Parser.treePrinter("PCALL");
        Parser.depthLevel++;
        if(!(Parser.inc()) || !(Parser.isolate().equals("(tok_userDefinedIdentifier)")))
             Parser.errorPrinter("Expected userDefinedIdentifier after call");
        Parser.treePrinter("proc: " + Parser.isolateliteral());
        Parser.depthLevel--;
    }
}
