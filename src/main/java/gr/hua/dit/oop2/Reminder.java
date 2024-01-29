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

// Reminder class to handle reminders for events
public class Reminder {

    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;;
    // TimeTeller instance to track time
    private TimeTeller teller;

    // Constructor to initialize the Reminder
    public Reminder() {
        // Get a TimeTeller instance
        teller = TimeService.getTeller();
        // Add a TimeListener to the TimeTeller

        teller.addTimeListener(new Listener());
    }

    // Inner class implementing TimeListener interface to track time changes
    private class Listener implements TimeListener{

        // Method called when time changes
        @Override
        public void timeChanged(TimeEvent e) {
            LocalDateTime now = e.getDateTime();
            // Calculate time difference for events
            calculateTimeDiff(now);
        }

        // Method to calculate time difference for events
        private void calculateTimeDiff(LocalDateTime now){
            LocalDateTime eventTime;

            // Iterate through each event
            for (Event event : events) {
                // Determine the time of the event
                if (event.getClass().equals(Task.class)){
                    eventTime = LocalDateTime.of(event.getEndYear(), event.getEndMonth(), event.getEndDay(), event.getEndHour(), event.getEndMinute());
                }else{
                    eventTime = LocalDateTime.of(event.getStartYear(), event.getStartMonth(), event.getStartDay(), event.getStartHour(), event.getStartMinute());
                }

                // Skip events that are already completed
                if(event.getClass().equals(Task.class)){
                    Task t = (Task)event;
                    if(!t.getCompleteTask().equals("IN-PROCESS")) continue;
                }

                // Calculate time difference in minutes
                long minutes = ChronoUnit.MINUTES.between(now, eventTime);

                // Check if event starts in the next 5 minutes and hasn't already popped up
                if(minutes >= 0 && minutes < 5 && !event.getIsPoppedUp()){
                    JOptionPane.showMessageDialog(null, "The event: " + event.getTitle() + " it's starting now", "WARNING", JOptionPane.PLAIN_MESSAGE);
                    // Set the event as popped up
                    event.setIsPoppedUp(true);
                }
            }    
        }
    
    }
}