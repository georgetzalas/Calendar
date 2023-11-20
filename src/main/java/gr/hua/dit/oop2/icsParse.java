package gr.hua.dit.oop2;

import java.io.File;
import biweekly.io.text.ICalReader;
import biweekly.ICalendar; 
import biweekly.component.VEvent;
import biweekly.property.DateStart;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import biweekly.util.ICalDate;
import biweekly.component.VTodo;
import biweekly.property.Status;
import biweekly.property.DateDue;
import biweekly.util.Duration;


import java.io.IOException;
import java.util.ArrayList;

public class icsParse extends icsOperations{
    
    //Represents an iCalendar object.
    private ICalendar ical;
    private ICalReader reader;
    private DateFormat df;
    
    public icsParse(String fileName){
        super(fileName);
        
        //Date format is used to convert ICalDate object into String
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public void extractAll(){
        try{

            Event eve;
            Task task;
            Appointements apo;
            //Parses iCalendar objects from the text file
            reader = new ICalReader(file); 

            //Read all the iCalendar objects from the text file
            while((ical = reader.readNext()) != null){
                
                //For every iCalendar object extract all of its events and get the necessary info
                for(VEvent event : ical.getEvents()){
                    
                    apo  = new Appointements();
                    eve = new Event();
                    
                    //Get current events tilte
                    String title;
                    if(event.getSummary() != null){
                        title = event.getSummary().getValue();
                    }else{
                        title = "";
                    }

                    //Get current events description
                    String desc;
                    if(event.getDescription() != null){
                        desc = event.getDescription().getValue();
                    }else{
                        desc = "";
                    }
      
                    //Get current events starting date
                    ICalDate dateStart;
                    String dateStartStr;
                    if(event.getDateStart() != null){
                        dateStart = event.getDateStart().getValue();
                        dateStartStr = df.format(dateStart);
                    }else{
                        dateStartStr = "";
                    }

                    //Get current events ending date
                    ICalDate dateEnd; 
                    String dateEndStr;
                    if(event.getDateEnd() != null){
                        dateEnd = event.getDateEnd().getValue();
                        dateEndStr = df.format(dateEnd);
                    }else{
                        dateEndStr = "";
                    }

                    Duration duration;
                    //If Duration property is set in VEVENT
                    //that means that the Event is an Appointment 
                    if(event.getDuration() != null){//APPOINTMENT
                        duration = event.getDuration().getValue();
                        
                        apo.setTitle(title);
                        apo.setDescription(desc);
                        apo.strToEventDateStart(dateStartStr);
                        apo.setDuration(duration.getMinutes());
                        events.add(apo);
                    }else{//EVENT
                        eve.setTitle(title);
                        eve.setDescription(desc);
                        eve.strToEventDateStart(dateStartStr);
                        eve.strToEventDateEnd(dateEndStr);
                        events.add(eve);
                    }

                        

                }

                //For every iCalendar object extract all of its todos and get the necessary info
                for(VTodo todo : ical.getTodos()){
                    
                    task = new Task();
                    
                    String title;
                    if(todo.getSummary() != null){
                        title = todo.getSummary().getValue();
                    }else{
                        title = "";
                    }
                    
                    String desc;
                    if(todo.getDescription() != null){
                        desc = todo.getDescription().getValue();
                    }else{
                        desc = "";
                    }
                    
                    ICalDate dueStart = (todo.getDateStart()) == null ? null : todo.getDateStart().getValue();
                    String dueStartStr = df.format(dueStart);

                    ICalDate dueEnd = (todo.getDateDue()) == null ? null : todo.getDateDue().getValue();
                    String dueEndStr = df.format(dueEnd);
              
                    String stat = (todo.getStatus()) == null ? null : todo.getStatus().getValue();
                
                    task.setTitle(title);
                    task.setDescription(desc);
                    task.strToEventDateStart(dueStartStr);
                    task.strToEventDateEnd(dueEndStr);
                    task.setCompleteTask(stat);

                    events.add(task);
                }
            }
      
            //Used to close the file
            reader.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}