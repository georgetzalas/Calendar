package gr.hua.dit.oop2;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import biweekly.util.Duration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import gr.hua.dit.oop2.calendar.TimeListener;
import gr.hua.dit.oop2.calendar.TimeEvent;

public class Reminder {

    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;;
    private TimeTeller teller;

    public Reminder() {
        teller = TimeService.getTeller();
        teller.addTimeListener(new Listener());
    }

    private class Listener implements TimeListener{

        @Override
        public void timeChanged(TimeEvent e) {
            LocalDateTime now = e.getDateTime();
            calculateTimeDiff(now);
        }

        private void calculateTimeDiff(LocalDateTime now){
            LocalDateTime eventTime;

            for (Event event : events) {
                if (event.getClass().equals(Task.class)){
                    eventTime = LocalDateTime.of(event.getEndYear(), event.getEndMonth(), event.getEndDay(), event.getEndHour(), event.getEndMinute());
                }else{
                    eventTime = LocalDateTime.of(event.getStartYear(), event.getStartMonth(), event.getStartDay(), event.getStartHour(), event.getStartMinute());
                }

                long minutes = ChronoUnit.MINUTES.between(now, eventTime);

                if(minutes >= 0 && minutes < 5 && !event.getIsPoppedUp()){
                    System.out.println(minutes);
                    JOptionPane.showMessageDialog(null, "The event: " + event.getTitle() + " it's starting now", "WARNING", JOptionPane.PLAIN_MESSAGE);
                    event.setIsPoppedUp(true);
                }
            }    
        }
    
    }



}