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
        
        extractAll();
    }

    private void componentError(){
        System.out.println("Something went wrong with the file structure");
        System.exit(1);
    }



    public void extractAll(){
        try{
            Task task;
            Appointements apo;
            
            //Parses iCalendar objects from the text file
            reader = new ICalReader(file); 

            //Read all the iCalendar objects from the text file
            while((ical = reader.readNext()) != null){
                
                //For every iCalendar object extract all of its events and get the necessary info
                for(VEvent event : ical.getEvents()){
                    
                    apo  = new Appointements();
                    
                    //Get current events tilte
                    String title = null;
                    if(event.getSummary() != null){
                        title = event.getSummary().getValue();
                    }else{
                        componentError();
                    }

                    //Get current events description
                    String desc = null;
                    if(event.getDescription() != null){
                        desc = event.getDescription().getValue();
                    }else{
                        componentError();
                    }
      
                    //Get current events starting date
                    ICalDate dateStart = null;
                    String dateStartStr = null;
                    if(event.getDateStart() != null){
                        dateStart = event.getDateStart().getValue();
                        dateStartStr = df.format(dateStart);
                    }else{
                        componentError();
                    }


                    //If duration exists
                
                    Duration duration;
                    if(event.getDuration() != null){
                        duration = event.getDuration().getValue();
                        apo.setDuration(duration.getMinutes());
                    }else{
                        apo.setDuration(-1);
                            
                        //Get current events ending date
                        ICalDate dateEnd = null; 
                        String dateEndStr = null;
                        if(event.getDateEnd() != null){
                            dateEnd = event.getDateEnd().getValue();
                            dateEndStr = df.format(dateEnd);
                        }else{
                            componentError();
                        }
                        
                        apo.strToEventDateEnd(dateEndStr);
                    }
               
                    apo.setTitle(title);
                    apo.setDescription(desc);
                    apo.strToEventDateStart(dateStartStr);
                    
                    events.add(apo);
                }

                //For every iCalendar object extract all of its todos and get the necessary info
                for(VTodo todo : ical.getTodos()){
                    
                    task = new Task();
                    
                    String title = null;
                    if(todo.getSummary() != null){
                        title = todo.getSummary().getValue();
                    }else{
                        componentError();
                    }
                    
                    String desc = null;
                    if(todo.getDescription() != null){
                        desc = todo.getDescription().getValue();
                    }else{
                        componentError();
                    }
                    
                    ICalDate due = null;
                    String dueStr = null;
                    if(todo.getDateDue() != null){
                        due = todo.getDateDue().getValue();
                        dueStr = df.format(due);
                    }else{
                        componentError();
                    }
              
                    String stat;
                    if(todo.getStatus() != null){
                        stat = todo.getStatus().getValue();
                    }else{
                        stat = "IN-PROCESS";
                    }
                    
                    task.setTitle(title);
                    task.setDescription(desc);
                    task.strToEventDateEnd(dueStr);
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