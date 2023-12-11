package gr.hua.dit.oop2;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class userInteraction{
    protected ArrayList<Event> events;
    protected Scanner input;
    
    public userInteraction(ArrayList<Event> events){
        this.events = events;
        this.input = new Scanner(System.in);
    }

    //CKECKS IF THE INPUT THAT THE USER GAVE IS BETWEEN VALID NUMBERS
    public int getOption(int base ,int bound){
        int num = -1;
        
        while(num < base || num > bound  ){
        
            while(!input.hasNextInt()){
                System.out.println("Please provide a valid integer");
                input.next();
            }
        
            num = input.nextInt();
            
            if(num < base || num > bound){
                System.out.println("Please provide a number between "+base+"-"+ bound);
            }
        }
        
        return num;
    }
    /* 
    public int ValidInteger(){
    Integer num=-1;
    while(!(input.hasNextInt()&& input.nextInt()>=0)){
                System.out.println("Please provide a valid integer");
                input.next();
            }
    num=input.nextInt();
    return num;    
    }*/
    //CKECKS IF THE INPUT THAT THE USER GAVE IS INTEGER
    public  int ValidInteger() {
        int num;

        do {
            while (!input.hasNextInt()) {
                System.out.println("Please provide a valid integer.");
                input.next(); // consume invalid input
            }
            num = input.nextInt();
            if (num < 0) {
                System.out.println("Please provide a non-negative integer.");
            }
        } while (num < 0);

        return num;
    }

    //RETURNS AN OBJECT LOCALDATETIME
    public LocalDateTime getDateTimeFromUser(int year, int month, int day, int hour, int minute) {
        try {
            return LocalDateTime.of(year,month,day,hour,minute);
        } catch (DateTimeException e) {
            System.out.println("Invalid input. Please enter a valid date and time.");
            return null;
        }
    }
    
}