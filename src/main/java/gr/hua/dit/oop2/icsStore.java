package gr.hua.dit.oop2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.component.VTodo;
import biweekly.io.text.ICalReader;
import biweekly.property.Status;
import biweekly.util.Duration;

public class icsStore extends icsOperations{
    
    //Represents an iCalendar object.
    private ICalendar ical;
    private ICalReader reader;
    private VEvent event;
    private VTodo todo;

    private LocalDateTime localDateTime;
    private Date startDate;
    private Date endDate;

    public icsStore(String fileName){
        super(fileName);
        ical = new ICalendar();
    }
    
    public void addAppointment(Appointements e){
        
        //Create a new VEvent component
        event = new VEvent();

        //Set properties to the component
        event.setSummary(e.getTitle());
        event.setDescription(e.getDescription());

        localDateTime = LocalDateTime.of(e.getStartYear(), e.getStartMonth(), e.getStartDay(), e.getStartHour(), e.getStartMinute());
        startDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        event.setDateStart(startDate);
        
        if(e.getDuration() == -1){
            localDateTime = LocalDateTime.of(e.getEndYear(), e.getEndMonth(), e.getEndDay(), e.getEndHour(), e.getEndMinute());
            endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            event.setDateEnd(endDate);
        }else{
            Duration duration = Duration.builder().minutes(e.getDuration()).build();
            event.setDuration(duration);
        }
        
        //Add component to the iCalendar object
        ical.addEvent(event);
    }


    public void addTodo(Task t){
        //Create a new VTodo component
        todo = new VTodo();

        //Set properties to the component
        todo.setSummary(t.getTitle());
        todo.setDescription(t.getDescription());
        
        localDateTime = LocalDateTime.of(t.getEndYear(), t.getEndMonth(), t.getEndDay(), t.getEndHour(), t.getEndMinute());
        endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        todo.setDateDue(endDate);

        if(t.getCompleteTask().equals("COMPLETED")){
            todo.setStatus(Status.create("COMPLETED"));
        }else if(t.getCompleteTask().equals("NEEDS-ACTION")){
            todo.setStatus(Status.create("NEEDS-ACTION"));
        }else if(t.getCompleteTask().equals("IN-PROCESS")){
            todo.setStatus(Status.create("IN-PROCESS"));
        }else if(t.getCompleteTask().equals("CANCELLED")){
            todo.setStatus(Status.create("CANCELLED"));
        }

        //Add component to the iCalendar object
        ical.addTodo(todo);
    }

    public void storeIcs(){
        ical = new ICalendar();
        try{
            for(Event event : events){
                System.out.println("HELLO");
                if(event.getClass() == Appointements.class){
                    if(event instanceof Appointements)
                        addAppointment((Appointements)event);            
                }else{
                    if(event instanceof Task)
                        addTodo((Task)event);
                }
            }

            //Write iCalendar object to the .ics file
            Biweekly.write(ical).go(file);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}