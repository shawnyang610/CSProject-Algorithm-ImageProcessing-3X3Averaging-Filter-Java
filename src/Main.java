//CS780-37 Project 2.1 Average 3x3 filter
//Shawn Yang
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner infile=null;
        PrintWriter outfile=null;
        if (args.length<2){
            System.out.println("Please supply both infile name and outfile name.");
            System.exit(1);
        }
        try {
            infile = new Scanner (new FileReader(args[0]));
            outfile = new PrintWriter(args[1]);
        } catch (IOException e) {
            System.out.println("Problem opening file");
            System.exit(1);
        }
        Avg3X3Filter filter = new Avg3X3Filter(infile, outfile);
        filter.run();


        infile.close();
        outfile.close();
    }
}
