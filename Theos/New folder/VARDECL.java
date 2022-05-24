import java.io.IOException;

public class VARDECL
{
    static String decl = "(tok_num)(tok_string)(tok_bool)";
    static void VarDecl() throws IOException {

        if(DECL.decl.contains(Parser.isolate())||DECL.array.contains(Parser.isolate()))
        {
            Parser.treePrinter("VarDecl");
            Parser.depthLevel++;

            DECL.Decl();
            if(Parser.inc())
                VarDecl$();
            Parser.depthLevel--;
        }
        else 
            Parser.index--;
        
    }
    static void VarDecl$() throws IOException {
        if(Parser.file.get(Parser.index).contains("tok_semi-colon"))
        {
            if (Parser.file.size()>++Parser.index)
                if (decl.contains(Parser.isolate()) || DECL.array.contains(Parser.isolate()))
                    VarDecl();
                else
                    Parser.index--;
            else
                Parser.errorPrinter("Expected DECL to follow semi-colon.");
        }
        else
            Parser.index--;
    }
}
