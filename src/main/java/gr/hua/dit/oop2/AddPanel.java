package gr.hua.dit.oop2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

public class AddPanel extends JPanel {
    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;
    private JComboBox<String> yearComboBox, monthComboBox;
    private JButton prevButton, nextButton, backButton, saveButton = new JButton("Save");
    private JPanel calendarPanel, topPanel, backButtonPanel;
    private JTextArea eventsTextArea;
    private Calendar calendar;
    Integer i;
    String[] responces = { "Duration", "End-date" };
    String[] options = { "Appointment", "Task" };

    public AddPanel() {
        Event a = new Event("Test", "Test Desc", 1, 2, 2023, 1, 3, 5, 10, 2025, 5, 1);
        events.add(a);

        setLayout(new BorderLayout());

        createBackButton();
        add(backButtonPanel, BorderLayout.SOUTH);

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

    private void createBackButton() {
        // topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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

        String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, JLabel.CENTER);
            calendarPanel.add(label);
        }

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (i = 1; i <= daysInMonth; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayEvents(Integer.parseInt(button.getText()));
                    AddEvents(Integer.parseInt(button.getText()),(String)monthComboBox.getSelectedItem(),Integer.parseInt((String) yearComboBox.getSelectedItem()));
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

    private void displayEvents(int day) {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        int month = getMonthNumber(selectedMonth);
        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());

    }
    //Constractor of Add
    private void AddEvents(int day,String selectedMonth,int year) {
        //Create the main frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);
        //Create the main panel
        JPanel addPanel = new JPanel();

        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        //Create the textfields of title and description
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleTextField = new JTextField("               ");
        titleTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleTextField.getPreferredSize().height));
        titlePanel.add(titleLabel);
        titlePanel.add(titleTextField);

        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionTextField = new JTextField("                 ");
        descriptionTextField.setAutoscrolls(false);
        descriptionTextField
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionTextField);
        //Creating the panels and the comboboxes of the needed data that the user must fill(start-date)
        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startDayLabel = new JLabel("Start Day:");
        
        JComboBox<Integer> startDayComboBox = new JComboBox<>(new DefaultComboBoxModel<>(
                generateNumberArray(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));
        startDayComboBox.setSelectedItem(day);
        startDatePanel.add(startDayLabel);
        startDatePanel.add(startDayComboBox);

        JPanel startMonthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startMonthLabel = new JLabel("Start Month:");
        JComboBox<String> startMonthComboBox = new JComboBox<>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            startMonthComboBox.addItem(months[i]);
        }
        startMonthComboBox.setSelectedItem(selectedMonth);
        startMonthPanel.add(startMonthLabel);
        startMonthPanel.add(startMonthComboBox);

        JPanel startYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startYearLabel = new JLabel("Start Year:");
        JComboBox<Integer> startYearComboBox = new JComboBox<>();
        for (int i = 1900; i <= 2100; i++) {
            startYearComboBox.addItem(i);
        }
        startYearComboBox.setSelectedItem(year);
        startYearPanel.add(startYearLabel);
        startYearPanel.add(startYearComboBox);

        JPanel startHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startHourLabel = new JLabel("Start Hour:");
        JComboBox<Integer> startHourComboBox = new JComboBox<>();
        for (int i = 0; i <= 23; i++) {
            startHourComboBox.addItem(i);
        }
        startHourPanel.add(startHourLabel);
        startHourPanel.add(startHourComboBox);

        JPanel startMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startMinutesLabel = new JLabel("Start Minutes:");
        JComboBox<Integer> startMinutesComboBox = new JComboBox<>();
        for (int i = 0; i <= 59; i++) {
            startMinutesComboBox.addItem(i);
        }
        startMinutesPanel.add(startMinutesLabel);
        startMinutesPanel.add(startMinutesComboBox);
        // Customize font properties
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
        endMonthPanel.add(endMonthLabel);
        endMonthPanel.add(endMonthComboBox);

        JPanel endYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel endYearLabel = new JLabel("End Year:");
        JComboBox<Integer> endYearComboBox = new JComboBox<>();
        for (int i = 1900; i <= 2100; i++) {
            endYearComboBox.addItem(i);
        }
        endYearPanel.add(endYearLabel);
        endYearPanel.add(endYearComboBox);

        JPanel endHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel endHourLabel = new JLabel("End Hour:");
        JComboBox<Integer> endHourComboBox = new JComboBox<>();
        for (int i = 0; i <= 23; i++) {
            endHourComboBox.addItem(i);
        }
        endHourPanel.add(endHourLabel);
        endHourPanel.add(endHourComboBox);

        JPanel endMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel endMinutesLabel = new JLabel("End Minutes:");
        JComboBox<Integer> endMinutesComboBox = new JComboBox<>();
        for (int i = 0; i <= 59; i++) {
            endMinutesComboBox.addItem(i);
        }
        endMinutesPanel.add(endMinutesLabel);
        endMinutesPanel.add(endMinutesComboBox);
        // Customize font properties
        Font dateFont1 = new Font("Arial", Font.PLAIN, 14); endDayLabel.setFont(dateFont1);
        endMonthLabel.setFont(dateFont1);
        endYearLabel.setFont(dateFont1);
        endHourLabel.setFont(dateFont1);
        endMinutesLabel.setFont(dateFont1);
        frame.dispose();
        //Add the panels of the title and description in the window 
        addPanel.add(titlePanel);
        addPanel.add(descriptionPanel);
        //Get the choice of user if they want to add ,task or appointment
        int choice = JOptionPane.showOptionDialog(null, "What do you want to add", "Give us your choice!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        //if they chooses appointment
            if (choice == 0) {
            //add the start-date panels 
            addPanel.add(startDatePanel);
            addPanel.add(startMonthPanel);
            addPanel.add(startYearPanel);
            addPanel.add(startHourPanel);
            addPanel.add(startMinutesPanel);

            //Get the choice of the user if they want to add duration or end-date
            int userChoice = JOptionPane.showOptionDialog(null, "What do you want to edit", "Give us your choice!",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responces, null);
            // if he chooses duration
            if (userChoice == 0) { // Assuming YES_OPTION corresponds to 0
                //Creates the duration panel and Combobox
                JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel durationLabel = new JLabel("Duration:");
                JComboBox<Integer> durationComboBox = new JComboBox<>();
                for (int i = 1; i <= 20000; i++) {
                    durationComboBox.addItem(i);
                }
                //add it to the panel
                durationLabel.setFont(dateFont1);
                durationPanel.add(durationLabel);
                durationPanel.add(durationComboBox);
                
                addPanel.add(durationPanel);
                //Create the actions of the save button
                //THIS WAS COMMENTED 
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //Save the selected options
                        String newTitle = titleTextField.getText();
                        String newDescription = descriptionTextField.getText();

                        int newStartDay = (int) startDayComboBox.getSelectedItem();
                        String newStartMonth = (String) startMonthComboBox.getSelectedItem();
                        int newStartYear = (int) startYearComboBox.getSelectedItem();
                        int newStartHour = (int) startHourComboBox.getSelectedItem();
                        int newStartMinutes = (int) startMinutesComboBox.getSelectedItem();
                        int newduration = (int) durationComboBox.getSelectedItem();
                        LocalDateTime APP = gr.hua.dit.oop2.eventManagement.plusDure(newStartDay,
                                getMonthNumber(newStartMonth), newStartYear, newStartHour, newStartMinutes,
                                newduration);
                        Integer endday = APP.getDayOfMonth();
                        Integer endmonth = APP.getMonthValue();
                        Integer endyear = APP.getYear();
                        Integer endhour = APP.getHour();
                        Integer endminute = APP.getMinute();
                        gr.hua.dit.oop2.Appointements ev = new Appointements(newTitle, newDescription,
                                newStartDay, getMonthNumber((String) startMonthComboBox.getSelectedItem()),
                                newStartYear,
                                newStartHour, newStartMinutes, endday, endmonth, endyear, endhour, endminute,
                                newduration);
                        //save the new object to the public static list event

                        events.add(ev);

                        
                        //Create the new object
                        
                        gr.hua.dit.oop2.Calendar.store.storeIcs();
                        frame.dispose();
                    }
                });
                // ...

saveButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            // Save the selected options
            String newTitle = titleTextField.getText();
            String newDescription = descriptionTextField.getText();

            int newStartDay = (int) startDayComboBox.getSelectedItem();
            String newStartMonth = (String) startMonthComboBox.getSelectedItem();
            int newStartYear = (int) startYearComboBox.getSelectedItem();
            int newStartHour = (int) startHourComboBox.getSelectedItem();
            int newStartMinutes = (int) startMinutesComboBox.getSelectedItem();

            // Check if the selected start day and month combination is valid
            if (newStartDay>gr.hua.dit.oop2.eventManagement.errorMonth(getMonthNumber(newStartMonth),newStartYear)) {
                throw new IllegalArgumentException("Invalid combination of start day and month.");
            }

            int newduration = (int) durationComboBox.getSelectedItem();
            LocalDateTime APP = gr.hua.dit.oop2.eventManagement.plusDure(newStartDay,
                    getMonthNumber(newStartMonth), newStartYear, newStartHour, newStartMinutes,
                    newduration);
            Integer endday = APP.getDayOfMonth();
            Integer endmonth = APP.getMonthValue();
            Integer endyear = APP.getYear();
            Integer endhour = APP.getHour();
            Integer endminute = APP.getMinute();
            gr.hua.dit.oop2.Appointements ev = new Appointements(newTitle, newDescription,
                    newStartDay, getMonthNumber(newStartMonth),
                    newStartYear, newStartHour, newStartMinutes, endday, endmonth, endyear, endhour, endminute,
                    newduration);
            // Save the new object to the public static list event
            events.add(ev);

            // Create the new object
            gr.hua.dit.oop2.Calendar.store.storeIcs();
            frame.dispose();
        } catch (Exception ex) {
            // Handle the exception (e.g., display an error message)
            ex.printStackTrace(); // Print the stack trace for debugging
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

// ...

            //if the user choose the end-date option
            } else if (userChoice == 1) {
                //add the end-date panels to the frame
                addPanel.add(endDatePanel);
                addPanel.add(endMonthPanel);
                addPanel.add(endYearPanel);
                addPanel.add(endHourPanel);
                addPanel.add(endMinutesPanel);
                //create the save button and set the actions of it
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Save the selected options
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
                
                            long compenddate2 = gr.hua.dit.oop2.eventManagement.compareDates(newEndYear, getMonthNumber(newEndMonth), newEndDay, newEndHour, newEndMinutes);
                            long compstartdate = gr.hua.dit.oop2.eventManagement.compareDates(newStartYear, getMonthNumber(newStartMonth), newStartDay, newStartHour, newStartMinutes);
                
                            // MAKE THE COMPARISON
                            if (compstartdate < compenddate2) {
                                // MAKE THE APPOINTMENT AS SOON AS THE COMPARISON IS CORRECT
                                gr.hua.dit.oop2.Appointements ev = new Appointements(newTitle, newDescription,
                                        //(String) startMonthComboBox.getSelectedItem()
                                        newStartDay, getMonthNumber(newStartMonth),
                                        newStartYear,
                                        newStartHour, newStartMinutes, newEndDay, getMonthNumber(newEndMonth), newEndYear, newEndHour, newEndMinutes,
                                        -1);
                                // save the new object to the public static list event
                                events.add(ev);
                                gr.hua.dit.oop2.Calendar.store.storeIcs();
                                // PRINT THE NEW OBJECT
                                System.out.printf("%s\t\n", ev.getTitle());
                            } else {
                                // ELSE PRINT THE MESSAGE AND GET BACK TO THE TOP OF THE LOOP
                                JOptionPane.showMessageDialog(null, "The end-date is before the start-date!", "ERROR", JOptionPane.ERROR_MESSAGE);
                                return; // Exit the method to avoid executing the remaining code
                            }
                
                            // close the frame
                            frame.dispose();
                        } catch (Exception ex) {
                            // Handle other exceptions if necessary
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                
            }
        }
        //if the user chooses the option Task
        if (choice == 1) {
            //set the 
            endDayComboBox.setSelectedItem(day);
            endMonthComboBox.setSelectedItem(selectedMonth);
            endYearComboBox.setSelectedItem(year);
            //add the end-date panels
            addPanel.add(endDatePanel);
            addPanel.add(endMonthPanel);
            addPanel.add(endYearPanel);
            addPanel.add(endHourPanel);
            addPanel.add(endMinutesPanel);
            //Create the save button and set the actions of it
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Get the selected options and save them to variables
                        String newTitle = titleTextField.getText();
                        String newDescription = descriptionTextField.getText();
                        int newEndDay = (int) endDayComboBox.getSelectedItem();
                        String newEndMonth = (String) endMonthComboBox.getSelectedItem();
                        int newEndYear = (int) endYearComboBox.getSelectedItem();
                        int newEndHour = (int) endHourComboBox.getSelectedItem();
                        int newEndMinutes = (int) endMinutesComboBox.getSelectedItem();
                        // Check if the selected start day and month combination is valid
                        if (newEndDay>gr.hua.dit.oop2.eventManagement.errorMonth(getMonthNumber(newEndMonth),newEndYear)) {
                            throw new IllegalArgumentException("Invalid combination of start day and month.");
                        }
                        // Create the new object - Task
                        Task task = new Task(newTitle, newDescription, 0, 0, 0, 0, 0, newEndDay,
                                getMonthNumber(newEndMonth), newEndYear, newEndHour, newEndMinutes, "IN-PROCESS");
            
                        // Add the new object to the public static list events
                        events.add(task);
            
                        gr.hua.dit.oop2.Calendar.store.storeIcs();
                        // Close the frame
                        frame.dispose();
                    } catch (Exception ex) {
                        // Handle exceptions if necessary
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
        }
        //add the save button to the panel
        addPanel.add(saveButton);
        //add the addpanel to the frame 
        frame.add(addPanel);
        //set visible the frame
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
    // Helper method to get the number of the month day
    private static Integer[] generateNumberArray(int start, int end) {
        Integer[] array = new Integer[end - start + 1];
        for (int i = start; i <= end; i++) {
            array[i - start] = i;
        }
        return array;
    }
}