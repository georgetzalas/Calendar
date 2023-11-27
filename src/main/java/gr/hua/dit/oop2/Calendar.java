package gr.hua.dit.oop2;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Thread;

import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import gr.hua.dit.oop2.calendar.TimeListener;
import gr.hua.dit.oop2.calendar.TimeEvent;

public class Calendar {
    public static void main (String[] args){
        ArrayList<Event> events;
        icsParse parse = new icsParse("greece.ics");
        icsStore store = new icsStore("greece.ics");
        eventList interaction;
        

        if(!parse.checkFile()){
            parse.createFile();
        }    

        parse.extractAll();
        events = parse.getEvents();

        interaction = new eventList();
 
        interaction.sort(events);
        interaction.showEventsFromNow(events);

        
        
        if(!store.checkFile()){
            store.createFile();
        }

        for(Event event : events){
            if(event.getClass() == Event.class){
                store.addEvent(event);
            }else if(event.getClass() == Appointements.class){
                store.addAppointment((Appointements)event);            
            }else{
                store.addTodo((Task)event);
            }
        }

        store.storeIcs();
    }
}

