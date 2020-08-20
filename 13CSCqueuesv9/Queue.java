
/**
 * Write a description of class Queue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Queue
{
    Person front;
    Person pBack;
    Person back;
    
    private Queue q;
    
    
    public Queue(){
        this.front=null;
        this.back=null;
    }

    public boolean emptyQ(){
        if(front==null)return true;
        return false;
    }

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

     public Person leaveQ(){
        Person remember;
        remember=this.front;
        this.front=this.front.getFollower();
        return remember;
    }

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

