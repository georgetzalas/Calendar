package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.util.Scanner;

public class eventManagement extends userInteraction{

    public eventManagement(ArrayList<Event> events){ 
        super(events); 
        
    }
    
    public void decideAction(){
        boolean running = true;
        while(running){
            this.printMenu();

            int option = this.getOption(4);

            if(option == 1){
                addCalendar();
            }else if(option == 2){
                editCalendar();
            }else if(option == 3){
                editTodoStatus();
            }else{
                running = false;
            }
        }
    }
    
    private void printMenu(){
        System.out.println("Make your choice,give a number between 1-4 !");
        System.out.println("1.Make a new event to your calendar!");
        System.out.println("2.Make changes to an existing event!");
        System.out.println("3.To change the task-status!");
        System.out.println("4.To exit!");
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
                Integer day = this.getOption(31);
                
                if(!day.equals(0)){
                    event.setStartDay(day);
                }
                
                System.out.println("If you don't want to change the month give '0' ,else give the new entrance:");
                Integer month = this.getOption(12);
                if(!month.equals(0)){
                    event.setStartMonth(month);
                }
                
                System.out.println("If you don't want to change the year give '0' ,else give the new entrance:");
                Integer year = this.getOption(2030);
                if(!year.equals(0)){
                    event.setStartYear(year);
                }
                
                System.out.println("If you don't want to change the hour give '0' ,else give the new entrance:");
                Integer hour = this.getOption(24);
                if(!hour.equals(0)){
                    event.setStartHour(hour);
                }
                
                System.out.println("If you don't want to change the minute give '0' ,else give the new entrance:");
                Integer minute = this.getOption(60);
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
                    Integer endday=this.getOption(31); 
                    if(!endday.equals(0)){
                        event.setEndDay(endday);
                    }
                    
                    System.out.println("If you don't want to change the last-month give '0' ,else give the new entrance:");
                    Integer endmonth=this.getOption(12);
                    if(!endmonth.equals(0)){
                        event.setEndMonth(endmonth);
                    }

                    System.out.println("If you don't want to change the last-year give '0' ,else give the new entrance:");
                    Integer endyear = this.getOption(2030);
                    if(!endyear.equals(0)){
                        event.setEndYear(endyear);
                    }
                    
                    System.out.println("If you don't want to change the last-hour give '0' ,else give the new entrance:");
                    Integer endhour = this.getOption(24);
                    if(!endhour.equals(0)){
                        event.setEndHour(endhour);
                    }
                    
                    System.out.println("If you don't want to change the last-minute give '0' ,else give the new entrance:");
                    Integer endminute = this.getOption(60);

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
        day = this.getOption(31);
        
        System.out.println("Month:");
        month = this.getOption(12);
        
        System.out.println("Year:");
        year = this.getOption(2030);
        
        System.out.println("Hour:");
        hour = this.getOption(24);
        
        System.out.println("Minutes:");
        minute = this.getOption(60);
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();

        System.out.println("Last-Day:");
        endday = this.getOption(31);
        
        System.out.println("Last-Month:");
        endmonth = this.getOption(12);
        
        System.out.println("Last-Year:");
        endyear = this.getOption(12);
        
        System.out.println("Last-Hour:");
        endhour = this.getOption(24);
        
        System.out.println("Last-Minutes:");
        endminute = this.getOption(60);

        events.add(new Event(title, description, day, month, year, hour, minute, 
        endday, endmonth, endyear, endhour, endminute));
    
    }

    private void addApointment(){
        int day, month, year, hour, minute, duration;
        String title, description;

        System.out.println("To enter a new event you need to give specific data!");
        
        System.out.println("Day:"); 
        day = this.getOption(31);
        
        System.out.println("Month:");
        month = this.getOption(12);
        
        System.out.println("Year:");
        year = this.getOption(2030);
        
        System.out.println("Hour:");
        hour = this.getOption(24);
        
        System.out.println("Minutes:");
        minute = this.getOption(60);
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();
        
        System.out.println("Duration:");
        duration = this.getOption(460);

        events.add(new Appointements(title, description, day, month, year, hour, minute, 
        0, 0, 0, 0, 0, duration));

    }

    private void addTodo(){
        int day, month, year, hour, minute;
        int endday, endmonth, endyear, endhour, endminute;
        String title, description, status;
 
        System.out.println("To enter a new event you need to give specific data!");
        
        System.out.println("Day:");
        day = this.getOption(31);
        
        System.out.println("Month:");
        month = this.getOption(12);
        
        System.out.println("Year:");
        year = this.getOption(2030);
        
        System.out.println("Hour:");
        hour = this.getOption(24);
        
        System.out.println("Minutes:");
        minute = this.getOption(60);
        
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();
        
        System.out.println("Description:");
        description = input.next();
        input.nextLine();

        System.out.println("Last-Day:");
        endday = this.getOption(31);
        
        System.out.println("Last-Month:");
        endmonth = this.getOption(12);
        
        System.out.println("Last-Year:");
        endyear = this.getOption(2030);
        
        System.out.println("Last-Hour:");
        endhour = this.getOption(24);
        
        System.out.println("Last-Minutes:");
        endminute = this.getOption(60);
        
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
	
