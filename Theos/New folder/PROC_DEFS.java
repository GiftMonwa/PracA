import java.io.IOException;

public class PROC_DEFS
{
    static void Proc_Defs() throws IOException {
       
        if(Parser.isolate().equals("(tok_proc)"))
        {
            Parser.treePrinter("ProcDefs");
            Parser.depthLevel++;
            PD.Pd();
            Parser.comma();
            if(Parser.inc())
                Proc_Defs$();
            Parser.depthLevel--;
        }
       else
       {
           Parser.index--;
       }


       
    }
    static void Proc_Defs$() throws IOException {
        if(Parser.isolate().contains("(tok_proc)"))
        {
            Proc_Defs();
        }
        else
            Parser.index--;

    }
}
