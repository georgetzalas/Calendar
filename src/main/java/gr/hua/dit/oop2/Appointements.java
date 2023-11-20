package gr.hua.dit.oop2;

public class Appointements extends Event{
    private int duration;
    
    public Appointements(){
        super();
    }

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
    
    @Override
    public String toString(){
        return super.toString() +"Duration: "+ duration;
    }
}