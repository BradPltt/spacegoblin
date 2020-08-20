
/**
 * This is the person class. It creates a person when it is asked to.
 * It will create an object for each person, and provide it with a name, status, and payment method.
 *
 * @author Callum Godfrey
 * @version5
 */

public class Person
{
    String name;
    Person follower;
    boolean statusTeacher;
    String timeJoined;
    String timeOfArrival;
    public Person(String time, boolean statusTeacher){
        this.statusTeacher = statusTeacher;
        this.timeJoined = time;
    }
   
    public String timeOfArrival(){
     return timeJoined;   
    }
    
    public void setFollower(Person follower){
        this.follower=follower;  
    }

    public Person getFollower(){
        return this.follower;  
    }

    public void details(){
     System.out.println(timeJoined);
     System.out.println(follower);
       
    }
}