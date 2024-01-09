package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

public class eventList extends userInteraction{

    private ArrayList<String> data;

    public eventList(ArrayList<Event> events){
        super(events);
        data = new ArrayList();
    }
    

    //USER CHOOSES WHAT EVENTS HE WANTS TO SEE
    public void choose(String action){

        System.out.println("All the upcoming events sorted");

        
        this.sort();
        this.showEventsFromNow();
        
        if(action.equals("day")){
            //CALLS THE METHOD FOR THE EVENTS BY THE END OF THIS DAY
            this.day();
        }else if(action.equals("week")){
            //CALLS THE METHOD FOR THE EVENTS BY THE END OF THIS WEEK
            this.week();
        }else if(action.equals("month")){
            //CALLS THE METHOD FOR THE EVENTS BY THE END OF THIS MONTH
            this.month();
        }else if(action.equals("pastday")){
            //CALLS THE METHOD FOR THE EVENTS FROM THE BEGINNING OF THE DAY UNTIL NOW
            this.pastday();
        }else if(action.equals("pastweek")){
            //CALLS THE METHOD FOR THE EVENTS FROM THE BEGINNING OF THIS WEEK UNTIL NOW
            this.pastweek();
        }else if(action.equals("pastmonth")){
            //CALLS THE METHOD FOR THE EVENTS FROM THE BEGINNING OF THIS MONTH UNTIL NOW
            this.pastmonth();
        }else if(action.equals("todo")){
            //CALLS THE METHOD FOR THE TASKS THAT WERE NOT COMPLETED AND THE DEADLINE HAS NOT PASSED
            this.todo();
        }else if(action.equals("due")){
            //CALLS THE METHOD FOR THE TASKS THAT WERE NOT COMPELTED AND THE DEADLINE HAS PASSED
            this.due();
        }else{
            //DEFAULT ENTRY
            System.out.println("Please provide a valid action");
            System.exit(0);
        }
    }
    //METHOD THAT SORTS EVENTS BASED BY THE DATE AND TIME, NEAREST TO CURRENT DATE-TIME
    private void sort(){
        for (int i = 0; i < events.size() - 1; i++){
            for (int j = 0; j < events.size() - i - 1; j++) {
                if(events.get(j).compareTo(events.get(j + 1)) > 0){
                        swap(j, j+1);
                }
            }
        }
    }

    //METHOD THAT FINDS THE EVENTS BY THE END OF THE DAY
    public void day(){
        //THE END OF THE DAY
        LocalDateTime endOfDay = getCurrentTime().toLocalDate().atTime(LocalTime.MAX);
        //CONVERTS THE TIME END OF THE DAY TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = endOfDay.toEpochSecond(ZoneOffset.UTC);

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();

            //IF THE TIME OF THE EVENT IS BIGGER THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND LESS THAN THE END OF THE DAY (epoch)
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }
    }

    //METHOD THAT FINDS THE EVENTS FROM THE BEGINNING OF THE DAY UNTIL NOW 
    public void pastday(){
        //THE START OF THE DAY
        LocalDateTime endOfDay = getCurrentTime().toLocalDate().atTime(LocalTime.MIN);
        //CONVERTS THE TIME START OF THE DAY TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = endOfDay.toEpochSecond(ZoneOffset.UTC);

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            //IF THE TIME OF THE EVENT IS LESS THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND BIGGER THAN OR EQUAL THAN THE BEGINNING OF THE DAY (epoch)
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }
    }

    //METHOD THAT FINDS THE EVENTS BY THE END OF THE WEEK
    public void week(){
        //THE END OF THE WEEK
        LocalDateTime endOfWeek = getCurrentTime().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23).withMinute(59).withSecond(59);
        //CONVERTS THE TIME END OF THE WEEK TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = endOfWeek.toEpochSecond(ZoneOffset.UTC);

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            //IF THE TIME OF THE EVENT IS BIGGER THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND LESS THAN THE END OF THE WEEK (epoch)
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }
        
    }

    //METHOD THAT FINDS THE EVENTS FROM THE BEGINNING OF THE WEEK UNTIL NOW
    public void pastweek(){
        //THE START OF THE WEEK
        LocalDateTime startOfWeek = getCurrentTime().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        //CONVERTS THE TIME START OF THE WEEK TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = startOfWeek.toInstant(ZoneOffset.UTC).getEpochSecond();
        
        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            //IF THE TIME OF THE EVENT IS LESS THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND BIGGER THAN OR EQUAL THAN THE BEGINNING OF THE WEEK (epoch)
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }
    
    }

    //METHOD THAT FINDS THE EVENTS BY THE END OF THE MONTH
    public void month(){
        //THE END OF THE MONTH
        LocalDateTime endOfMonth = getCurrentTime().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        //CONVERTS THE TIME END OF THE MONTH TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = endOfMonth.toEpochSecond(ZoneOffset.UTC);
        
        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            //IF THE TIME OF THE EVENT IS BIGGER THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND LESS THAN THE END OF THE MONTH (epoch)
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }   
    }

    //METHOD THAT FINDS THE EVENTS FROM THE BEGINNING OF THE MONTH UNTIL NOW
    public void pastmonth(){
        //THE START OF THE MONTH
        LocalDateTime startOfMonth = getCurrentTime().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        //CONVERTS THE TIME START OF THE MONTH TO A NUMBER OF SECONDS FROM THE EPOCH TO ZERO
        long epoch = startOfMonth.toInstant(ZoneOffset.UTC).getEpochSecond();

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
             //IF THE TIME OF THE EVENT IS LESS THAN THE TIME THAT HAS PASSED SINCE THE SEASON AND BIGGER THAN OR EQUAL THAN THE BEGINNING OF THE MONTH (epoch)
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                    data.add(e.getTitle() + " " + e.toString());
                }
            } 
        }
    }
    //METHOD THAT FINDS THE TASKS THAT WERE NOT COMPLETED AND THE DEADLINE HAS NOT PASSED
    public void todo(){
        
        for(Event t : events){
            if(t.getClass() == Task.class){
                //CALCULATES ITS TIME IN SECONDS FROM THE EPOCH
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                //IF THE TASK IS WITHIN ITS DEADLINE AND HAS NOT BEEN COMPELETED
                if(e.calculateEpoch() >= timeSinceEpoch && !e.getCompleteTask().equals("COMPLETED")){
                    data.add(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    //METHOD THAT FINDS THE TASKS THAT WERE NOT COMPELTED AND THE DEADLINE HAS PASSED
    public void due(){
        
        for(Event t : events){
            if(t.getClass() == Task.class){
                //CALCULATES ITS TIME IN SECONDS FROM THE EPOCH
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                //IF THE TASK HAS EXCEEDED ITS DEADLINE AND HAS NOT BEEN COMPLETED
                if(e.calculateEpoch() < timeSinceEpoch && !e.getCompleteTask().equals("COMPLETED")){
                    data.add(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    //METHOD THAT PRINTS THE FUTURE EVENTS 
    public void showEventsFromNow(){
        for(Event e : events){
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(e.calculateEpoch() >= timeSinceEpoch){
                data.add(e.getTitle() + " " + e.toString());
            }
        }
    }

    //METHOD THAT SWAPS 2 EVENTS 
    private void swap(int i, int j){
        Event tmp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, tmp);
    }

    //METHOD THAT GETS CURRENT TIME
    private LocalDateTime getCurrentTime(){
        TimeTeller teller = TimeService.getTeller();
        TimeService.stop();
        return teller.now();
    }

    //METHOD THAT CALCULATES THE TIME THAT HAW PASSED FROM THE EPOCH TO THE CURRENT MOMENT,IN SECONDS
    private long getTimeSinceEpoch(){
        //CREATES AN ZONEID OBJECT THAT REPRESENTS THE DEFAULT ZONE OF THE SYSTEM
        ZoneId defaultZone = ZoneId.systemDefault();
        //APPENDS THE ZONE TO THE CURRENT LOCAL TIME OBTAINED FROM getCurrentTime() AND CONVERTS THAT TIME TO SECONDS FROM EPOCH
        long timeSinceEpoch = getCurrentTime().atZone(defaultZone).toEpochSecond();
        return timeSinceEpoch;
    }

    public String[] getData(){
        return data.toArray(new String[0]);
    }

    public void clearData(){
        data = new ArrayList();
    }
}
