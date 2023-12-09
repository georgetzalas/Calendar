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

    public eventList(ArrayList<Event> events){
        super(events);
    }

    public void choose(String action){

        System.out.println("All the upcoming events sorted");

        this.sort();
        this.showEventsFromNow();
        
        if(action.equals("day")){
            this.day();
        }else if(action.equals("week")){
            this.week();
        }else if(action.equals("month")){
            this.month();
        }else if(action.equals("pastday")){
            this.pastday();
        }else if(action.equals("pastweek")){
            this.pastweek();
        }else if(action.equals("pastmonth")){
            this.pastmonth();
        }else if(action.equals("todo")){
            this.todo();
        }else if(action.equals("due")){
            this.due();
        }else{
            System.out.println("Please provide a valid action");
            System.exit(0);
        }
    }

    private void sort(){
        for (int i = 0; i < events.size() - 1; i++){
            for (int j = 0; j < events.size() - i - 1; j++) {
                if(events.get(j).compareTo(events.get(j + 1)) > 0){
                        swap(j, j+1);
                }
            }
        }
    }

    private void day(){
        LocalDateTime endOfDay = getCurrentTime().toLocalDate().atTime(LocalTime.MAX);
        long epoch = endOfDay.toEpochSecond(ZoneOffset.UTC);

        System.out.println("Remaining events today");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    }

    private void pastday(){
        LocalDateTime endOfDay = getCurrentTime().toLocalDate().atTime(LocalTime.MIN);
        long epoch = endOfDay.toEpochSecond(ZoneOffset.UTC);
        
        System.out.println("Finished events today");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    }

    private void week(){
        LocalDateTime endOfWeek = getCurrentTime().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23).withMinute(59).withSecond(59);
        long epoch = endOfWeek.toEpochSecond(ZoneOffset.UTC);

        System.out.println("Remaining events in this week");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
        
    }

    private void pastweek(){
        LocalDateTime startOfWeek = getCurrentTime().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        long epoch = startOfWeek.toInstant(ZoneOffset.UTC).getEpochSecond();
        
        System.out.println("Finished events in this week");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    
    }
    
    private void month(){
        LocalDateTime endOfMonth = getCurrentTime().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        long epoch = endOfMonth.toEpochSecond(ZoneOffset.UTC);
        
        System.out.println("Remaining events in this week");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }   
    }

    private void pastmonth(){
        LocalDateTime startOfMonth = getCurrentTime().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        long epoch = startOfMonth.toInstant(ZoneOffset.UTC).getEpochSecond();

        System.out.println("Remaining events in this month");

        for(Event e : events){
            long eventSeconds = e.calculateEpoch();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        } 
    }

    private void todo(){
        
        System.out.println("The task is still within the deadline and its not finished");
        
        for(Event t : events){
            if(t.getClass() == Task.class){
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                if(e.calculateEpoch() >= timeSinceEpoch && !e.getCompleteTask().equals("COMPLETED")){
                    System.out.println(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    private void due(){
        
        System.out.println("The task is not within the deadline and its not finished");

        for(Event t : events){
            if(t.getClass() == Task.class){
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                if(e.calculateEpoch() < timeSinceEpoch && !e.getCompleteTask().equals("COMPLETED")){
                    System.out.println(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    private void showEventsFromNow(){
        for(Event e : events){
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(e.calculateEpoch() >= timeSinceEpoch){
                System.out.println(e.getTitle() + " " + e.toString());
            }

        }
    }
    
    private void swap(int i, int j){
        Event tmp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, tmp);
    }

    private LocalDateTime getCurrentTime(){
        TimeTeller teller = TimeService.getTeller();
        TimeService.stop();
        return teller.now();
    }

    private long getTimeSinceEpoch(){
        ZoneId defaultZone = ZoneId.systemDefault();
        long timeSinceEpoch = getCurrentTime().atZone(defaultZone).toEpochSecond();
        return timeSinceEpoch;
    }
}