package gr.hua.dit.oop2;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.time.LocalDateTime;

public class Reminder {

    public static ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;;
    private LocalDateTime now1 = LocalDateTime.now();

    private Integer currentyear = now1.getYear();
    private Integer currentmonth = now1.getMonthValue();
    private Integer currentday = now1.getDayOfMonth();
    private Integer currenthour = now1.getHour();
    private Integer currentminute = now1.getMinute();

    public Reminder() {
        for (Event event : events) {
            if (event.getClass().equals(Appointements.class)) {
                Appointements appointmentsEvent = (Appointements) event;
                if(currentyear.equals(appointmentsEvent.getStartYear())&& currentmonth.equals(appointmentsEvent.getStartMonth())&& currentday.equals(appointmentsEvent.getStartDay())&& currenthour.equals(appointmentsEvent.getStartHour())&& currentminute.equals(appointmentsEvent.getStartMinute())){
                    JOptionPane.showMessageDialog(null, "The Appointment: "+ appointmentsEvent.getTitle() + "it's starting now", "WARNING", JOptionPane.PLAIN_MESSAGE);
                }
            }else if(event.getClass().equals(Task.class)){
                Task taskev = (Task) event;
                if(currentyear.equals(taskev.getEndYear())&& currentmonth.equals(taskev.getEndMonth())&& currentday.equals(taskev.getEndDay())&& currenthour.equals(taskev.getEndHour())&& currentminute.equals(taskev.getEndMinute())){
                    JOptionPane.showMessageDialog(null, "The Task: "+ taskev.getTitle() + "it's starting now", "WARNING", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

}