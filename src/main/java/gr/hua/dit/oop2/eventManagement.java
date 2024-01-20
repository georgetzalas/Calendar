package gr.hua.dit.oop2;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

public class eventManagement extends userInteraction {

    public eventManagement(ArrayList<Event> events) {
        super(events);

    }
    //METHOD THAT RETURN THE DATE INTO A LONG INTEGER
    private long compareDates(int year, int month, int day, int hour, int minute){
        long epoch;
        
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);

        epoch = date.atZone(ZoneId.systemDefault()).toEpochSecond();
        
        return epoch;
    }

    LocalDateTime dateTime = null;
    //METHOD THAT RETURNS THE END-DATE(START-DATE+DURATION)
    public static LocalDateTime plusDure(Integer dayx, Integer monthx, Integer yearx, Integer hourx, Integer minutex,
            Integer Duration) {
        LocalDateTime dt1 = LocalDateTime.of(yearx, monthx, dayx, hourx, minutex);

        System.out.println("LocalDateTime with duration minutes added(end-date): " + dt1.plusMinutes(Duration));
        return dt1.plusMinutes(Duration);
    }
    //METHOD TO MAKE SURE THAT THE OPTION THAT THE USER GAVE IS INTO LIMITS
    public void decideAction() {
        boolean running = true;
        while (running) {
            this.printMenu();

            int option = this.getOption(1, 3);

            if (option == 1) {
                addCalendar();
            } else if (option == 2) {
                editTodoStatus();
            } else {
                running = false;
            }
        }
    }
    //METHOD THAT PRINTS THE MENU
    private void printMenu() {
        System.out.println("Make your choice,give a number between 1-3!");
        System.out.println("1.Make a new event to your calendar!");
        System.out.println("2.To change the task-status!");
        System.out.println("3.To exit!");
    }

    private void addCalendar() {
        //PRINT THE CHOICES TO THE USER
        System.out.println("What you want to add");
        System.out.println("1.Appointements!");
        System.out.println("2.Tasks!");
        //WE TAKE THE OPTION THAT THE USER GAVE
        int option = this.getOption(1, 2);
        if (option == 1) {
            addApointment();
        } else if (option == 2) {
            addTodo();
        }
    }

    String tilte;

    
    //CHOICE OF ADDING APPOINTMENTS
    private void addApointment() {
        //INITIALIZING THE VARIABLES
        int day, month, year, hour, minute, duration=-1;
        String title, description;
        int endday=0, endmonth=0, endyear=0, endhour=0, endminute=0;
        boolean validcomp=false;
        System.out.println("To enter a new event you need to give specific data!");
        do{
        do {
            //USER'S INPUTS
            System.out.println("Day:");
            day = this.ValidInteger();

            System.out.println("Month:");
            month = this.ValidInteger();

            System.out.println("Year:");
            year = this.ValidInteger();

            System.out.println("Hour:");
            hour = this.ValidInteger();

            System.out.println("Minutes:");
            minute = this.ValidInteger();
            dateTime = getDateTimeFromUser(year, month, day, hour, minute);
        } while (dateTime == null);
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();

        System.out.println("Description:");
        description = input.next();
        input.nextLine();
        System.out.println("Duration:give 1 if you want(else if you don't want to give it ,write '0')");
        Integer choiceduration = this.ValidInteger();
        while(!(choiceduration==1 || choiceduration==0)){
            System.out.println("Your input was wrong ,please try again!");
            choiceduration = this.ValidInteger();
        }
        //OPTION:IF USER WANTS TO ADD DURATION
        if (choiceduration == 1) {
            System.out.println("Give the new duration,else give '0':");
            duration = this.ValidInteger();
            //GET THE NEW END-DATE
            LocalDateTime APP = this.plusDure(day,month,year,hour,minute, duration);
            endday = APP.getDayOfMonth();
            endmonth = APP.getMonthValue();
            endyear = APP.getYear();
            endhour = APP.getHour();
            endminute = APP.getMinute();

        }
        //OPTION:IF USER WANTS TO GIVE END-DATE
        if (choiceduration == 0) {
            do {
                //USER INPUTS OF END-DATE
                System.out.println("Please provide the end-date");
                System.out.println("End-Day:");
                endday = this.ValidInteger();

                System.out.println("End-Month:");
                endmonth = this.ValidInteger();

                System.out.println("End-Year:");
                endyear = this.ValidInteger();

                System.out.println("End-Hour:");
                endhour = this.ValidInteger();

                System.out.println("End-Minutes:");
                endminute = this.ValidInteger();
                dateTime = getDateTimeFromUser(endyear, endmonth, endday, endhour, endminute);
            } while (dateTime == null);

            
        }
    //GET THE END-DATE AND START-DATE INTO A LONG INTEGER NUMBER
    long compenddate2=compareDates(endyear,endmonth,endday,endhour,endminute);
    long compstartdate=compareDates(year,month,day,hour,minute);
    //MAKE THE COMPARISON
    if(compstartdate<compenddate2){
        //MAKE THE APPOINTMENT AS SOON AS THE COMPARISON IS CORRECT
        Appointements Ap1 = new Appointements(title, description, day, month, year, hour, minute, endday, endmonth,endyear, endhour, endminute, duration);
            events.add(Ap1);
            // PRINT THE NEW OBJECT
            System.out.printf("%s\t\n", Ap1.getTitle());
            validcomp=true;
    }
    else{
        //ELSE PRINT THE MESSAGE AND GET BACK TO THE TOP OF THE LOOP
        System.out.println("You gave wrong input(enddate<startdate),please try again!");
    }
    }while(validcomp==false);

    }
    //OPTION:OF ADDING TASK
    private void addTodo() {
        //INITIALIZING THE COMPARABLES
        int endday, endmonth, endyear, endhour, endminute;
        String title, description, status;

        System.out.println("To enter a new event you need to give specific data!");
        //TAKE THE USER'S INPUTS
        System.out.println("Title:");
        input.nextLine();
        title = input.nextLine();

        System.out.println("Description:");
        description = input.next();
        input.nextLine();
        do {
            System.out.println("Last-Day:");
            endday = this.ValidInteger();

            System.out.println("Last-Month:");
            endmonth = this.ValidInteger();

            System.out.println("Last-Year:");
            endyear = this.ValidInteger();

            System.out.println("Last-Hour:");
            endhour = this.ValidInteger();

            System.out.println("Last-Minutes:");
            endminute = this.ValidInteger();
            dateTime = getDateTimeFromUser(endyear, endmonth, endday, endhour, endminute);
        } while (dateTime == null);

        status = "IN-PROCESS";
        //MAKING THE NEW OBJECT
        Task Ta1 = new Task(title, description, 0, 0, 0, 0, 0,
                endday, endmonth, endyear, endhour, endminute, status);
        events.add(Ta1);
        // PRINT THE NEW OBJECT
        System.out.printf("%s\t", Ta1.getTitle());
        System.out.println(Ta1);
    }

    //CHOICE:CHANGING THE STATUS OF TASK
    private void editTodoStatus() {
        System.out.println("Your Tasks:");
        //PRINT EVERY TASK OF THE LIST
        for (Event event : events) {
            if (event instanceof Task) {
                Task t = (Task) event;
                System.out.println(t.getTitle() + " " + t.getCompleteTask());
            }
        }
        //SEARCHING BASED TO THE TITLE THAT USER GAVE
        System.out.println("Give the title of the task you want to change the status!");
        input.nextLine();
        String title = input.nextLine();
        //WHEN IT IS FOUND
        for (Event event : events) {
            if (event.getClass() == Task.class && title.equals(event.getTitle())) {
                Task t = (Task) event;
                //THE USER GIVES THE NEW STATUS
                System.out.println("Give the new status(NEEDS-ACTION/IN-PROCESS/CANCELLED/COMPLETED)!");
                String stat = input.nextLine();
                //WHILE THE ENTRANCE IS NOT VALID,THEN USER NEEDS TO GIVE IT AGAIN
                while (!(stat.equals("IN-PROCESS") || stat.equals("COMPLETED") || stat.equals("CANCELLED")
                        || stat.equals("NEEDS-ACTION"))) {
                    System.out.println("Your entrance was wrong ,try again!");
                    stat = input.nextLine();
                }
                //SET THE NEW STATUS
                t.setCompleteTask(stat);
                // PRINT THE RENEWED OBJECT
                System.out.printf("%s\t", event.getTitle());
                System.out.println(event);
            }
        }
    }
}
