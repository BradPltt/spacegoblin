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
 * Credit to Felix Vidal and Fletcher Smith for assistance in writing code
 *
 * Changes made by Callum Godfrey
 */

import java.util.Scanner; // needed to read files
import java.io.IOException; // handle errors
import java.io.File;  //  File handles
import java.io.FileWriter;//to write the files
public class queueSim{

    String filename; // change to reflect the CSV we are reading
    final int MAXLINES=100; // for ease of writing, we are only going to read at most 100 lines.
    final int VALUESPERLINE=4;  // for ease of writing, we know how many values we get on each line.
    String save[][] = new String[4][200];//creates an array to save simulation information
    public queueSim(){
        System.out.println('\u000c');//this clears any text from the terminal window
        Scanner input = new Scanner(System.in);//this creates a new scanner
        System.out.println("What is the name of the file you want to load?");
        System.out.println("");
        System.out.println("Please enter the filename in the format filename.csv");
        System.out.println("");
        System.out.println("The example file is named arrivals.csv");
        filename = input.nextLine();//sets the filename to the user input, and opens the file of the same name as long as it is in the project folder
        File  thefile = new File(filename);  // generate the file handle
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];  // where we keep all those lines we read in.

        boolean keepGoing = true; //this boolean keeps the program running. As long as it remains true, the program will continue.

        System.out.println('\u000c');//this clears any text from the terminal window
        System.out.println("If you would like to run a priority queue press p");
        System.out.println("If you would like to run a linear queue press l");
        System.out.println("If you would like to quit the program press q");

        String choice;//creates a string named choice
        choice = input.nextLine();//sets the value of String choice to the user input

        File saveFile = new File ("simulationInformation.txt"); //creates a file for the simulation information to be saved to

        while (keepGoing){

            int linecount=0;  //initially keeps track of lines read, eventually used to remember the number that was read; 
            int studentsAdded=0;//creates a variable for the students added to the student queue
            int teachersAdded=0;//creates a variable for the teachers added to the teacher queue
            int studentQueueNumber;//creates a varibale for the number of students in the student queue
            int teacherQueueNumber;//creates a variable for the number of teachers in the teacher queue
            int studentQueueSize=0;//creates a variable for the size of the student queue
            int teacherQueueSize=0;//creates a variable for the size of the teacher queue
            int totalStudentTime=0;//creates a variable for the total student time in queue (every student)
            int totalTeacherTime=0;//creates a variable for the total teacher time in queue (every teacher)
            int totalStudentQueueSize=0;//creates a variable for the total student queue size (every student over the course of an hour)
            int totalteacherQueueSize=0;//creates a variable for the total teacher queue size (every teacher over the course of an hour)
            int studentServed = 0;
            int teacherServed = 0;
            int timeWaiting=0;//creates a variable for the time spent waiting in queue

            if(choice.equals("p")){ //this runs the following code if the user inputs 'p'
                Queue studentQ = new Queue();//this creates a student queue
                Queue teacherQ = new Queue();//this creates a teacher queue

                System.out.println('\u000c');//this clears any text from the terminal window
                try {
                    Scanner reader = new Scanner(thefile); // open the file with the Scanner
                    String line=reader.nextLine();//throw away first line

                    while (reader.hasNextLine()  && linecount < MAXLINES){
                        line=reader.nextLine();
                        String values[] = line.split(",");//splits the line at each comma, only taking the values in between the commas.
                        studentsAdded=0;//sets students added to 0
                        teachersAdded=0;//sets teachers added to 0

                        int studentsJoining = Integer.parseInt(values[1]);//creates a variable for the students joining the queue
                        for ( studentQueueNumber =0; studentQueueNumber<studentsJoining; studentQueueNumber++){ 
                            Person Student = new Person (values[0], false);//creates a new person, the person is a student
                            studentQ.joinQ(Student);//enqueues the new student at the back of the student queue
                            studentQueueSize++;//increases the size of the student queue by one
                            studentsAdded++;//increases the value of students added by one
                        }
                        System.out.println(studentsAdded + " students joined the queue.");//prints the number of students joining the queue every minute

                        int teachersJoining = Integer.parseInt(values[2]);//creates a variable for the teachers joining the queue
                        for (teacherQueueNumber =0; teacherQueueNumber<teachersJoining; teacherQueueNumber++){
                            Person Teacher = new Person (values[0], true);//creates a new person, the person is a teacher
                            teacherQ.joinQ(Teacher);//enqueues the new teacher in the teacher (priority) queue
                            teacherQueueSize++;//increases the size of the teacher queue by one
                            teachersAdded++;//increases the value of teachers added by one
                        }
                        System.out.println(teachersAdded + " teachers joined the queue.");//prints the amount of teachers that join the queue each minute
                        System.out.println("");
                        int served = Integer.parseInt(values[3]);//creates a variable for the people served
                        for (int i =1; i<=served; i++){
                            if (!teacherQ.emptyQ()){
                                Person john = teacherQ.leaveQ();
                                teacherQueueSize--;
                                totalteacherQueueSize++;
                                teacherServed++;
                                int timeJoined = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount - timeJoined + 1;
                                System.out.println("A teacher left the queue, their wait time was "+timeWaiting+" minute(s).");
                                totalTeacherTime = totalTeacherTime+timeWaiting;
                            }
                            else{
                                Person john = studentQ.leaveQ();
                                studentQueueSize--;
                                totalStudentQueueSize++;
                                studentServed++;
                                int timeJoined = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount - timeJoined + 1;
                                System.out.println("A student left the queue, their wait time was "+timeWaiting+" minute(s).");
                                totalStudentTime = totalStudentTime+timeWaiting;
                            }
                        }
                        CSVlines[linecount]=line;  
                        linecount++;//increases the linecount by one
                        System.out.println("");
                        System.out.println("At "+values[0]+" minute(s), the number of students in the queue is "+studentQueueSize+".");//prints the amount of students in the student queue at each minute
                        System.out.println("At "+values[0]+" minute(s), the number of teachers in the queue is "+teacherQueueSize+".");//prints the amount of teachers in the teacher queue at each minute
                        System.out.println("");              
                    }

                    for (int i =0; i<linecount; i++){
                        String values[] = CSVlines[i].split(",");  // process the line from the Scanner and break it up at each comma.                                              
                        for (int j=0; j< values.length;j++)
                            AllLinesAllElements[i][j]=values[j];
                    }  

                    int averageStudentWait = totalStudentTime/totalStudentQueueSize;//creates a variable for the average student wait time, makes it equal to the total student time in queue divided by the total student queue size 
                    int averageTeacherWait = totalTeacherTime/totalteacherQueueSize;//creates a variable for the average teacher wait time, makes it equal to the total teacher time in queue divided by the total teacher queue size
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The average student wait time was "+averageStudentWait+" minute(s).");//prints the average student wait time in the student queue
                    System.out.println("The average teacher wait time was "+averageTeacherWait+" minute(s).");//prints the average teacher wait time in the teacher (priority) queue
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The total number of students served was "+studentServed+".");
                    System.out.println("The total number of teachers served was "+teacherServed+".");
                } catch (IOException e) {System.out.println(e);}
                System.out.println("");
                System.out.println("---------------------------");//prints a line to indicate that the queue is finished
                System.out.println("************End************");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();
            }
            else if(choice.equals("l")){//this runs the following code if the user inputs 'l'
                Queue everyoneQueue = new Queue();//creates one queue for both teachers and students to join

                System.out.println('\u000c');//clears any text from the terminal window
                try {
                    Scanner reader = new Scanner(thefile); // open the file with the Scanner
                    String line=reader.nextLine();//throw away first line

                    while (reader.hasNextLine()  && linecount < MAXLINES){
                        line=reader.nextLine();
                        String values[] = line.split(",");  
                        studentsAdded=0;
                        teachersAdded=0;

                        int studentsJoining = Integer.parseInt(values[1]);
                        for ( studentQueueNumber =0; studentQueueNumber<studentsJoining; studentQueueNumber++){ 
                            Person Student = new Person (values[0], false);  
                            everyoneQueue.joinQ(Student);
                            studentQueueSize++;
                            studentsAdded++;
                        }
                        System.out.println(studentsAdded + " students joined the queue.");

                        int teachersJoining = Integer.parseInt(values[2]);
                        for (teacherQueueNumber =0; teacherQueueNumber<teachersJoining; teacherQueueNumber++){
                            Person Teacher = new Person (values[0], true);  
                            everyoneQueue.joinQ(Teacher);
                            teacherQueueSize++;
                            teachersAdded++;
                        }
                        System.out.println(teachersAdded + " teachers joined the queue.");
                        System.out.println("");
                        int served = Integer.parseInt(values[3]);
                        for (int i =1; i<=served; i++){

                            if (!everyoneQueue.emptyQ()){
                                Person john = everyoneQueue.leaveQ();
                                if (john.teacher){
                                    int timeJoined = Integer.parseInt(john.timeOfArrival());
                                    timeWaiting = linecount - timeJoined + 1;
                                    teacherQueueSize--;
                                    totalteacherQueueSize++;
                                    teacherServed++;
                                    System.out.println("A teacher left the queue, their time in the queue was "+timeWaiting+" minute(s).");
                                    totalTeacherTime = totalTeacherTime+timeWaiting;
                                }
                                else{
                                    int timeJoined = Integer.parseInt(john.timeOfArrival());
                                    timeWaiting = linecount - timeJoined + 1;
                                    studentQueueSize--;
                                    totalStudentQueueSize++;
                                    studentServed++;
                                    System.out.println("A student left the queue, their time in the queue was "+timeWaiting+" minute(s).");
                                    totalStudentTime = totalStudentTime+timeWaiting;
                                }
                            }
                        }
                        CSVlines[linecount]=line;                
                        linecount++;
                        System.out.println("");
                        System.out.println("At "+values[0]+" minute(s), the number of students in the queue is "+studentQueueSize+".");
                        System.out.println("At "+values[0]+" minute(s), the number of teachers in the queue is "+teacherQueueSize+".");
                        System.out.println("");   
                    }

                    for (int i =0; i<linecount; i++){
                        String values[] = CSVlines[i].split(",");  // process the line from the Scanner and break it up at each comma.                                              
                        for (int j=0; j< values.length;j++)
                            AllLinesAllElements[i][j]=values[j];
                    }

                    int averageStudentWait = totalStudentTime/totalStudentQueueSize;
                    int averageTeacherWait = totalTeacherTime/totalteacherQueueSize;
                    int averagePersonWait = (totalStudentTime+totalTeacherTime)/(totalStudentQueueSize+totalteacherQueueSize);
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The average wait time for everyone in the queue was "+averagePersonWait+" minute(s).");
                    System.out.println("");
                    System.out.println("The average wait time for students in the queue was "+averageStudentWait+" minute(s).");
                    System.out.println("The average wait time for teachers in the queue was "+averageTeacherWait+" minute(s).");
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The total number of students served was "+studentServed+".");
                    System.out.println("The total number of teachers served was "+teacherServed+".");
                } catch (IOException e) {System.out.println(e);}

                System.out.println("");
                System.out.println("---------------------------");
                System.out.println("************End************");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();

            }
            else if (choice.equals("q")){
                System.out.println("You have quit the program.");
                keepGoing = false;}
            else{

                System.out.println('\u000c');
                System.out.println("Sorry, that wasn't valid input.");
                System.out.println("");
                System.out.println("If you would like to run a priority queue press p");
                System.out.println("If you would like to run a linear queue press l");
                System.out.println("If you would like to quit the program press q");
                choice = input.nextLine();

            }
        }
    }

    void saveFile(){

    }
}
