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
import java.time.LocalDateTime;

public class FunctionsPanel extends JPanel{
    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;
    private JComboBox<String> yearComboBox, monthComboBox;
    private JButton prevButton, nextButton,backButton;
    private JPanel calendarPanel, topPanel, backButtonPanel;
    private JTextArea eventsTextArea;
    private Calendar calendar;
    String[] responces = { "Duration", "End-date" };
    String[] options = {"COMPLETED","CANCELLED","IN-PROCESS","NEEDS-ACTION"};

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
    //private String eventsText;
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
            //eventsText +=""; 
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
        frame.setSize(800, 700); 
        frame.setResizable(false);
    
        //JLabel titleLabel = new JLabel("Title: " + event.getTitle());
        //JLabel descriptionLabel = new JLabel("Description: " + event.getDescription())
        
            JPanel editPanel = new JPanel();
            
            editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel titleLabel = new JLabel("Title:");
            JTextField titleTextField = new JTextField(event.getTitle());
            titleTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleTextField.getPreferredSize().height));
            titlePanel.add(titleLabel);
            titlePanel.add(titleTextField);

            JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel descriptionLabel = new JLabel("Description:");
            JTextField descriptionTextField = new JTextField(event.getDescription());
            descriptionTextField.setAutoscrolls(false);
            descriptionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
            descriptionPanel.add(descriptionLabel);
            descriptionPanel.add(descriptionTextField);
        
            JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startDayLabel = new JLabel("Start Day:");
            //JComboBox<Integer> startDayComboBox = new JComboBox<>();
            //for (int i = 1; i <= 31; i++) {
                //startDayComboBox.addItem(i);
            //}
            JComboBox<Integer> startDayComboBox = new JComboBox<>(new DefaultComboBoxModel<>(
                generateNumberArray(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));
            startDayComboBox.setSelectedItem(event.getStartDay());
            startDatePanel.add(startDayLabel);
            startDatePanel.add(startDayComboBox);
            
            JPanel startMonthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startMonthLabel = new JLabel("Start Month:");
            JComboBox<String> startMonthComboBox = new JComboBox<>();
            String[] months = new DateFormatSymbols().getMonths();
            for (int i = 0; i < months.length - 1; i++) {
                startMonthComboBox.addItem(months[i]);
            }
            startMonthComboBox.setSelectedItem(new DateFormatSymbols().getMonths());
            startMonthComboBox.setSelectedItem(getMonthName(event.getStartMonth()));
            startMonthPanel.add(startMonthLabel);
            startMonthPanel.add(startMonthComboBox);
        
            JPanel startYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startYearLabel = new JLabel("Start Year:");
            JComboBox<Integer> startYearComboBox = new JComboBox<>();
            for (int i = 1900; i <= 2100; i++) {
                startYearComboBox.addItem(i);
            }
            startYearComboBox.setSelectedItem(event.getStartYear());
            startYearPanel.add(startYearLabel);
            startYearPanel.add(startYearComboBox);
            
            JPanel startHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startHourLabel = new JLabel("Start Hour:");
            JComboBox<Integer> startHourComboBox = new JComboBox<>();
            for (int i = 0; i <= 23; i++) {
                startHourComboBox.addItem(i);
            }
            startHourComboBox.setSelectedItem(event.getStartHour());
            startHourPanel.add(startHourLabel);
            startHourPanel.add(startHourComboBox);
            
            JPanel startMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startMinutesLabel = new JLabel("Start Minutes:");
            JComboBox<Integer> startMinutesComboBox = new JComboBox<>();
            for (int i = 0; i <= 59; i++) {
                startMinutesComboBox.addItem(i);
            }
            startMinutesComboBox.setSelectedItem(event.getStartMinute());
            startMinutesPanel.add(startMinutesLabel);
            startMinutesPanel.add(startMinutesComboBox);
            
            Font largeFont = new Font("Arial", Font.PLAIN, 16); 
            titleLabel.setFont(largeFont);
            descriptionLabel.setFont(largeFont);

            Font dateFont = new Font("Arial", Font.PLAIN, 14); 
            startDayLabel.setFont(dateFont);
            startMonthLabel.setFont(dateFont);
            startYearLabel.setFont(dateFont);
            startHourLabel.setFont(dateFont);
            startMinutesLabel.setFont(dateFont);

            JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endDayLabel = new JLabel("End Day:");
            JComboBox<Integer> endDayComboBox = new JComboBox<>();
            for (int i = 1; i <= 31; i++) {
                endDayComboBox.addItem(i);
            }
            //JComboBox<Integer> endDayComboBox = new JComboBox<>(new DefaultComboBoxModel<>(
                //generateNumberArray(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));
            endDayComboBox.setSelectedItem(event.getEndDay());
            endDatePanel.add(endDayLabel);
            endDatePanel.add(endDayComboBox);
            
            JPanel endMonthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endMonthLabel = new JLabel("End Month:");
            JComboBox<String> endMonthComboBox = new JComboBox<>();
            String[] months1 = new DateFormatSymbols().getMonths();
            for (int i = 0; i < months1.length - 1; i++) {
                endMonthComboBox.addItem(months1[i]);
            }
            endMonthComboBox.setSelectedItem(new DateFormatSymbols().getMonths());
            endMonthComboBox.setSelectedItem(getMonthName(event.getEndMonth()));
            endMonthPanel.add(endMonthLabel);
            endMonthPanel.add(endMonthComboBox);
        
            JPanel endYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endYearLabel = new JLabel("End Year:");
            JComboBox<Integer> endYearComboBox = new JComboBox<>();
            for (int i = 1900; i <= 2100; i++) {
                endYearComboBox.addItem(i);
            }
            endYearComboBox.setSelectedItem(event.getEndYear());
            endYearPanel.add(endYearLabel);
            endYearPanel.add(endYearComboBox);
            
            JPanel endHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endHourLabel = new JLabel("End Hour:");
            JComboBox<Integer> endHourComboBox = new JComboBox<>();
            for (int i = 0; i <= 23; i++) {
                endHourComboBox.addItem(i);
            }
            endHourComboBox.setSelectedItem(event.getEndHour());
            endHourPanel.add(endHourLabel);
            endHourPanel.add(endHourComboBox);
            
            JPanel endMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endMinutesLabel = new JLabel("End Minutes:");
            JComboBox<Integer> endMinutesComboBox = new JComboBox<>();
            for (int i = 0; i <= 59; i++) {
                endMinutesComboBox.addItem(i);
            }
            endMinutesComboBox.setSelectedItem(event.getEndMinute());
            endMinutesPanel.add(endMinutesLabel);
            endMinutesPanel.add(endMinutesComboBox);

            /*JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel durationLabel = new JLabel("Duration:");
            JComboBox<Integer> durationComboBox = new JComboBox<>();
            for(int i=1; i<=20000; i++){
                durationComboBox.addItem(i);
            }
            durationComboBox.setSelectedItem(event.getDuration());
            durationPanel.add(durationLabel);
            durationPanel.add(durationComboBox);*/

            Font dateFont1 = new Font("Arial", Font.PLAIN, 14); // Customize font properties
            endDayLabel.setFont(dateFont1);
            endMonthLabel.setFont(dateFont1);
            endYearLabel.setFont(dateFont1);
            endHourLabel.setFont(dateFont1);
            endMinutesLabel.setFont(dateFont1);
            //durationLabel.setFont(dateFont1);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                String newTitle = titleTextField.getText();
                String newDescription = descriptionTextField.getText();

                int newStartDay = (int) startDayComboBox.getSelectedItem();
                String newStartMonth = (String) startMonthComboBox.getSelectedItem();
                int newStartYear = (int) startYearComboBox.getSelectedItem();
                int newStartHour = (int) startHourComboBox.getSelectedItem();
                int newStartMinutes = (int) startMinutesComboBox.getSelectedItem();
                int newEndDay = (int) endDayComboBox.getSelectedItem();
                String newEndMonth = (String) endMonthComboBox.getSelectedItem();
                int newEndYear = (int) endYearComboBox.getSelectedItem();
                int newEndHour = (int) endHourComboBox.getSelectedItem();
                int newEndMinutes = (int) endMinutesComboBox.getSelectedItem();
                //int newduration = (int) durationComboBox.getSelectedItem();

                // Update the event object with the new values
                event.setTitle(newTitle);
                event.setDescription(newDescription);
                event.setStartDay(newStartDay);
                event.setStartMonth(getMonthNumber(newStartMonth));
                event.setStartYear(newStartYear);
                event.setStartHour(newStartHour);
                event.setStartMinute(newStartMinutes);
                event.setEndDay(newEndDay);
                event.setEndMonth(getMonthNumber(newEndMonth));
                event.setEndYear(newEndYear);
                event.setEndHour(newEndHour);
                event.setEndMinute(newEndMinutes);
                //event.setDuration(newduration);
                frame.dispose();
                }
            });

            editPanel.add(titlePanel);
            editPanel.add(descriptionPanel);

            if (event instanceof Appointements) {
                Appointements appointmentsEvent = (Appointements) event;

                editPanel.add(startDatePanel);
                editPanel.add(startMonthPanel);
                editPanel.add(startYearPanel);
                editPanel.add(startHourPanel);
                editPanel.add(startMinutesPanel);

                //if he chooses duration
                int userChoice = JOptionPane.showOptionDialog(null, "What do you want to edit","Give us your choice!", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, responces, null);
                if (userChoice == 0) { // Assuming YES_OPTION corresponds to 0
                    JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel durationLabel = new JLabel("Duration:");
                    JComboBox<Integer> durationComboBox = new JComboBox<>();
                    for(int i=1; i<=20000; i++){
                        durationComboBox.addItem(i);
                    }
                    durationComboBox.setSelectedItem(appointmentsEvent.getDuration());
                    durationLabel.setFont(dateFont1);
                    durationPanel.add(durationLabel);
                    durationPanel.add(durationComboBox);
                    int newduration = (int) durationComboBox.getSelectedItem();
                    appointmentsEvent.setDuration(newduration);

                    editPanel.add(durationPanel);
            
                } else if (userChoice == 1) {

                    editPanel.add(endDatePanel);
                    editPanel.add(endMonthPanel);
                    editPanel.add(endYearPanel);
                    editPanel.add(endHourPanel);
                    editPanel.add(endMinutesPanel);
                //editPanel.add(Box.createVerticalStrut(1));
                }
            }
            if (event instanceof Task) {
                Task taskevent = (Task) event;

                JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel statusLabel = new JLabel("Duration:");
                JComboBox statusComboBox = new JComboBox<>(options);
                statusComboBox.setSelectedItem(taskevent.getCompleteTask());
                statusLabel.setFont(dateFont1);
                statusPanel.add(statusLabel);
                statusPanel.add(statusComboBox);
                String newstatus = (String) statusComboBox.getSelectedItem();
                taskevent.setCompleteTask(newstatus);

                editPanel.add(statusPanel);

                editPanel.add(endDatePanel);
                editPanel.add(endMonthPanel);
                editPanel.add(endYearPanel);
                editPanel.add(endHourPanel);
                editPanel.add(endMinutesPanel);
            }

            editPanel.add(saveButton);        
        
            frame.add(editPanel);
            frame.setVisible(true);
        }

    // Helper method to get the month number from its name
    private int getMonthNumber(String monthName) {
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(monthName)) {
                return i + 1;
            }
        }
        return -1; // Return -1 if the month name is not found
    }

    // Helper method to get the month name from its number
    private String getMonthName(int monthNumber) {
        String[] months = new DateFormatSymbols().getMonths();
        if (monthNumber >= 1 && monthNumber <= 12) {
            return months[monthNumber - 1];
        }
        return ""; // Return empty string if the month number is out of range
    }
    private static Integer[] generateNumberArray(int start, int end) {
        Integer[] array = new Integer[end - start + 1];
        for (int i = start; i <= end; i++) {
        array[i - start] = i;
        }
        return array;
    }
    
        /*editPanel.add(titleLabel);
        editPanel.add(titleTextField);
        editPanel.add(descriptionLabel);
        editPanel.add(descriptionTextField);
    
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String newTitle = titleTextField.getText();
                String newDescription = descriptionTextField.getText();
    
                event.setTitle(newTitle);
                event.setDescription(newDescription);
    
                frame.dispose();
            }
        });
        editPanel.add(saveButton);

        //panel.add(titleLabel);
        //panel.add(descriptionLabel);

        frame.add(editPanel);
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
    }*/

}