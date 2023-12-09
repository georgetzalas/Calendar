package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.util.Scanner;

public class userInteraction{
    protected ArrayList<Event> events;
    protected Scanner input;
    
    public userInteraction(ArrayList<Event> events){
        this.events = events;
        this.input = new Scanner(System.in);
    }

    public int getOption(int bound){
        int num = -1;
        
        while(num < 1 || num > bound){
        
            while(!input.hasNextInt()){
                System.out.println("Please provide a valid integer");
                input.next();
            }
        
            num = input.nextInt();
            
            if(num < 1 || num > bound){
                System.out.println("Please provide a number between 1 - " + bound);
            }
        }
        
        return num;
    }
    


}