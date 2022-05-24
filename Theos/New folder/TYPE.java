import java.io.IOException;

public class TYPE
{
    static void Type() throws IOException {
        Parser.treePrinter("TYP");
        Parser.depthLevel++;
        Parser.treePrinter("type: " + Parser.isolate().substring(5,Parser.isolate().length()-1));
        Parser.depthLevel--;
    }
}
