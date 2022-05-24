

import java.util.HashMap;
import java.util.Map;

public class SymbolState extends State
{

    public static Map<Character, String> charToString;
    static {
        charToString = new HashMap<>();
        charToString.put('[', ":[ (tok_obracket)\n");
        charToString.put(']', ":] (tok_cbracket)\n");
        charToString.put('(', ":( (tok_oparen)\n");
        charToString.put(')', ":) (tok_cparen)\n");
        charToString.put('{', ":{ (tok_obrace)\n");
        charToString.put('}', ":} (tok_cbrace)\n");
        charToString.put('=', ":= (tok_assign)\n");
        charToString.put(':', ":: (tok_colon)\n");
        charToString.put(',', ":, (tok_comma)\n");
        charToString.put(';', ":; (tok_semi-colon)\n");
        charToString.put('0', ":0 (tok_zero)\n");
    }





    public SymbolState(String f, String part, int pos, int l)
    {
        full = f;
        partial = part;
        position = pos;
        line = l;
    }

    @Override
    public void resolve() throws Exception
    {
        if(charToString.containsKey(partial.charAt(0)))
        {
            Lexer.fileWriter.write(Lexer.mark++ +charToString.get( partial.charAt(0)));
            Lexer.filelineWriter.write("[line: " + line + ", col:" + position+"]\n");
            next = new StartState(full,"",position,line);
        }
        else
        {
            System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: '"+partial.charAt(0)+"' is not a valid character");
            System.exit(0);
        }
    }
}
