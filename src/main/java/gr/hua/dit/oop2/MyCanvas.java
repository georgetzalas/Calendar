package gr.hua.dit.oop2;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

class MyCanvas extends JComponent {
    private Calendar currentMonth;
    private int startDay = -1;
    private int endDay = -1;
    private int startHour = 0;
    private int startMinute = 0;
    private int endHour = 0;
    private int endMinute = 0;
    private int selectedEndYear;
    private int selectedEndMonth;
    private int selectedYear;
    private int selectedMonth;
    private List<Task> events = new ArrayList<>();
    //private List<Appointement> eventsAp = new ArrayList<>();
    
    public MyCanvas() {
        currentMonth = Calendar.getInstance();

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> updateMonth(-1));

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> updateMonth(1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 350, 350);

        window.getContentPane().setLayout(new BorderLayout());
        window.getContentPane().add(this, BorderLayout.CENTER);
        window.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        window.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                handleMouseClick(x, y);
            }
        });

        window.setVisible(true);
    }
    

private void handleMouseClick(int x, int y) {
  int cellWidth = getWidth() / 7;
  int cellHeight = (getHeight() - 81) / 5;

  int col = (x - 46) / cellWidth;
  int row = (y - 81) / cellHeight;

  if (col >= 0 && col < 7 && row >= 0 && row < 4) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentMonth.getTime());
    cal.set(Calendar.DATE, 1);
    cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);

    int dayOffset = col + 1 + row * 7;
    cal.add(Calendar.DATE, dayOffset - 1);
    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

    String evoption = JOptionPane.showInputDialog("To Add Appointment or Task ?");

    if (evoption != null && evoption.equalsIgnoreCase("Appointment")) {
        if (startDay == -1) {
            // Prompt for start day
            startDay = dayOfMonth;
            promptForTime("Enter start time for the appointment:", true);
            dayOffset = col + 1 + row * 7;
            cal.add(Calendar.DATE, dayOffset - 1);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            String eventTitle = JOptionPane.showInputDialog("Enter event title for days " + startDay + " to " + endDay + ":");
            String desc = JOptionPane.showInputDialog("Enter a Description of the Appointment");
            // Ask the user if they want to enter the end date or duration
            String endOption = JOptionPane.showInputDialog("Do you want to enter the end date (E) or duration (D)?");
            if (endOption != null && endOption.equalsIgnoreCase("E")) {
                // Prompt for end date
                endDay = dayOfMonth;
                promptForTime("Enter end time for the appointment:", false);
                
            } else if (endOption != null && endOption.equalsIgnoreCase("D")) {
                // Prompt for duration
                promptForDuration("Enter duration for the appointment:");
            }
            System.out.println("Appointment for days " + startDay + " to " + endDay + ": " + eventTitle
                    + " (Start time: " + startHour + ":" + startMinute + ", End time: " + endHour + ":" + endMinute + ")");

            //Appointements App1 = new Appointements(eventTitle, desc, startDay, selectedMonth, selectedYear, startHour, startMinute, endDay, endmonth,endyear, endHour, endMinute, durationMinute);
            //eventsAp.add(App1);
            // Reset start and end days and times for the next appointment
            startDay = -1;
            endDay = -1;
            startHour = 0;
            startMinute = 0;
            endHour = 0;
            endMinute = 0;
        }
    } else if (evoption != null && evoption.equalsIgnoreCase("Task")) {
            dayOffset = col + 1 + row * 7;
            cal.add(Calendar.DATE, dayOffset - 1);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            // Prompt for end date
            endDay = dayOfMonth;
            promptForTime("Enter end time for the task:", false);
            

            String eventTitle = JOptionPane.showInputDialog("Enter event title for days " + startDay + " to " + endDay + ":");
            String desc = JOptionPane.showInputDialog("Enter a Description of the Task");
            String stat = "IN-PROCESS";
            Task Ta1 = new Task(eventTitle, desc, 0, 0, 0, 0, 0,
                endDay, selectedMonth, selectedYear, endHour, endMinute, stat);
                events.add(Ta1);
            System.out.println("Task for days " + endDay + ": " + eventTitle
                    + ", End time: " + endHour + ":" + endMinute + stat );

            // Reset start and end days and times for the next task
            startDay = -1;
            endDay = -1;
            startHour = 0;
            startMinute = 0;
            endHour = 0;
            endMinute = 0;
        }
    }
}


private void promptForDuration(String message) {
JPanel panel = new JPanel();

JSpinner durationMinuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 4320, 1));

//panel.add(new JLabel("Duration Hour:"));
//panel.add(durationHourSpinner);
panel.add(new JLabel("Duration Minute:"));
panel.add(durationMinuteSpinner);

int result = JOptionPane.showConfirmDialog(null, panel, message, JOptionPane.OK_CANCEL_OPTION);
if (result == JOptionPane.OK_OPTION) {
    //int durationHour = (int) durationHourSpinner.getValue();
    int durationMinute = (int) durationMinuteSpinner.getValue();

    // Set end time based on duration
    //endHour = startHour + durationHour;
    endMinute = startMinute + durationMinute;

    // Adjust end time if it goes beyond 23:59
    if (endMinute >= 60) {
        endHour += endMinute / 60;
        endMinute = endMinute % 60;
    }

    if (endHour > 23) {
        endHour = 23;
        endMinute = 59;
    }
}
}





private void promptForTime(String message, boolean isStart) {
    JPanel panel = new JPanel();
    JSpinner hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
    JSpinner minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

    panel.add(new JLabel("Hour:"));
    panel.add(hourSpinner);
    panel.add(new JLabel("Minute:"));
    panel.add(minuteSpinner);

    int result = JOptionPane.showConfirmDialog(null, panel, message, JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        if (isStart) {
            startHour = (int) hourSpinner.getValue();
            startMinute = (int) minuteSpinner.getValue();
        } else {
            endHour = (int) hourSpinner.getValue();
            endMinute = (int) minuteSpinner.getValue();
        }
    }
}

// ... (unchanged code)
 
  
    private void updateMonth(int offset) {
        currentMonth.add(Calendar.MONTH, offset);
        selectedYear = currentMonth.get(Calendar.YEAR);
        selectedMonth = currentMonth.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
        repaint();
    }

    public void paint(Graphics g) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        Date date = currentMonth.getTime();

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.black);
        g.drawString(monthFormat.format(date), 90, 36);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);

        for (int week = 0; week < 5; week++) {
            for (int d = 0; d < 7; d++) {
                String dayStr = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                g.drawString(dayStr, d * 30 + 46 + 4, week * 29 + 81 + 20);
                cal.add(Calendar.DATE, +1);
            }
        }
      }
  static class CalendarFrame {
    public static void CanvasVis() {
      SwingUtilities.invokeLater(() -> {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 350, 350);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);
      });
    }
  }
}