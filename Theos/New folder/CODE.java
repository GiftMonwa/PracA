import java.io.IOException;

public class CODE
{
    static void Code() throws IOException {
        if(INSTR.part.contains(Parser.isolate()))
        {
            Parser.treePrinter("CODE");
            Parser.depthLevel++;
            INSTR.Instr();
            if (Parser.file.size() > ++Parser.index)
                Code$();
        }
        Parser.depthLevel--;
    }
    static void Code$() throws IOException {
        if(Parser.file.get(Parser.index).contains("(tok_semi-colon)"))
        {
            if (Parser.file.size()>++Parser.index) {
                if (!Parser.isolate().equals("(tok_proc)"))
                    Code();
                else
                    Parser.index-=2;
            }
            else
                Parser.errorPrinter("Expected CODE to follow semi-colon.");
        }
        else if(Parser.isolate().equals("(tok_cbrace)"))
            Parser.index--;
    }
}
