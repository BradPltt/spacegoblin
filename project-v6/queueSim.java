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
    /**
     * This function contains the main menu. It also contains all of the main menu functions, and the priorty and standard queue implementations.
     * Depending on the user input, the function can load, quit, run a priority queue, and run a standard queue.
     * 
     * The function loads a file into the program by letting the user assign
     * 
     * The function creates priority queues by creating two seperate queues, one for teachers and one for students.
     */
    public queueSim(){
        Scanner input = new Scanner(System.in);
        System.out.println('\u000c');
        System.out.println("WELCOME to the WHS Canteen lunchtime queue simulation program.");
        System.out.println("");
        System.out.println("What is the name of the file you want to load?");
        System.out.println("");
        System.out.println("Please enter the filename in the format filename.csv");
        System.out.println("");
        System.out.println("The example file is named arrivals.csv");
        filename = input.nextLine();//sets the filename to the user input, and opens the file of the same name as long as it is in the project folder
        File thefile = new File(filename);  // generate the file handle

        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];  // where we keep all those lines we read in.

        boolean keepGoing = true; //this boolean keeps the program running. As long as it remains true, the program will continue

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
            int studentServed = 0;//creates a variable for the number of students served
            int teacherServed = 0;//creates a variable for the number of teachers served
            int timeWaiting=0;//creates a variable for the time spent waiting in queue

            System.out.println('\u000c');//this clears any text from the terminal window

            System.out.println("MENU OPTIONS:");
            System.out.println("Press Q to quit;  Press S to save;");
            System.out.println("");
            System.out.println("QUEUES:");
            System.out.println("Press P to run a priority queue; Press B to run a standard queue;");
            System.out.println("");
            System.out.println("HELP:");
            System.out.println("Press H for help.");
            System.out.println("");
            System.out.println("Make sure that when you press a button to perform an action, it is a capital letter.");
            System.out.println("");
            System.out.println("If any other key is pressed, nothing will happen and this menu will print itelf again.");

            String choice;//creates a string named choice
            choice = input.nextLine();//sets the value of String choice to the user input

            if (choice.equals("H")){
                System.out.println('\u000c');   
                System.out.println("What do you need help with?");
                System.out.println("Press T for MENU OPTIONS.");
                System.out.println("Press Y for QUEUES.");
                System.out.println("");
                System.out.println("Make sure that when you press a button to perform an action, it is a capital letter.");
                choice = input.nextLine();
                if (choice.equals("T")) menuOptionHelp();
                if (choice.equals("Y")) queueHelp();
            }
            else if(choice.equals("P")) { //this runs the following code if the user inputs 'p'
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
                                Person john = teacherQ.leaveQ();//dequeues 
                                teacherQueueSize--;//decreases the current teacher queue size by one
                                totalteacherQueueSize++;//increases the total number of teachers to join the queue over the hour by one
                                teacherServed++;//increases the number of teachers served by one
                                int timeJoined = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount - timeJoined + 1;
                                System.out.println("A teacher left the queue, their wait time was "+timeWaiting+" minute(s).");//prints the time the teacher spent waiting in the queue
                                totalTeacherTime = totalTeacherTime+timeWaiting;//increases the total time spent in queue by all teachers by the waiting time
                            }
                            else{
                                Person john = studentQ.leaveQ();//dequeues
                                studentQueueSize--;//decreases the current student queue size by one
                                totalStudentQueueSize++;//increases the total number of students to join the queue over the hour by one
                                studentServed++;//increases the number of students served by one
                                int timeJoined = Integer.parseInt(john.timeOfArrival());
                                timeWaiting = linecount - timeJoined + 1;
                                System.out.println("A student left the queue, their wait time was "+timeWaiting+" minute(s).");//prints the time the student spent waiting in the queue
                                totalStudentTime = totalStudentTime+timeWaiting;//increases the total time spent in queue by all students by the waiting time
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
                    System.out.println("The total number of students served was "+studentServed+".");//prints the total number of students served over the course of an hour
                    System.out.println("The total number of teachers served was "+teacherServed+".");//prints the total number of teachers served over the course of an hour
                } catch (IOException e) {System.out.println(e);}
                System.out.println("");
                System.out.println("---------------------------");//prints a line to indicate that the queue is finished
                System.out.println("************End************");
                System.out.println("");
                System.out.println("Press S to save the information on your priority queue.");
                System.out.println("");
                System.out.println("Enter any other key to return to the menu.");
                choice = input.nextLine();//Pauses the while loop until the user inputs something
                if (choice.equals("S")) saveFile();//if the user inputs 'S' then the priority queue information will be saved to an external txt file
            }
            else if(choice.equals("B")){//this runs the following code if the user inputs 'l'
                Queue everyoneQueue = new Queue();//creates one queue for both teachers and students to join

                System.out.println('\u000c');//clears any text from the terminal window
                try {
                    Scanner reader = new Scanner(thefile); // open the file with the Scanner
                    String line=reader.nextLine();//throw away first line

                    while (reader.hasNextLine()  && linecount < MAXLINES){
                        line=reader.nextLine();
                        String values[] = line.split(",");  
                        studentsAdded=0;//sets the value of students added to zero
                        teachersAdded=0;//sets the value of teachers added to zero

                        int studentsJoining = Integer.parseInt(values[1]);//creates a variable for students joining the queue
                        for ( studentQueueNumber =0; studentQueueNumber<studentsJoining; studentQueueNumber++){ 
                            Person Student = new Person (values[0], false);//creates a new person, the person is a student
                            everyoneQueue.joinQ(Student);//enques the new student into the standard queue
                            studentQueueSize++;//increases the student queue size by one
                            studentsAdded++;//increases the value of students added by one
                        }
                        System.out.println(studentsAdded + " students joined the queue.");//prints the number of students joining the queue every minute

                        int teachersJoining = Integer.parseInt(values[2]);//creates a variable for teachers joining the queue
                        for (teacherQueueNumber =0; teacherQueueNumber<teachersJoining; teacherQueueNumber++){
                            Person Teacher = new Person (values[0], true);//creates a new person, the person is a teacher  
                            everyoneQueue.joinQ(Teacher);//enqueues the new teacher into the standard queue
                            teacherQueueSize++;//increases the teacher queue size by one
                            teachersAdded++;//increases the value of teachers added by one
                        }
                        System.out.println(teachersAdded + " teachers joined the queue.");//prints the number of teachers joining the queue every minute
                        System.out.println("");
                        int served = Integer.parseInt(values[3]);
                        for (int i =1; i<=served; i++){

                            if (!everyoneQueue.emptyQ()){
                                Person john = everyoneQueue.leaveQ();//dequeues person from the standard queue
                                if (john.teacher){
                                    teacherQueueSize--;//decreases the current teacher queue size by one
                                    totalteacherQueueSize++;//increaes the total number of teachers to join the queue over an hour by one
                                    teacherServed++;//increases the number of teachers served by one
                                    int timeJoined = Integer.parseInt(john.timeOfArrival());
                                    timeWaiting = linecount - timeJoined + 1;
                                    System.out.println("A teacher left the queue, their time in the queue was "+timeWaiting+" minute(s).");//prints the time the teacher spent waiting in the queue
                                    totalTeacherTime = totalTeacherTime+timeWaiting;//increases the total time spent in queue by all students by the time spent waiting
                                }
                                else{ 
                                    studentQueueSize--;//decreases the current student queue size by one
                                    totalStudentQueueSize++;//increases the total number of students to join the queue over the hour by one
                                    studentServed++;//increases the number of students served by one
                                    int timeJoined = Integer.parseInt(john.timeOfArrival());
                                    timeWaiting = linecount - timeJoined + 1;
                                    System.out.println("A student left the queue, their time in the queue was "+timeWaiting+" minute(s).");//prints the time the student spent waiting in the queue
                                    totalStudentTime = totalStudentTime+timeWaiting;//increases the total time spent in queue by all teachers by the time spent waiting
                                }
                            }
                        }
                        CSVlines[linecount]=line;                
                        linecount++;//increases the linecount by one
                        System.out.println("");
                        System.out.println("At "+values[0]+" minute(s), the number of students in the queue is "+studentQueueSize+".");//prints the number of students in the queue at each minute
                        System.out.println("At "+values[0]+" minute(s), the number of teachers in the queue is "+teacherQueueSize+".");//prints the number of teachers in the queue at each minute
                        System.out.println("");   
                    }

                    for (int i =0; i<linecount; i++){
                        String values[] = CSVlines[i].split(",");  // process the line from the Scanner and break it up at each comma.                                              
                        for (int j=0; j< values.length;j++)
                            AllLinesAllElements[i][j]=values[j];
                    }

                    int averageStudentWait = totalStudentTime/totalStudentQueueSize;//creates a variable for the average student wait time, makes it equal to the total student time in queue divided by the total student queue size
                    int averageTeacherWait = totalTeacherTime/totalteacherQueueSize;//creates a variable for the average teacher wait time, makes it equal to the total teacher time in queue divided by the total teacher queue size
                    int averagePersonWait = (totalStudentTime+totalTeacherTime)/(totalStudentQueueSize+totalteacherQueueSize);//creates a variable for the average person wait time, makes it equal to the total time spent in queue by everyone divided by the total queue size
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The average wait time for everyone in the queue was "+averagePersonWait+" minute(s).");//prints the average wait time for everyone in the queue
                    System.out.println("");
                    System.out.println("The average wait time for students in the queue was "+averageStudentWait+" minute(s).");//prints the average wait time for just students in the queue
                    System.out.println("The average wait time for teachers in the queue was "+averageTeacherWait+" minute(s).");//prints the average wait time for just teachers in the queue
                    System.out.println("");
                    System.out.println("---------------------------");
                    System.out.println("");
                    System.out.println("The total number of students served was "+studentServed+".");//prints the total number of students served
                    System.out.println("The total number of teachers served was "+teacherServed+".");//prints the total number of teachers served
                } catch (IOException e) {System.out.println(e);}

                System.out.println("");
                System.out.println("---------------------------");
                System.out.println("************End************");
                System.out.println("");
                System.out.println("Press S to save the information on your standard queue.");
                System.out.println("");
                System.out.println("Enter any other key to return to the menu.");
                choice = input.nextLine();//pauses the while loop until the user inputs something
                if (choice.equals("S")) saveFile();//if the user inputs 'S' then the standard queue information will be saved to an external txt file

            }
            else if (choice.equals("Q")){
                System.out.println("You have quit the program.");
                keepGoing = false;}//force stops the program
            else{

                System.out.println('\u000c');
                System.out.println("Sorry, that wasn't valid input.");
                System.out.println("");
                System.out.println("Enter any key to return to the menu.");
                choice = input.nextLine();

            }
        }
    }

    /**
     * This function acts as a sub menu for the help menu. It covers the MENU OPTIONS, what they do, and how to use them.
     * The function works by printing the information that the user has requested, and then using the code input.nextLine(); to pause the while loop of the main menu
     * so that it doesnt instantly print the main menu again.
     */
    public static void menuOptionHelp(){
        Scanner input = new Scanner(System.in);//creates a new scanner named input
        String choice;//creates a new string named choice
        System.out.println('\u000c');
        System.out.println("LOAD:");
        System.out.println("The load function allows you to load a csv file into the program.");
        System.out.println("From there you can run either a basic queue simulation or a priority queue simulation on the data in the file.");
        System.out.println("");
        System.out.println("SAVE:");
        System.out.println("The save function allows you to save information on any queue simulation to an external txt file.");
        System.out.println("The new txt file can be accessed from this programs folder on your computer.");
        System.out.println("");
        System.out.println("QUIT:");
        System.out.println("The quit function forces the program to stop running, and allows you to hit the red X button in the top of your screen to close the program.");
        System.out.println("It can be used at any point in time, no matter whether or not you have done anything yet.");
        System.out.println("");
        System.out.println("To return to the main menu, enter any key.");
        System.out.println("");
        choice = input.nextLine();//This pauses the while loop of the main menu until the user inputs something
    }

    /**
     * This function acts as a sub menu for the help menu. It covers the QUEUEs, and what they do.
     * This function works by printing the information that the user has requested, and then using the code input.nextLine(); to pause the while loop of the main menu so that 
     * it doesn't instantly print the main menu again.
     */
    public static void queueHelp(){
        Scanner input = new Scanner(System.in);//creates a new scanner named input
        String choice;//creates a new string named choice
        System.out.println('\u000c');
        System.out.println("PRIORITY QUEUE:");
        System.out.println("A priority queue is when teachers get to skip the line and join the queue at the front.");
        System.out.println("");
        System.out.println("STANDARD QUEUE:");
        System.out.println("A standard queue is when teachers have to join the queue at the back, and wait like the students would.");
        System.out.println("");
        System.out.println("To return to the main menu, enter any key.");
        System.out.println("");
        choice=input.nextLine();//This pauses the while loop of the main menu until the user inputs something
    }

    void saveFile(){

    }
}
