import java.lang.Math;
import java.util.Random;
/**
 * Write a description of class simulation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class simulation
{   int time = 0;
    boolean served;
    int peopleServed;
    Person person;
    int i;
    Queue canteen;
    public simulation(){
        canteen = new Queue();
        while(time<60){
            if(time<10){
            if(Math.random() > 0.3){
                for (i = 0; i < 6; i++)
                {
                    Person person = new Person();
                    canteen.joinQ(person);
                    i++;
                } }
            else {
                for (i = 0; i < 7; i++){
                    Person person = new Person();
                    canteen.joinQ(person);
                    i++;
                }
            }
        }
        if (time>=10){
            if(Math.random() > 0.3){
                for (i = 0; i < 3; i++)
                {
                    Person person = new Person();
                    canteen.joinQ(person);
                    i++;
                } }
            else {
                for (i = 0; i < 4; i++){
                    Person person = new Person();
                    canteen.joinQ(person);
                    i++;
                }
            }
        }

            if(Math.random() > 0.8 ){
                peopleServed = peopleServed + 5;
            } else {
                peopleServed = peopleServed + 4;   
            }
            time++;
            
        }
        canteen.printQ();
    }
    
}

