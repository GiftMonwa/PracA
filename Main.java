public class Main {
    
    public static void main(String[] args) {
       Lexer lexer=new Lexer();
       try{
        lexer.readInput("input.txt");
        lexer.Tokenizer();
        //boolean number=Pattern.matches("0|(([1-9]|(-.[1-9])).[0-9]*)","");
       }
       catch(Exception e)
       {
         e.printStackTrace();
       }
    }
}
