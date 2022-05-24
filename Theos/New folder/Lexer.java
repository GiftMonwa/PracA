import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Lexer {

    public static int mark = 0;
    public static FileWriter fileWriter;
    public static FileWriter filelineWriter;
    public static void main(String []args) throws Exception
    {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the txt e.g program.txt");
        String txt = sc.nextLine();
        FileReader fileReader = new FileReader(txt);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        fileWriter = new FileWriter("output.txt");
        filelineWriter = new FileWriter("lineout.txt");
        if(bufferedReader.ready())
        {
            int i =1;
            while(bufferedReader.ready())
            {
                State move = new StartState(bufferedReader.readLine(), "", 0, i++);
                while (move != null)
                {
                    move.resolve();
                    move = move.next;
                }
            }
        }
        else
            System.out.println("Lexical Error [line: 0, col: 0]: File is empty.");
        fileWriter.close();
        filelineWriter.close();
    }
}