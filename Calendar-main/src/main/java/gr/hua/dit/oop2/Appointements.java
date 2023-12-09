package gr.hua.dit.oop2;

import java.time.LocalDateTime;
import java.time.ZoneId;
//SUB CLASS OF EVENT
public class Appointements extends Event{
    //INITIALIZING THE VARIABLES
    private int duration;
    
    public Appointements(){
        super();
    }
    //CONSTRUCTOR
    public Appointements(String title, String description, int startDay, int startMonth, int startYear, int startHour, int startMinute,
    int endDay, int endMonth, int endYear, int endHour, int endMinute, int duration){
        super(title, description, startDay, startMonth, startYear, startHour, startMinute,
        endDay, endMonth, endYear, endHour, endMinute);
        this.duration = duration;
    }
    
    public int getDuration(){
        return duration;
    }
    
    public void setDuration(int duration){
        this.duration=duration;
    }
    //METHOD THAT RETURNS THE END DATE INTO A LONG INTEGER 
    public long calculateEpochEnd(){
        long epoch;
        
        LocalDateTime date = LocalDateTime.of(this.getEndYear(), this.getEndMonth(), this.getEndDay(), this.getEndHour(), this.getEndMinute(), 0);

        epoch = date.atZone(ZoneId.systemDefault()).toEpochSecond();
        
        return epoch;
    }
    
    //METHOD TOSTRING
    @Override
    public String toString(){
        return super.toString() + " Duration: " + duration;
    }
}
