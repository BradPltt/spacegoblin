/**
 * This is class that allows for the creation of a queue.
 * People can be added and removed from the queue at will.
 *
 * @author Callum Godfrey
 * 
 * @version 18
 */
public class Queue
{
    Person front;
    Person back;
      
   
    /**
     * This function sets the front and back of the newly created queue to null (an empty queue).
     */
    public Queue(){
        this.front=null;
        this.back=null;
    }

    /**
     * This function checks whether or not the queue is empty.
     */
    public boolean emptyQ(){
        if(front==null)return true;
        return false;
    }

    /**
     * This function adds a new person to the back of the queue.
     */
    public void joinQ(Person name){
        if(emptyQ()==true){
            this.front=name;
            this.back=name;
        }
        else{
            this.back.setFollower(name);
            this.back=name;
        }
    }

     /**
      * This function removes a person from the front of the queue.
      */
     public Person leaveQ(){
        Person remember;
        remember=this.front;
        this.front=this.front.getFollower();
        return remember;
    }

    /**
     * This function prints the queue in whatever state at the time of printing. 
     */
    public void printQ(){
        System.out.println('\u000c');
        System.out.println("This is the current state of the queue from front to back:");
        Person current=front;
        while(current!=null){
            System.out.println(current.name);
            current.details();
            current=current.follower;
        }
    }
}


