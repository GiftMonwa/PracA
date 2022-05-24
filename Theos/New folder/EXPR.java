import java.io.IOException;

public class EXPR
{
    static String part = "(tok_str)(tok_int)(tok_true)(tok_false)" +
            "(tok_userDefinedIdentifier)" + 
            "(tok_input)(tok_not)" + 
            "(tok_and)(tok_or)(tok_eq)(tok_larger)(tok_add)(tok_sub)(tok_mult)";

    static void Expr() throws IOException {
        if(part.contains(Parser.isolate()))
        {
            Parser.treePrinter("Expr");
            Parser.depthLevel++;

            if(BINOP.part.contains(Parser.isolate()))
                BINOP.BinOp();
            else if(CONST.part.contains(Parser.isolate()))
                CONST.Const();
            else if(UNOP.part.contains(Parser.isolate()))
                UNOP.Unop();
            else if(Parser.isolate().equals("(tok_userDefinedIdentifier)")) {
                if (Parser.file.size()>++Parser.index && Parser.isolate().equals("(tok_obracket)"))
                {    
                    FIELD.Field();
                }
                 else
                 {
                    Parser.index--; 
                    VAR.Var();
                 }
            }
            Parser.depthLevel--;
        }
            
    }
}

