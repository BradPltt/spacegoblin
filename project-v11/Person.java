
/**
 * This is the person class. It creates a person when it is asked to.
 * It will create an object for each person.
 *
 * @author Callum Godfrey
 * @version18
 */

public class Person
{
    String name;
    Person follower;
    boolean teacher;
    String timeJoined;
    String timeOfArrival;
    /**
     * 
     */
    public Person(String time, boolean teacher){
        this.teacher = teacher;
        this.timeJoined = time;
    }
   
    /**
     * This function returns the time at which the person joined the queue.
     */
    public String timeOfArrival(){
     return timeJoined;   
    }
    
    /**
     * This function creates a follower for a person (a person lining up behind).
     */
    public void setFollower(Person follower){
        this.follower=follower;  
    }

    /**
     * This function returns the follower of a person (the person lining up behind).
     */
    public Person getFollower(){
        return this.follower;  
    }

    /**
     * This function prints the details of a person (the time joined and the person behind them).
     */
    public void details(){
     System.out.println(timeJoined);
     System.out.println(follower);
    }
}