import java.io.PrintWriter;
import java.util.Scanner;

public class Avg3X3Filter {
    int numRows;
    int numCols;
    int minVal;
    int maxVal;
    int newMin;
    int newMax;
    int[][] mirrorFramedAry;
    int[][] tempAry;
    int[] neighborAry;
    int pixel;
    Scanner infile;
    PrintWriter outfile;

    public Avg3X3Filter (Scanner in_infile, PrintWriter in_outfile){
        infile = in_infile;
        outfile = in_outfile;
        //step1 initialising
        try {
            numRows=infile.nextInt();
            numCols=infile.nextInt();
            minVal=infile.nextInt();
            maxVal=infile.nextInt();
        } catch (Exception e) {
            System.out.println("Error reading header from input file");
            System.exit(1);
        }
        mirrorFramedAry = new int[numRows+2][numCols+2];
        tempAry= new int[numRows+2][numCols+2];
        neighborAry = new int[9];
    }

    public void run (){
        //step2 fill mirrorFramedAry
        //read into array
        for (int r=1; r<numRows+1; r++){
            for (int c=1; c<numCols+1; c++){
                mirrorFramedAry[r][c]= infile.nextInt();
            }
        }
        //mirror to frame
        for (int c=1; c<numCols+2; c++){
            //top
            mirrorFramedAry[0][c]=mirrorFramedAry[1][c];
            //bottom
            mirrorFramedAry[numRows+1][c]=mirrorFramedAry[numRows][c];
        }
        for (int r=0; r<numRows+2; r++){
            //left
            mirrorFramedAry[r][0]=mirrorFramedAry[r][1];
            //right
            mirrorFramedAry[r][numCols+1]=mirrorFramedAry[r][numCols];
        }
        //step3 process mirrorFramedAry -> neighborAry -> tempAry, keep track minVal and maxVal;
        //double for-loop loops thru every pixel in order
        int neighborAryAvg=0;
        for (int r=1; r<numRows+1; r++){
            for (int c=1; c<numCols+1; c++){
                //fill neighborAry, sampling area is fixed at 3x3
                //use of neighborAry is redundant.
                neighborAry[0]=mirrorFramedAry[r-1][c-1];
                neighborAryAvg+=neighborAry[0];
                neighborAry[1]=mirrorFramedAry[r-1][c];
                neighborAryAvg+=neighborAry[1];
                neighborAry[2]=mirrorFramedAry[r-1][c+1];
                neighborAryAvg+=neighborAry[2];
                neighborAry[3]=mirrorFramedAry[r][c-1];
                neighborAryAvg+=neighborAry[3];
                neighborAry[4]=mirrorFramedAry[r][c];
                neighborAryAvg+=neighborAry[4];
                neighborAry[5]=mirrorFramedAry[r][c+1];
                neighborAryAvg+=neighborAry[5];
                neighborAry[6]=mirrorFramedAry[r+1][c-1];
                neighborAryAvg+=neighborAry[6];
                neighborAry[7]=mirrorFramedAry[r+1][c];
                neighborAryAvg+=neighborAry[7];
                neighborAry[8]=mirrorFramedAry[r+1][c+1];
                neighborAryAvg+=neighborAry[8];
                //calculate average
                neighborAryAvg/=9;
                //save to tempAry
                tempAry[r][c]=neighborAryAvg;
                //update new Min and Max
                if (newMin>neighborAryAvg)
                    newMin=neighborAryAvg;
                if (newMax<neighborAryAvg)
                    newMax=neighborAryAvg;
                neighborAryAvg=0;//reset
            }
        }//step 4 repeat until all pixels are processed
        //step5 output new header to outfile
        outfile.println(numRows+" "+numCols+" "+newMin+" "+newMax);
        //step6 output all pixels
        for (int r=1; r<numRows+1; r++){
            for (int c=1; c<numCols+1; c++){
                outfile.print(tempAry[r][c]+" ");
            }
            outfile.println();
        }
    }
}
