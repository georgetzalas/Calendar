package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.util.Scanner;

public class eventManagement extends userInteraction{
    private Scanner input;
    //private int option;

    public eventManagement(ArrayList<Event> events){ 
        super(events); 
        input = new Scanner(System.in);
    }

    
    public void decideAction(){
        this.printMenu();

        int option = this.getOption(4);

        if(option == 1){
            addCalendar();
        }else if(option == 2){
            editCalendar();
        }else if(option == 3){
            editTodoStatus();
        }else{
            System.exit(0);
        }
    }
    
    private void printMenu(){
        System.out.println("Make your choice,give a number between 1-4 !");
        System.out.println("1.Make a new event to your calendar!");
        System.out.println("2.Make changes to an existing event!");
        System.out.println("3.To change the task-status!");
        System.out.println("4.To exit!");
    }

    private int getOption(int bound){
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

    private void addCalendar(){
        System.out.println("What you want to add");
        System.out.println("1.Events!");
        System.out.println("2.Appointements!");
        System.out.println("3.Tasks!");
        
        int option = this.getOption(3);

        if(option == 1){
            addEvent();
        }else if(option == 2){
            addApointment();
        }else if(option == 3){
            addTodo();
        }
    }

    private void editCalendar(){
        System.out.println("To alter event/task you need to give specific data!"); 
        
        for(Event event : events){
            System.out.println(event.getTitle());
        }
        
        System.out.println("You need to give the title of the event/task you want to update:");

        boolean found = false;
        input.nextLine();
        String tilte = input.nextLine();
        for(Event event : events){
            if(event.getTitle().equals(tilte)){
                //System.out.println(event.getTitle());
                found = true;
                   
                System.out.println("If you don't want to change the day give '0' ,else give the new entrance:");
                Integer day = input.nextInt(); 
                if(!day.equals(0)){
                    event.setStartDay(day);
                }
                
                System.out.println("If you don't want to change the month give '0' ,else give the new entrance:");
                Integer month = input.nextInt();
                if(!month.equals(0)){
                    event.setStartMonth(month);
                }
                
                System.out.println("If you don't want to change the year give '0' ,else give the new entrance:");
                Integer year = input.nextInt();
                if(!year.equals(0)){
                    event.setStartYear(year);
                }
                
                System.out.println("If you don't want to change the hour give '0' ,else give the new entrance:");
                Integer hour = input.nextInt();
                if(!hour.equals(0)){
                    event.setStartHour(hour);
                }
                
                System.out.println("If you don't want to change the minute give '0' ,else give the new entrance:");
                Integer minute = input.nextInt();
                if(!minute.equals(0)){
                    event.setStartMinute(minute);
                }
                
                System.out.println("If you don't want to change the title give '-' ,else give the new entrance:");
                String title = input.nextLine();
                input.nextLine();
                if(title.equals("-")){
                    event.setTitle(title);
                }
                
                System.out.println("If you don't want to change the description give '-' ,else give the new entrance:");
                String desc = input.nextLine();
                if(!desc.equals("-")){
                    event.setDescription(desc);
                }
                
                if(event.getClass() == Appointements.class){
                    System.out.println("If you don't want to change the duration give '0.0',else give the new entrance:");
                    
                    Integer duration = input.nextInt();
                    
                    if(!duration.equals(0)){
                        ((Appointements)event).setDuration(duration);
                    }
                }else{

                
                    System.out.println("If you don't want to change the last-day give '0' ,else give the new entrance:");
                    Integer endday=input.nextInt(); 
                    if(!endday.equals(0)){
                        event.setEndDay(endday);
                    }
                    
                    System.out.println("If you don't want to change the last-month give '0' ,else give the new entrance:");
                    Integer endmonth=input.nextInt();
                    if(!endmonth.equals(0)){
                        event.setEndMonth(endmonth);
                    }

                    System.out.println("If you don't want to change the last-year give '0' ,else give the new entrance:");
                    Integer endyear = input.nextInt();
                    if(!endyear.equals(0)){
                        event.setEndYear(endyear);
                    }
                    
                    System.out.println("If you don't want to change the last-hour give '0' ,else give the new entrance:");
                    Integer endhour = input.nextInt();
                    if(!endhour.equals(0)){
                        event.setEndHour(endhour);
                    }
                    
                    System.out.println("If you don't want to change the last-minute give '0' ,else give the new entrance:");
                    Integer endminute = input.nextInt();
                    if(!endminute.equals(0)){
                        event.setEndMinute(endminute);
                    }
                    //ΕΚΤΥΠΩΣΗ ΤΟΥ ΑΝΑΝΝΕΩΜΕΝΟΥ ΑΝΤΙΚΕΙΜΕΝΟΥ
                    //System.out.println(event);
                    break;
                }
            }
                    
        }
        if(!found){
            System.out.println("Event with this title not found");
        }
    }

    private void addEvent(){
        int day, month, year, hour, minute;
        int endday, endmonth, endyear, endhour, endminute;
        String title, description;

        System.out.println("To enter a new event you need to give specific data!");
        
        System.out.println("Day:");
        day = input.nextInt();
        
        System.out.println("Month:");
        month = input.nextInt();
        
        System.out.println("Year:");
        year = input.nextInt();
        
        System.out.println("Hour:");
        hour = input.nextInt();
        
        System.out.println("Minutes:");
        minute = input.nextInt();
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();

        System.out.println("Last-Day:");
        endday = input.nextInt();
        
        System.out.println("Last-Month:");
        endmonth = input.nextInt();
        
        System.out.println("Last-Year:");
        endyear = input.nextInt();
        
        System.out.println("Last-Hour:");
        endhour = input.nextInt();
        
        System.out.println("Last-Minutes:");
        endminute = input.nextInt();

        events.add(new Event(title, description, day, month, year, hour, minute, 
        endday, endmonth, endyear, endhour, endminute));
    
    }

    private void addApointment(){
        int day, month, year, hour, minute, duration;
        String title, description;

        System.out.println("To enter a new event you need to give specific data!");
        
        System.out.println("Day:"); 
        day = input.nextInt();
        
        System.out.println("Month:");
        month = input.nextInt();
        
        System.out.println("Year:");
        year = input.nextInt();
        
        System.out.println("Hour:");
        hour = input.nextInt();
        
        System.out.println("Minutes:");
        minute = input.nextInt();
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();
        
        System.out.println("Duration:");
        duration = input.nextInt();

        events.add(new Appointements(title, description, day, month, year, hour, minute, 
        0, 0, 0, 0, 0, duration));

    }

    private void addTodo(){
        int day, month, year, hour, minute;
        int endday, endmonth, endyear, endhour, endminute;
        String title, description, status;
 
        System.out.println("To enter a new event you need to give specific data!");
        
        System.out.println("Day:");
        day = input.nextInt();
        
        System.out.println("Month:");
        month = input.nextInt();
        
        System.out.println("Year:");
        year = input.nextInt();
        
        System.out.println("Hour:");
        hour = input.nextInt();
        
        System.out.println("Minutes:");
        minute = input.nextInt();
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();

        System.out.println("Last-Day:");
        endday = input.nextInt();
        
        System.out.println("Last-Month:");
        endmonth = input.nextInt();
        
        System.out.println("Last-Year:");
        endyear = input.nextInt();
        
        System.out.println("Last-Hour:");
        endhour = input.nextInt();
        
        System.out.println("Last-Minutes:");
        endminute = input.nextInt();
        
        System.out.println("To enter a Task you need to give the");
        System.out.println(":Status(YES/NO)");

        status = input.next();
        input.nextLine();
    
        events.add(new Task(title, description, day, month, year, hour, minute, 
        endday, endmonth, endyear, endhour, endminute, status));
    }

    private void edit(){

    }

    private void editTodoStatus(){
        System.out.println("Your Tasks:");
        for(Event event : events){
            if(event instanceof Task){
                Task t = (Task)event;
                System.out.println(t.getTitle() + " " + t.getCompleteTask());
            }
        }

        System.out.println("Give the title of the task you want to change the status!");
        String title = input.next();
        input.nextLine();
        for(Event event : events){
            if(event.getClass() == Task.class && title.equals(event.getTitle())){
                Task t = (Task)event;

                System.out.println("Give the new status(IN-PROGRESS/COMPLETED)!");
                String stat = input.next();
                
                while(!(stat.equals("IN-PROGRESS")|| stat.equals("COMPLETED")) ){
                    System.out.println("Your entrance was wrong ,try again!");
                    stat=input.next();
                }
                
                t.setCompleteTask(stat);
            }
        }
    }

}