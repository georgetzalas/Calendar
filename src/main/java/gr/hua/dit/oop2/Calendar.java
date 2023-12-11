package gr.hua.dit.oop2;

import java.util.ArrayList;

public class Calendar {
    public static void main (String[] args){
        //Stores all the events
        ArrayList<Event> events = new ArrayList();
        icsParse parse = null;
        icsStore store = null;
        eventList list = null;
        eventManagement manage = null;

        //Check if the user provided the right num of args
        if(args.length == 1){
            parse = new icsParse(args[0]);
            store = new icsStore(args[0]);
        }else if(args.length == 2){
            parse = new icsParse(args[1]);
            store = new icsStore(args[1]);
        }else{
            System.out.println("Please provide the right number of arguments");
            System.exit(0);   
        }

        //Check if the file exists
        if(!parse.checkFile()){
            parse.createFile();
        }    

        //Extract all the events from the file and save them into the events List
        parse.extractAll();
        events = parse.getEvents();

        list = new eventList(events);
        manage = new eventManagement(events);

        if(args.length == 1)
            manage.decideAction();
        else
            list.choose(args[0]);

        //Check if the file exists(maybe user deleted it during runtime) 
        if(!store.checkFile()){
            store.createFile();
        }

        //Save all the events back to the ics file
        for(Event event : events){
            if(event.getClass() == Appointements.class){
                store.addAppointment((Appointements)event);            
            }else{
                store.addTodo((Task)event);
            }
        }

        store.storeIcs();
    }
}

