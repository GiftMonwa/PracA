
@SuppressWarnings("Duplicates")

public class NumericState extends State
{
    public NumericState(String f, String part, int pos, int l)
    {
        full = f;
        partial = part;
        position = pos;
        line = l;
    }

    @Override
    public void resolve() throws Exception
    {

        if(full.length()!=position && partial.charAt(0)=='-' && full.charAt(position)=='0' && partial.length()==1||full.length()!=position && (((full.charAt(position)=='-')&&partial.length()==1)||Character.isLowerCase(full.charAt(position))))
        {
            if((partial.charAt(0)=='-')&& full.charAt(position)=='0')
               System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: '-' value cannot be followed by '"+full.charAt(position)+"'");
            else  if(full.charAt(position)=='-')
                System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: '-' value cannot be followed by another '"+full.charAt(position)+"'");
            else
                System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: numeric value cannot be followed by character '"+full.charAt(position)+"'");
            System.exit(0);
        }
        else if(full.length()!=position&&Character.isDigit(full.charAt(position)))
        {
            next = new NumericState(full,partial+ full.substring(position,position+1),position+1,line);
        }    
        else
        {
            Lexer.fileWriter.write(Lexer.mark++ + ":" + partial + " (tok_int)\n");
            Lexer.filelineWriter.write("[line: " + line + ", col:" + position+"]\n");
            next = new StartState(full,"",position,line);
        }
    }
}
