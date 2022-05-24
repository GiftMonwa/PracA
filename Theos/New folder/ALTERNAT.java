import java.io.IOException;

public class ALTERNAT
{
    static void Alternat() throws IOException {
        if(Parser.file.get(Parser.index).contains("(tok_else"))
        {
            Parser.treePrinter("Alternat");
            Parser.treePrinter("conditional branch: else");
            Parser.depthLevel++;
            Parser.obrace();
            if(Parser.inc())
                 Algorithm.algorithm();
            else    
                Parser.errorPrinter("Expected algorithm after the opening barce");
            Parser.cbrace(); 
            Parser.depthLevel--;
        }
    }
        
        
        
        
       
    
}