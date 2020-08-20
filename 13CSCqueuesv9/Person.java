import java.lang.Math;
import java.util.Random;
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
    int timeOfArrival;
    boolean statusTeacher;
    boolean pCash;
    String time;
    String timeJoined;
    public Person(){
        this.name="Anonymous";   
        if(Math.random() > 0.3){
            statusTeacher = false;
        }else{
            statusTeacher = true;
        }
        if(Math.random() > 0.5){
         pCash = true;   
        } else {
         pCash = false;  
        }
    }
    
    public Person(String name){
        this.name=name;   
    }

    public void setName(String name){
        this.name=name;   
    }
    
    public void setFollower(Person follower){
        this.follower=follower;   
    }

    public Person getFollower(){
        return this.follower;   
    }
    
    public String timeJoined(){
        String timeJoined = time;
        return timeJoined;
    }
    
    public void details(){
     System.out.println(timeJoined);
     System.out.println(follower);
        
    }
}
