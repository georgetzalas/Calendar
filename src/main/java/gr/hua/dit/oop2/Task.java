package gr.hua.dit.oop2;

public class Task extends Event{
    private String completetask;
    
    public Task(){
        super();
    }
    
    public Task(String title, String description, int startDay, int startMonth, int startYear, int startHour, int startMinute,
    int endDay, int endMonth, int endYear, int endHour, int endMinute, String completetask){
        super(title, description, startDay, startMonth, startYear, startHour, startMinute,
        endDay, endMonth, endYear, endHour, endMinute);
        this.completetask = completetask;
    }
    
    public String getCompleteTask(){
        return completetask;
    }
    
    public String setCompleteTask(String completetask){
        return this.completetask=completetask;
    }
    
    @Override
    public String toString(){
        return super.toString() + "Status: " + completetask;
    }
}