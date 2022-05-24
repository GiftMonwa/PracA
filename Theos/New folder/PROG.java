import java.io.IOException;

public class PROG
{
    static void Prog() throws IOException {
        Parser.depthLevel++;
        if(INSTR.part.contains(Parser.isolate()))
        {
            Parser.treePrinter("PROG");
            Parser.depthLevel++;
            CODE.Code();
            if (Parser.file.size()>++Parser.index)
                Prog$();
        }
        else
            Parser.errorPrinter("CODE expected at beginning of PROG");
        Parser.depthLevel--;
    }

    static void Prog$() throws IOException {
        if(Parser.isolate().equals("(tok_semi-colon)"))
        {
            if (Parser.file.size()>++Parser.index)
            {
                Parser.depthLevel--;
                PROC_DEFS.Proc_Defs();
            }
            else
                Parser.errorPrinter("Expected PROC_DEFS after semi-colon");
            if (Parser.inc())
                Prog$();
        }
        else if(Parser.isolate().equals("(tok_cbrace)"))
        {
            Parser.index--;
        }
        else
            Parser.errorPrinter("");
    }
}
