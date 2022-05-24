import java.io.IOException;

public class Algorithm
{
    static void algorithm() throws IOException {
        if(INSTR.part.contains(Parser.isolate()))
        {
            Parser.treePrinter("Algorithm");
            Parser.depthLevel++;
            INSTR.Instr();
            if (Parser.file.size() > ++Parser.index)
                algorithm$();
            Parser.depthLevel--;
        }
        else
        {   
            Parser.index--;
        }


    }
    static void algorithm$() throws IOException {
        if(Parser.file.get(Parser.index).contains("(tok_semi-colon)"))
        {
            if (Parser.file.size()>++Parser.index) {
                if (INSTR.part.contains(Parser.isolate()))
                    algorithm();
                else
                    Parser.index--;
            }
            else
                Parser.errorPrinter("Expected Algorithm to follow semi-colon.");
        }
        else 
             Parser.errorPrinter("Expected Algorithm to follow semi-colon.");
    }
}
