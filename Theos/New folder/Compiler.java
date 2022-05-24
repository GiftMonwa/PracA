public class Compiler {
    static Lexer lexer;
    static Parser parser;
    static Semantic_analyser semantic_analyser;
    public static void main(String []args)
    {
        try{
            lexer.main(args);
            parser.main(null);
            semantic_analyser.main(null);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}