import java.util.ArrayList;

public class Parser {
    ArrayList<token> tList;
    String TK;  //holds the token
    Parser(ArrayList<token> e)
    {
        for(token i: e)
        {
            tList.add(i);
        }
    }

    public void Parse()
    {
        SPLProgr();
    }

    public void SPLProgr()
    {
        if(!tList.isEmpty())
        {
            
        }
    }

    public void ProcDefs()
    {
    }

    public void Algorithm()
    {

    }

    public void VarDecl()
    {

    }

    public void PD()
    {

    }

    public void Instr()
    {

    }

    public void Assign()
    {

    }

    public void Branch()
    {

    }

    public void Loop()
    {

    }

    public void PCall()
    {

    }

    public void LHS()
    {

    }

    public void Expr()
    {

    }

    public void Alternat()
    {

    }

    public void Var()
    {

    }

    public void Field()
    {

    }

    public void Const()
    {

    }

    public void UnOp()
    {

    }

    public void BinOp()
    {

    }

    public void Dec()
    {

    }

    public void TYP()
    {

    }
}
