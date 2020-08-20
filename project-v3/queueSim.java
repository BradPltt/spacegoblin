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
     *
     * Constructor for objects of class readCSV
     */
    public queueSim()
    {
        System.out.println('\u000c');
        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the file you want to load?");
        System.out.println("");
        System.out.println("Please enter the filename in the format filename.csv");
        filename = input.nextLine();
        File  thefile = new File(filename);  // generate the file handle
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];  // where we keep all those lines we read in.
        
        boolean keepGoing = true; //this boolean keeps the program running. As long as it remains true, the program will continue.

        System.out.println('\u000c');
        System.out.println("If you would like to run a priority queue press p");
        System.out.println("If you would like to run a linear queue press l");
        System.out.println("If you would like to quit the program press q");

        String choice;
        choice = input.nextLine();

        while (keepGoing){
            int linecount=0;
            int studentQN; //number of people in the student queue
            int studentQS = 0; //size of the student queue
            int teacherQN;//number of people in the teacher queue
            int teacherQS = 0;//size of the teacher queue
            int studentAdN = 0; //students added to the student queue
            int teacherAdN = 0;//teachers added to the teacher queue
            int totalSQS = 0;//total student queue size
            int totalTQS = 0;//total teacher queue size
            int totalTT = 0;//total teacher time in queue (every teacher)
            int totalST = 0;//total student time in queue (every student)

            int timeWaiting = 0; //variable for time in queue (individual)

            if (choice.equals("p")){
                Queue studentsQ = new Queue(); //this creates a student queue
                Queue teachersQ = new Queue(); //this creates a teacher queue

                //System.out.println('\u000c'); //this clears any text from the terminal window
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
                        for (studentQN =0; studentQN<studentsJoining; studentQN ++){
                            Person Student = new Person (values[0], false);
                            studentsQ.joinQ(Student);
                            studentQS ++;
                            studentAdN++;
                        }
                        System.out.println(studentAdN + " students joined the queue.");
                        System.out.println("");
                        int teachersJoining = Integer.parseInt(values[2]);
                        for (teacherQN = 0; teacherQN<teachersJoining; teacherQN++){
                            Person Teacher = new Person (values[0], true);
                            teachersQ.joinQ(Teacher);
                            teacherQS++;
                            teacherAdN++;
                        }
                        System.out.println(teacherAdN + " teachers joined the queue.");
                        System.out.println("");
                        int served = Integer.parseInt(values[3]);
                        for (int j = 1; j<=served; j++){
                            if (!teachersQ.emptyQ()){
                                Person john = teachersQ.leaveQ();
                                teacherQS--;
                                totalTQS++;
                                int timeOfEntry = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount + 1 - timeOfEntry;
                                System.out.println("A teacher left the queue, their wait time was " +timeWaiting);
                                totalTT = totalTT + timeWaiting;
                            }
                            else{
                                Person john = studentsQ.leaveQ();
                                studentQS --;
                                totalSQS ++;
                                int timeOfEntry = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount + 1 - timeOfEntry;
                                System.out.println("A student left the queue, their wait time was " +timeWaiting);
                                totalST = totalST + timeWaiting;
                            }
                        }
                        linecount++;
                        System.out.println("At "+values[0]+" the number of students in the queue is " +studentQS+".");
                        System.out.println("At "+values[0]+" the number of teachers in the queue is " +teacherQS+".");
                    }
                    
                    for (int i =0; i<linecount; i++){
                        String values[] = CSVlines[i].split(",");  // process the line from the Scanner and break it up at each comma.                                              
                        for (int j=0; j< values.length;j++)
                            AllLinesAllElements[i][j]=values[j];
                    }  
                    
                    int averageStudentTime = totalST/totalSQS;
                    int averageTeacherTime = totalTT/totalTQS;
                    System.out.println("The average student wait time was "+averageStudentTime+" minutes.");
                   System.out.println("The average teacher wait time was "+averageTeacherTime+" minutes.");
                }  catch (IOException e) {System.out.println(e);}
                System.out.println("");
                System.out.println("--------------------------");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();

            } else if (choice.equals("l")){
                Queue everyone = new Queue(); //this creates a queue for EVERYONE. Both students and teachers will join this queue

                //System.out.println('\u000c'); //this clears any text from the terminal window
                try{
                    Scanner reader = new Scanner(thefile); // open the file with the Scanner
                    String line=reader.nextLine();
                    while(reader.hasNextLine() && linecount < MAXLINES){
                        line=reader.nextLine();
                        String values[] = line.split(",");
                        studentAdN = 0;
                        teacherAdN = 0;

                        int studentsJoining = Integer.parseInt(values[1]);
                        for (studentQN = 0; studentQN<studentsJoining; studentQN++){
                            Person Student = new Person (values[0], false);
                            everyone.joinQ(Student);
                            studentQS++;
                            studentAdN++;
                        }
                        System.out.println("Students added: " + studentAdN);

                        int teachersJoining = Integer.parseInt(values[2]);
                        for (teacherQN = 0; teacherQN < teachersJoining; teacherQN++){
                            Person Teacher = new Person (values[0], true);
                            everyone.joinQ(Teacher);
                            teacherQS++;
                            teacherAdN++;
                        }
                        System.out.println("Teachers added: "+teacherAdN);
                        int served = Integer.parseInt(values[3]);
                        for (int j =1; j<= served; j++){
                            if (!everyone.emptyQ()){
                                Person john = everyone.leaveQ(); 
                                teacherQS--;
                                totalTQS++;
                                int timeOfEntry = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount + 1 - timeOfEntry;
                                System.out.println("A teacher left the queue, their time in the queue was " +timeWaiting+" minutes");
                                totalTT = totalTT+timeWaiting;
                            }
                            else {
                                Person john = everyone.leaveQ();
                                studentQS--;
                                totalSQS++;
                                int timeOfEntry = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount + 1 - timeOfEntry;
                                System.out.println("A student left the queue, their time in the queue was " +timeWaiting+" minutes.");
                                totalST = totalST+timeWaiting;
                            }
                        }
                        linecount++;
                        System.out.println("At "+values[0]+" the number of students in the queue is "+studentQS);
                        System.out.println("At "+values[0]+" the number of teachers in the queue is "+teacherQS);
                    }
                    int averagePersonTime = (totalST+totalTT)/(totalSQS+totalTQS);
                    System.out.println("The average time for everyone in the queue was "+averagePersonTime+" minutes.");
                } catch (IOException e) {System.out.println(e);}
                System.out.println("");
                System.out.println("--------------------------");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();
            } else if(choice.equals("q"))keepGoing = false;
            else{
                System.out.println('\u000c');
                System.out.println("Sorry, that wasn't a valid input.");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();
            }

        }
    }
}

