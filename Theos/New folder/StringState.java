

public class StringState extends State
{
    public StringState(String f, String part, int pos, int l)
    {
        full = f;
        partial = part;
        position = pos;
        line = l;
    }

    @Override
    public void resolve() throws Exception
    {
        if (partial.length() <= 15)
        {
            if(full.length()!=position&&(Character.isDigit(full.charAt(position))||Character.isUpperCase(full.charAt(position))||full.charAt(position)==' '))
            {
                next = new StringState(full,partial+ full.substring(position,position+1),position+1,line);
            }
            else if(full.length()!=position&&(full.charAt(position)==34))
            {
                partial += full.substring(position,position+1);
                Lexer.fileWriter.write(Lexer.mark++ +":"+partial + " (tok_str)\n");
                Lexer.filelineWriter.write("[line: " + line + ", col:" + position+"]\n");
                next = new StartState(full,"",position+1,line);
            }
            else if(full.length() == position)
            {
                System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: string must be terminated with '\"'");
                System.exit(0);

            }
            else
            {
                System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: invalid character "+full.charAt(position));
                System.exit(0);
            }
        }
        else
        {
            System.out.println("Lexical Error [line: "+line+ ", col: "+position+"]: '"+partial+"' strings have at most 15 characters");
            System.exit(0);
        }

    }
}
