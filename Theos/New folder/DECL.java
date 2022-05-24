import java.io.IOException;

public class DECL {
    static String decl = "(tok_num)(tok_string)(tok_bool)";
    static String array = "(tok_arr)";

    static void Decl() throws IOException {
        Parser.treePrinter("Dec");
        Parser.depthLevel++;

        if (Parser.file.get(Parser.index).contains("(tok_arr)")) {
            if (Parser.file.size() > ++Parser.index)
                TYPE.Type();
            else
                Parser.errorPrinter("Expected TYPE after arr.");
            Parser.obracket();
            if (Parser.file.size() > ++Parser.index)
                CONST.Const();
            else
                Parser.errorPrinter("Expected CONST after Opening Bracket.");
            Parser.cbracket();
            if (Parser.inc())
                VAR.Var();
            else
                Parser.errorPrinter("Expected VAR after Closing Bracket.");
        } else if (decl.contains(Parser.isolate())) {
            TYPE.Type();
            if (Parser.inc())
                VAR.Var();
            else
                Parser.errorPrinter("Expected VAR after TYPE.");
        }
        Parser.depthLevel--;

    }
}