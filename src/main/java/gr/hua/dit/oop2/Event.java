package gr.hua.dit.oop2;

public class Event{
    private String title;
    private String description;
    private EventDate date;

    private class EventDate{
        int startDay;
        int startMonth;
        int startYear;
        int startHour;
        int startMinute;

        int endDay;
        int endMonth;
        int endYear;
        int endHour;
        int endMinute;
    }
    
    public Event(){
        date = new EventDate();
    }

    public Event(String title, String description, int startDay, int startMonth, int startYear, int startHour, int startMinute,
    int endDay, int endMonth, int endYear, int endHour, int endMinute){
        date = new EventDate();
        
        this.title = title;
        this.description = description;

        this.date.startDay = startDay;
        this.date.startMonth = startMonth;
        this.date.startYear = startYear;
        this.date.startHour = startHour;
        this.date.startMinute = startMinute;

        this.date.endDay = endDay;
        this.date.endMonth = endMonth;
        this.date.endYear = endYear;
        this.date.endHour = endHour;
        this.date.endMinute = endMinute;
    }
    
    public void strToEventDateStart(String date){
        this.setStartDay(Integer.parseInt(date.substring(0, 2)));
        this.setStartMonth(Integer.parseInt(date.substring(3, 5)));
        this.setStartYear(Integer.parseInt(date.substring(6, 10)));
        this.setStartHour(Integer.parseInt(date.substring(11, 13)));
        this.setStartMinute(Integer.parseInt(date.substring(14, 16)));
    }

    public void strToEventDateEnd(String date){
        this.setEndDay(Integer.parseInt(date.substring(0, 2)));
        this.setEndMonth(Integer.parseInt(date.substring(3, 5)));
        this.setEndYear(Integer.parseInt(date.substring(6, 10)));
        this.setEndHour(Integer.parseInt(date.substring(11, 13)));
        this.setEndMinute(Integer.parseInt(date.substring(14, 16)));
    }
    
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    public void setStartDay(int startDay){
        this.date.startDay = startDay;
    } 
    
    public void setStartMonth(int startMonth){
        this.date.startMonth = startMonth;
    } 
    
    public void setStartYear(int startYear){
        this.date.startYear = startYear;
    } 
    
    public void setStartHour(int startHour){
        this.date.startHour = startHour;
    } 
    
    public void setStartMinute(int startMinute){
        this.date.startMinute = startMinute;
    } 

    public void setEndDay(int endDay){
        this.date.endDay= endDay;
    }
    
    public void setEndMonth(int endMonth){
        this.date.endMonth = endMonth;
    }

    public void setEndYear(int endYear){
        this.date.endYear = endYear;
    }
    
    public void setEndHour(int endHour){
        this.date.endHour = endHour;
    }
    
    public void setEndMinute(int endMinute){
        this.date.endMinute = endMinute;
    }

    public String getTitle(){ return this.title; }

    public String getDescription(){ return this.description; }
    
    public int getStartDay(){ return this.date.startDay; }

    public int getStartMonth(){ return this.date.startMonth; }

    public int getStartYear(){ return this.date.startYear; }

    public int getStartHour(){ return this.date.startHour; }

    public int getStartMinute(){ return this.date.startMinute; }

    public int getEndDay(){ return this.date.endDay; }

    public int getEndMonth(){ return this.date.endMonth; }

    public int getEndYear(){ return this.date.endYear; }

    public int getEndHour(){ return this.date.endHour; }

    public int getEndMinute(){ return this.date.endMinute; }

    @Override
    public String toString(){
        String start = getStartYear() + "/" + getStartMonth() + "/" + getStartDay() + " " + getStartDay() + ":" + getStartMinute();
        
        return start;
    }

}