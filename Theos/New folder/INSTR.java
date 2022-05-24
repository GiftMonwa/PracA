import java.io.IOException;

public class INSTR
{
    static String part ="(tok_output)(tok_userDefinedIdentifier)" +
            "(tok_call)(tok_if)"+"(tok_while)(tok_do)" ;

    static void Instr() throws IOException {
        if(part.contains(Parser.isolate()))
        {

            Parser.treePrinter("Instr");
            Parser.depthLevel++;
            if(Parser.isolate().equals("(tok_output)") || Parser.isolate().equals("(tok_userDefinedIdentifier)"))
                ASSIGN.Assign();
            else if(Parser.isolate().equals("(tok_call)"))
                    CALL.Call();
            else if(COND_BRANCH.part.contains(Parser.isolate()))
                COND_BRANCH.Cond_Branch();
            else if(COND_LOOP.part.contains(Parser.isolate()))
                COND_LOOP.Cond_loop();
            Parser.depthLevel--;
        }

    }
}

