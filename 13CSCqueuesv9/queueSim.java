/**
 * readCSV - a sample bit of code that reads CSV
 * (comma seperated value) text files and keeps the
 * results in an array.
 * 
 * This code can be made a lot more general.  That is an exercise to the reader.
 * It can also be considerably shortened.  Multiple lines are used one after another
 * which could put run together on a single line.  This is to illustrate the steps.
 * 
 *
 * @author of original code Bill Viggers
 * @version 18 - June - 2020
 * 
 * Changes made by Callum Godfrey
 */

import java.util.Scanner; // needed to read files
import java.io.IOException; // handle errors
import java.io.File;  //  File handles
import java.io.FileWriter; // Writes files

public class queueSim
{

    String filename; // change to reflect the CSV we are reading
    final int MAXLINES=100; // for ease of writing, we are only going to read at most 100 lines.
    final int VALUESPERLINE=4;  // for ease of writing, we know how many values we get on each line.
    /**
     * Constructor for objects of class readCSV
     */
    public queueSim()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the file you want to load?");
        filename = input.nextLine();
        File  thefile = new File(filename);  // generate the file handle
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];  // where we keep all those lines we read in.
        int linecount=0;  // initially keeps track of lines read, eventually used to remember the number that was read;
        boolean keepGoing = true;
        
        System.out.println('\u000c');
        System.out.println("If you would like to run a priority queue press p");
        System.out.println("If you would like to run a linear queue press l");
        System.out.println("If you would like to quit the program press q");
        
        String choice;
        choice = input.nextLine();
        

        
        
        while (keepGoing){
        int studentQN = 0;
        int teacherQN = 0;
        int studentAdN = 0;
        int teacherAdN = 0;
        int timeQT = 0;
        int timeQS = 0;
        int totalTT = 0;
        int totalST = 0;
        
        int averageWait = 0;
        int personAdd = 0;
        
        if (choice.equals("p")){
         Queue students = new Queue();
         Queue teachers = new Queue();
         //Creates two new queues, one for teachers, one fore students.
         System.out.println('\u000c');
        try {
            Scanner reader = new Scanner(thefile); // open the file with the Scanner
            String line=reader.nextLine();
            
            // Read in the file, stop at file end or if we read too many lines
            while (reader.hasNextLine()  && linecount < MAXLINES){  
                line=reader.nextLine();
                String values[] = line.split(",");
                studentAdN = 0;
                teacherAdN = 0;
               int studentsJoining = Integer.parseInt(values[1]);
            }

            // Now we have all the lines, lets print them out enmass
            // This is just to prove we have read them in okay.  In reality, we don't need to do this step.
            for (int i =0; i<linecount; i++)
                System.out.println(CSVlines[i]);
            
            // We want to split the lines on the comma.  The Split command can do this for us.
            // In production code, we would probably do this during the inital reading step
            for (int i =0; i<linecount; i++){
                // process the line from the Scanner and break it up at each comma.
                try{
                    int v[] = new int[values.length];
                    int x = 0;
                    while(x < values.length){
                     v[x] = Integer.parseInt(values[x]);
                     x = x + 1;   
                    }
                }catch(NumberFormatException e){
                    
                }
                // Now we will print it out again, again, this is just to prove a point.  Real code doesn't need this.
                for (int j=0; j< values.length;j++)
                    System.out.print(values[j]+"****");
                System.out.println("");

                // However, we probably will want to put this into an array of Strings, which we may want to later do some
                // other processing on.
                for (int j=0; j< values.length;j++)
                    AllLinesAllElements[i][j]=values[j];
            }  // process the file we read, line by line.

        } catch (IOException e) {System.out.println(e);}
        
        // Here we have all the information in our array and we can do what we want with it.
        //Example #1,  print out just the first column
        System.out.println("The first column read");
        for (int i=0;i<linecount;i++)
            System.out.println(AllLinesAllElements[i][0]);

        // Example #2, I could print out the number of staff arriving at each time.
        System.out.println("Staff arriving at any given time.");
        for (int i=0;i<linecount;i++)
            System.out.println("At "+AllLinesAllElements[i][0]+" "+AllLinesAllElements[i][2]+" arrived.");                
            
    }
    else if(choice.equals("q"))keepGoing = false;
}
}
}