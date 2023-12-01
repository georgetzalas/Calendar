package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class eventList extends userInteraction{

    public eventList(ArrayList<Event> events){
        super(events);
    }

    public void choose(String action){
        
        //this.sort();
        //this.showEventsFromNow();
        
        switch(action){
            case "day":
                this.day();
                break;
            case "week":
                this.week();
                break;
            case "month":
                this.month();
                break;
            case "pastday":
                this.pastday();
                break;
            case "pastweek":
                this.pastweek();
                break;
            case "pastmonth":
                this.pastmonth();
                break;
            case "todo":
                this.todo();
                break;
            case "due":
                this.due();
                break;
            default:
                System.out.println("Please provide a valid action");
                System.exit(0);
                break;
            
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

        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    }

    private void pastday(){
        LocalDateTime endOfDay = getCurrentTime().toLocalDate().atTime(LocalTime.MIN);
        long epoch = endOfDay.toEpochSecond(ZoneOffset.UTC);
        
        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    }

    private void week(){
        LocalDateTime endOfWeek = getCurrentTime().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23).withMinute(59).withSecond(59);
        long epoch = endOfWeek.toEpochSecond(ZoneOffset.UTC);

        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
        
    }

    private void pastweek(){
        LocalDateTime startOfWeek = getCurrentTime().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        long epoch = startOfWeek.toInstant(ZoneOffset.UTC).getEpochSecond();
        
        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }
    
    }
    
    private void month(){
        LocalDateTime endOfMonth = getCurrentTime().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        long epoch = endOfMonth.toEpochSecond(ZoneOffset.UTC);
        
        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds > timeSinceEpoch && eventSeconds < epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        }   
    }

    private void pastmonth(){
        LocalDateTime startOfMonth = getCurrentTime().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        long epoch = startOfMonth.toInstant(ZoneOffset.UTC).getEpochSecond();

        for(Event e : events){
            long eventSeconds = e.calculateEpochOfStart();
            long timeSinceEpoch = this.getTimeSinceEpoch();
            if(eventSeconds < timeSinceEpoch && eventSeconds >= epoch){
                    System.out.println(e.getTitle() + " " + e.toString());
            }
        } 
    }

    private void todo(){
        for(Event t : events){
            if(t.getClass() == Task.class){
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                if(e.calculateEpochOfEnd() >= timeSinceEpoch && e.getCompleteTask().equals("IN-PROGRESS")){
                    System.out.println(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    private void due(){
        for(Event t : events){
            if(t.getClass() == Task.class){
                long timeSinceEpoch = this.getTimeSinceEpoch();
                Task e = (Task)t;
                if(e.calculateEpochOfEnd() < timeSinceEpoch && e.getCompleteTask().equals("IN-PROGRESS")){
                    System.out.println(e.getTitle() + " " + e.toString());
                }
            }
        }
    }

    private void showEventsFromNow(){
        for(Event e : events){
            long timeSinceEpoch = this.getTimeSinceEpoch();
            
            if(e.calculateEpochOfEnd() >= timeSinceEpoch){
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
        return LocalDateTime.now();
    }

    private long getTimeSinceEpoch(){
        ZoneId defaultZone = ZoneId.systemDefault();
        long timeSinceEpoch = getCurrentTime().atZone(defaultZone).toEpochSecond();
        return timeSinceEpoch;
    }
}