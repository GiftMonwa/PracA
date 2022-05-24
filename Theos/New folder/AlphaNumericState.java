


import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")

public class AlphaNumericState extends State
{
    public static Map<String, String> stringToString;
    static {
        stringToString = new HashMap<>();
        stringToString.put("and", ":and (tok_and)\n");
        stringToString.put("add", ":add (tok_add)\n");
        stringToString.put("arr", ":arr (tok_arr)\n");
        stringToString.put("true", ":true (tok_true)\n");
        stringToString.put("false", ":false (tok_false)\n");
        stringToString.put("if", ":if (tok_if)\n");
        stringToString.put("while", ":while (tok_while)\n");
        stringToString.put("until", ":until (tok_until)\n");
        stringToString.put("input", ":input (tok_input)\n");
        stringToString.put("num", ":num (tok_num)\n");
        stringToString.put("proc", ":proc (tok_proc)\n");
        stringToString.put("or", ":or (tok_or)\n");
        stringToString.put("sub", ":sub (tok_sub)\n");
        stringToString.put("larger", ":larger (tok_larger)\n");
        stringToString.put("then", ":then (tok_then)\n");
        stringToString.put("do", ":do (tok_do)\n");
        stringToString.put("main", ":main (tok_main)\n");
        stringToString.put("call", ":call (tok_call)\n");
        stringToString.put("output", ":output (tok_output)\n");
        stringToString.put("bool", ":bool (tok_bool)\n");
        stringToString.put("not", ":not (tok_not)\n");
        stringToString.put("mult", ":mult (tok_mult)\n");
        stringToString.put("else", ":else (tok_else)\n");
        stringToString.put("eq", ":eq (tok_eq)\n");
        stringToString.put("halt", ":halt (tok_halt)\n");
        stringToString.put("return", ":return (tok_return)\n");
        stringToString.put("string", ":string (tok_string)\n");
    }

    public AlphaNumericState(String f, String part, int pos, int l)
    {
        full = f;
        partial = part;
        position = pos;
        line = l;
    }

    @Override
    public void resolve() throws Exception
    {
        if(full.length()!=position&&(Character.isDigit(full.charAt(position))||Character.isLowerCase(full.charAt(position))))
            next = new AlphaNumericState(full,partial+ full.substring(position,position+1),position+1,line);
        else
        {
            Lexer.fileWriter.write(Lexer.mark++  + stringToString.getOrDefault(partial, ":"+partial+" (tok_userDefinedIdentifier)\n"));
            Lexer.filelineWriter.write("[line: " + line + ", col:" + position+"]\n");
            next = new StartState(full,"",position,line);
        }
    }
}
