public class StartState extends State
{
    public StartState(String f, String part, int pos, int l)
    {
        full = f;
        partial = part;
        position = pos;
        line = l;
    }

    @Override
    public void resolve()
    {
        if(full.length()<=position)
            next = null;
        else if(full.charAt(position)==32||full.charAt(position)=='\t')
            next = new StartState(full,"",position+1,line);
        else if(Character.isLowerCase(full.charAt(position)))
            next = new AlphaNumericState(full,partial+ full.substring(position,position+1),position+1,line);
        else if(full.charAt(position) == '\"')
            next = new StringState(full,partial+ full.substring(position,position+1),position+1,line);
        else if(full.charAt(position)=='-'||Character.isDigit(full.charAt(position))&&full.charAt(position)!='0')
            next = new NumericState(full,partial+ full.substring(position,position+1),position+1,line);
        else
            next = new SymbolState(full,partial+ full.substring(position,position+1),position+1,line);
    }
}
