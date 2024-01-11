package gr.hua.dit.oop2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Calendar;
import java.text.DateFormatSymbols;

public class FunctionsPanel extends JPanel{
    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;
    private JComboBox<String> yearComboBox, monthComboBox;
    private JButton prevButton, nextButton,backButton;
    private JPanel calendarPanel, topPanel, backButtonPanel;
    private JTextArea eventsTextArea;
    private Calendar calendar;

    public FunctionsPanel(){
        Event a = new Event("Test", "Test Desc", 1, 2, 2023, 1, 3, 5, 10, 2025, 5, 1);
        events.add(a);
        setLayout(new BorderLayout());
        
        createBackButton();
        add(backButtonPanel,BorderLayout.SOUTH);
        
        calendar = Calendar.getInstance();

        JPanel topPanel = new JPanel(new FlowLayout());

        prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousMonth();
            }
        });
        topPanel.add(prevButton);

        yearComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();

        for (int i = 1900; i <= 2100; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            monthComboBox.addItem(months[i]);
        }

        yearComboBox.setSelectedItem(String.valueOf(calendar.get(Calendar.YEAR)));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]);

        yearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCalendar();
            }
        });

        monthComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCalendar();
            }
        });

        topPanel.add(yearComboBox);
        topPanel.add(monthComboBox);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextMonth();
            }
        });
        topPanel.add(nextButton);

        add(topPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new GridLayout(0, 7));
        add(calendarPanel, BorderLayout.CENTER);

        eventsTextArea = new JTextArea(10, 20);
        eventsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventsTextArea);
        add(scrollPane, BorderLayout.EAST);

        updateCalendar();
        
        setVisible(false);
    }

    private void createBackButton(){
        //topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel = new JPanel();

        backButton = new JButton("Back");
        backButton.addActionListener(new Actions(this));

        backButtonPanel.add(backButton);
    }

    private void updateCalendar() {
        calendar.set(Calendar.YEAR, Integer.parseInt((String) yearComboBox.getSelectedItem()));

        for (int i = 0; i < new DateFormatSymbols().getMonths().length - 1; i++) {
            if (monthComboBox.getSelectedItem().equals(new DateFormatSymbols().getMonths()[i])) {
                calendar.set(Calendar.MONTH, i);
                break;
            }
        }

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendarPanel.removeAll();

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, JLabel.CENTER);
            calendarPanel.add(label);
        }

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayEvents(Integer.parseInt(button.getText()));
                }
            });
            calendarPanel.add(button);
        }

        revalidate();
        repaint();
    }

    private void previousMonth() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (month == 0) {
            year--;
            month = 11;
        } else {
            month--;
        }

        yearComboBox.setSelectedItem(String.valueOf(year));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[month]);
    }

    private void nextMonth() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (month == 11) {
            year++;
            month = 0;
        } else {
            month++;
        }

        yearComboBox.setSelectedItem(String.valueOf(year));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[month]);
    }
    private String eventsText;
    private void displayEvents(int day) {
        //eventsText = "Events for " + monthComboBox.getSelectedItem() + " " + day + ", " + yearComboBox.getSelectedItem() + ":\n";
        
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        int month = getMonthNumber(selectedMonth);
        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
        ArrayList<Event> eventsForDay = getEventsForDay(day, month,year);
         
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        JLabel eventTextLabel = new JLabel("Events for " + monthComboBox.getSelectedItem() + " " + day + ", " + yearComboBox.getSelectedItem() + ":");
        buttonsPanel.add(eventTextLabel);

        if (eventsForDay.isEmpty()) {
            buttonsPanel.add(new JLabel("No events for this day."));
            //eventsText +="No events for this day.";
        } else {
            eventsText +=""; 
            for (Event event : eventsForDay) {
                //System.out.print(event.toString());
                JButton eventButton = new JButton(event.getTitle()); 
                eventButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EditEvents(event); 
                    }
                });
                buttonsPanel.add(eventButton);
                //eventsPanel.add(eventButton);
                //eventsText += " ";
                //eventsText += event.getTitle() + "\n";             
                //eventsTextArea.setText(eventsText);
            }
        }
        
        //events += "Here we will see the events of this day!\nPlease wait until we fix it :)";
        //eventsTextArea.setText("GEorge"); 
        for (Component component : getComponents()) {
            if (component instanceof JScrollPane) {
                remove(component);
            }
        }
        JScrollPane newScrollPane = new JScrollPane(buttonsPanel);
        add(newScrollPane, BorderLayout.EAST);

        revalidate();
        repaint();

    }
    

    private ArrayList<Event> getEventsForDay(int day,int month,int year) {
        ArrayList<Event> eventsForDay = new ArrayList<>();
        for (Event event : events) {
            
            if (event.getStartDay() == day && event.getStartMonth() == month && event.getStartYear() == year) {    
                eventsForDay.add(event);
            }else{
                eventsTextArea.setText("");
            }

        }

        return eventsForDay;
    }

    private void EditEvents(Event event) {
        JFrame frame = new JFrame("Edit Events");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setSize(300, 200); 

    
        JLabel titleLabel = new JLabel("Title: " + event.getTitle());
        JLabel descriptionLabel = new JLabel("Description: " + event.getDescription());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1)); 
        panel.add(titleLabel);
        panel.add(descriptionLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
    private int getMonthNumber(String selectedMonth) {
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(selectedMonth)) {
                
                return i + 1;
            }
        }
        // Return -1 if the selected month is not found (handle as needed in your code).
        return -1;
    }

}