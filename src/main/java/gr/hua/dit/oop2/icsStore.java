package gr.hua.dit.oop2;

import biweekly.io.text.ICalReader;
import biweekly.ICalendar; 
import biweekly.component.VEvent;
import biweekly.component.VTodo;
import biweekly.property.Status;
import biweekly.ICalVersion;
import biweekly.Biweekly; 
import biweekly.util.Duration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.io.File;

import java.io.IOException;

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
    
    public void addEvent(Event e){
        
        //Create a new VEvent component
        event = new VEvent();

        //Set properties to the component
        event.setSummary(e.getTitle());
        event.setDescription(e.getDescription());


        localDateTime = LocalDateTime.of(e.getStartYear(), e.getStartMonth(), e.getStartDay(), e.getStartHour(), e.getStartMinute());
        startDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        event.setDateStart(startDate);
        
        localDateTime = LocalDateTime.of(e.getEndYear(), e.getEndMonth(), e.getEndDay(), e.getEndHour(), e.getEndMinute());
        endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        event.setDateEnd(endDate);
        
        //Add component to the iCalendar object
        ical.addEvent(event);
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
        
        //localDateTime = LocalDateTime.of(e.getEndYear(), e.getEndMonth(), e.getEndDay(), e.getEndHour(), e.getEndMinute());
        //endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        
        Duration duration = Duration.builder().minutes(e.getDuration()).build();
        event.setDuration(duration);
        
        //Add component to the iCalendar object
        ical.addEvent(event);
        
    }


    public void addTodo(Task t){
        //Create a new VTodo component
        todo = new VTodo();

        //Set properties to the component
        todo.setSummary(t.getTitle());
        todo.setDescription(t.getDescription());
        
        
        todo.setDateStart(new Date(t.getStartYear(), t.getStartMonth(), t.getStartDay(), t.getStartHour(), t.getStartMinute()));
        todo.setDateDue(new Date(t.getEndYear(), t.getEndMonth(), t.getEndDay(), t.getEndHour(), t.getEndMinute()));

        if(t.getCompleteTask().equals("COMPLETED")){
            todo.setStatus(Status.completed());
        }else if(t.getCompleteTask().equals("IN-PROGRESS")){
            todo.setStatus(Status.inProgress());
        }

        //Add component to the iCalendar object
        ical.addTodo(todo);
    }

    public void storeIcs(){
        try{
            //Write iCalendar object to the .ics file
            Biweekly.write(ical).go(file);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}