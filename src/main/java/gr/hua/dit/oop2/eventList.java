package gr.hua.dit.oop2;

import java.util.ArrayList;
import java.time.Instant;

public class eventList{

    public void sort(ArrayList<Event> events){
        for (int i = 0; i < events.size() - 1; i++){
            for (int j = 0; j < events.size() - i - 1; j++) {
                if(events.get(j).compareTo(events.get(j + 1)) > 0){
                    swap(events, j, j+1);
                }
            }
        }
    }

    public void showEventsFromNow(ArrayList<Event> events){
        for (int i = 0; i < events.size(); i++){
            if(events.get(i).calculateEpochOfStart() >= this.getCurrentTime()){
                System.out.println(events.get(i).getTitle() + " " + events.get(i).toString());
            }
        }
    }
    
    private void swap(ArrayList<Event> events, int i, int j){
        Event tmp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, tmp);
    }

    private long getCurrentTime(){
        long currentTime = Instant.now().getEpochSecond();
        return currentTime;
    }
}