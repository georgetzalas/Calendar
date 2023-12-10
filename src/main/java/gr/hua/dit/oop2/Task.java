package gr.hua.dit.oop2;
import java.util.*; 
import java.time.*; 
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
//SUB CLASS OF EVENT
public class Task extends Event{
    private String completetask;
    
    public Task(){
        super();
    }
    //CONSTRACTOR
    public Task(String title, String description, int startDay, int startMonth, int startYear, int startHour, int startMinute,
    int endDay, int endMonth, int endYear, int endHour, int endMinute, String completetask){
        super(title, description, startDay, startMonth, startYear, startHour, startMinute,
        endDay, endMonth, endYear, endHour, endMinute);
        this.completetask = completetask;
    }
    //GETTERS-SETTERS
    public String getCompleteTask(){
        return completetask;
    }
    
    public String setCompleteTask(String completetask){
        return this.completetask = completetask;
    }
    //METHOD THAT RETURNS THE END-DATE INTO A LONG INTEGER
    @Override
    public long calculateEpoch(){
        long epoch;
        
        LocalDateTime date = LocalDateTime.of(this.getEndYear(), this.getEndMonth(), this.getEndDay(), this.getEndHour(), this.getEndMinute(), 0);

        epoch = date.atZone(ZoneId.systemDefault()).toEpochSecond();
        
        return epoch;
    }
    
  

     
 
    //METHOD TOSTRING
    @Override
    public String toString(){
        return getTitle() + "  " + getDescription() + "  " + getEndYear() + "/" + getEndMonth() + "/" + getEndDay() + " " + getEndHour() + ":" + getEndMinute() + " - " + getCompleteTask();
    }
}