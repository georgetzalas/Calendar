package gr.hua.dit.oop2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;

public class FunctionsPanel extends JPanel {
    private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;
    private JComboBox<String> yearComboBox, monthComboBox;
    private JButton prevButton, nextButton, backButton;
    private JPanel calendarPanel, topPanel, backButtonPanel;
    private JTextArea eventsTextArea;
    private Calendar calendar;
    String[] responces = { "Duration", "End-date" };
    String[] options = { "COMPLETED", "CANCELLED", "IN-PROCESS", "NEEDS-ACTION" };

    private int userChoice;
    private int newduration;
    private Appointements appointmentsEvent;
    private JComboBox<Integer> durationComboBox;
    private JComboBox statusComboBox;
    private Task taskevent;
    public FunctionsPanel() {

        setLayout(new BorderLayout());

        createBackButton();
        add(backButtonPanel, BorderLayout.SOUTH);

        //Create a Calendar object to manage the date
        calendar = Calendar.getInstance();

        //Create top panel containing the buttons for the month change
        JPanel topPanel = new JPanel(new FlowLayout());

        //Button for the previous month
        prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousMonth();
            }
        });
        topPanel.add(prevButton);

        //Create JComboBoxes for selecting year and month
        yearComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();

        //Add options for years
        for (int i = 1900; i <= 2100; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        //Add options for months

        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            monthComboBox.addItem(months[i]);
        }

        //Set initial options in Comboboxes based on current date
        yearComboBox.setSelectedItem(String.valueOf(calendar.get(Calendar.YEAR)));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]);

        //Listeners to change options in Comboboxes
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

        //Button for next month
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextMonth();
            }
        });
        topPanel.add(nextButton);
        //Add the top panel to the main panel
        add(topPanel, BorderLayout.NORTH);

        //Create a panel containing the dates of the month
        calendarPanel = new JPanel(new GridLayout(0, 7));
        add(calendarPanel, BorderLayout.CENTER);

        //Create a text field to display events
        eventsTextArea = new JTextArea(10, 20);
        eventsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventsTextArea);
        add(scrollPane, BorderLayout.EAST);

        updateCalendar();

        setVisible(false);
    }

    private void createBackButton() {
        
        backButtonPanel = new JPanel();

        backButton = new JButton("Back");
        backButton.addActionListener(new Actions(this));

        backButtonPanel.add(backButton);
    }

    //Method to update the calendar
    private void updateCalendar() {
        //Set the year in the Calendar based on the user's choice
        calendar.set(Calendar.YEAR, Integer.parseInt((String) yearComboBox.getSelectedItem()));

        //Set the month in the Calendar object based on the user's choice
        for (int i = 0; i < new DateFormatSymbols().getMonths().length - 1; i++) {
            if (monthComboBox.getSelectedItem().equals(new DateFormatSymbols().getMonths()[i])) {
                calendar.set(Calendar.MONTH, i);
                break;
            }
        }

        //Set the first day of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        //Cleaning the panel of the days of the week
        calendarPanel.removeAll();

        //Determine the days of the week
        String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

        //Add labels for the days of the week
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, JLabel.CENTER);
            calendarPanel.add(label);
        }

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Add blank cells for days preceding the first day of the month
        for (int i = 1; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        //Add buttons with the days of the month
        for (int i = 1; i <= daysInMonth; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayEvents(Integer.parseInt(button.getText()));
                }
            });
            calendarPanel.add(button);
        }

        //Redesign of the main panel
        revalidate();
        repaint();
    }

     //Method to display the previous month
    private void previousMonth() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //If the current month is January, we have to change the year
        if (month == 0) {
            year--;
            month = 11;
        } else {
            month--;
        }

        //Set the new options in the combo boxes
        yearComboBox.setSelectedItem(String.valueOf(year));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[month]);
    }

     //Method for displaying the next month
    private void nextMonth() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //If the current month is December, we need to change the year
        if (month == 11) {
            year++;
            month = 0;
        } else {
            month++;
        }

        //Set the new options in the combo boxes
        yearComboBox.setSelectedItem(String.valueOf(year));
        monthComboBox.setSelectedItem(new DateFormatSymbols().getMonths()[month]);
    }

    // private String eventsText;
    private void displayEvents(int day) {

        String selectedMonth = (String) monthComboBox.getSelectedItem();
        int month = getMonthNumber(selectedMonth);
        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
        ArrayList<Event> eventsForDay = getEventsForDay(day, month, year);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        JLabel eventTextLabel = new JLabel("Events for " + monthComboBox.getSelectedItem() + " " + day + ", "
                + yearComboBox.getSelectedItem() + ":");
        buttonsPanel.add(eventTextLabel);

        if (eventsForDay.isEmpty()) {
            buttonsPanel.add(new JLabel("No events for this day."));
            
        } else {
            
            for (Event event : eventsForDay) {
                
                JButton eventButton = new JButton(event.getTitle());
                eventButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EditEvents(event);
                    }
                });
                buttonsPanel.add(eventButton);
                
            }
        }
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

    private ArrayList<Event> getEventsForDay(int day, int month, int year) {
        ArrayList<Event> eventsForDay = new ArrayList<>();
        for (Event event : events) {
            if (event.getClass().equals(Task.class)) {
                if (event.getEndDay() == day && event.getEndMonth() == month && event.getEndYear() == year) {
                    eventsForDay.add(event);
                } else {
                    eventsTextArea.setText("");
                }
            } else {
                if (event.getStartDay() == day && event.getStartMonth() == month && event.getStartYear() == year) {
                    eventsForDay.add(event);
                } else {
                    eventsTextArea.setText("");
                }
            }

        }

        return eventsForDay;
    }

    private void EditEvents(Event event) {
        JFrame frame = new JFrame("Edit Events");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);

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
        descriptionTextField
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionTextField);

        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startDayLabel = new JLabel("Start Day:");

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

        Font dateFont1 = new Font("Arial", Font.PLAIN, 14); // Customize font properties
        endDayLabel.setFont(dateFont1);
        endMonthLabel.setFont(dateFont1);
        endYearLabel.setFont(dateFont1);
        endHourLabel.setFont(dateFont1);
        endMinutesLabel.setFont(dateFont1);
        
        editPanel.add(titlePanel);
        editPanel.add(descriptionPanel);

        if (event.getClass().equals(Appointements.class)) {
            appointmentsEvent = (Appointements) event;

            editPanel.add(startDatePanel);
            editPanel.add(startMonthPanel);
            editPanel.add(startYearPanel);
            editPanel.add(startHourPanel);
            editPanel.add(startMinutesPanel);

            // if he chooses duration
            userChoice = JOptionPane.showOptionDialog(null, "What do you want to edit", "Give us your choice!",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responces, null);
            if (userChoice == 0) { // Assuming YES_OPTION corresponds to 0
                JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel durationLabel = new JLabel("Duration:");
                durationComboBox = new JComboBox<>();
                for (int i = 1; i <= 20000; i++) {
                    durationComboBox.addItem(i);
                }
                durationComboBox.setSelectedItem(appointmentsEvent.getDuration());
                durationLabel.setFont(dateFont1);
                durationPanel.add(durationLabel);
                durationPanel.add(durationComboBox);

                editPanel.add(durationPanel);

            } else if (userChoice == 1) {

                editPanel.add(endDatePanel);
                editPanel.add(endMonthPanel);
                editPanel.add(endYearPanel);
                editPanel.add(endHourPanel);
                editPanel.add(endMinutesPanel);
            }
        }
        if (event.getClass().equals(Task.class)) {
            taskevent = (Task) event;

            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel statusLabel = new JLabel("Status:");
            statusComboBox = new JComboBox<>(options);
            statusComboBox.setSelectedItem(taskevent.getCompleteTask());
            statusLabel.setFont(dateFont1);
            statusPanel.add(statusLabel);
            statusPanel.add(statusComboBox);

            editPanel.add(statusPanel);

            editPanel.add(endDatePanel);
            editPanel.add(endMonthPanel);
            editPanel.add(endYearPanel);
            editPanel.add(endHourPanel);
            editPanel.add(endMinutesPanel);
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // Get the selected options and save them to variables
                String newTitle = titleTextField.getText();
                String newDescription = descriptionTextField.getText();

                int newStartDay = 0;
                String newStartMonth = null;
                int newStartYear = 0;
                int newStartHour = 0;
                int newStartMinutes = 0;

                int newEndDay = 0;
                String newEndMonth = null;
                int newEndYear = 0;
                int newEndHour = 0;
                int newEndMinutes = 0;

                if (event.getClass().equals(Appointements.class) && userChoice == 0) {
                    // Duration

                    newStartDay = (int) startDayComboBox.getSelectedItem();
                    newStartMonth = (String) startMonthComboBox.getSelectedItem();
                    newStartYear = (int) startYearComboBox.getSelectedItem();
                    newStartHour = (int) startHourComboBox.getSelectedItem();
                    newStartMinutes = (int) startMinutesComboBox.getSelectedItem();
                    newduration = (int) durationComboBox.getSelectedItem();
                    LocalDateTime APP = gr.hua.dit.oop2.eventManagement.plusDure(newStartDay,
                            getMonthNumber(newStartMonth), newStartYear, newStartHour, newStartMinutes,
                            newduration);

                    newEndDay = APP.getDayOfMonth();
                    newEndMonth = (String) endMonthComboBox.getSelectedItem();
                    newEndYear = APP.getYear();
                    newEndHour = APP.getHour();
                    newEndMinutes = APP.getMinute();
                    long compEndDate2 = gr.hua.dit.oop2.eventManagement.compareDates(newEndYear,
                            getMonthNumber(newEndMonth), newEndDay, newEndHour, newEndMinutes);
                    long compStartDate = gr.hua.dit.oop2.eventManagement.compareDates(newStartYear,
                            getMonthNumber(newStartMonth), newStartDay, newStartHour, newStartMinutes);

                    // Make the comparison
                    if (compStartDate < compEndDate2) {
                        // Update the event object with the new values
                        appointmentsEvent.setTitle(newTitle);
                        appointmentsEvent.setDescription(newDescription);
                        appointmentsEvent.setStartDay(newStartDay);
                        appointmentsEvent.setStartMonth(getMonthNumber(newStartMonth));
                        appointmentsEvent.setStartYear(newStartYear);
                        appointmentsEvent.setStartHour(newStartHour);
                        appointmentsEvent.setStartMinute(newStartMinutes);
                        appointmentsEvent.setEndDay(newEndDay);
                        appointmentsEvent.setEndMonth(getMonthNumber(newEndMonth));
                        appointmentsEvent.setEndYear(newEndYear);
                        appointmentsEvent.setEndHour(newEndHour);
                        appointmentsEvent.setEndMinute(newEndMinutes);
                        appointmentsEvent.setDuration(newduration);

                    } else {
                        // Display an error message if the date comparison fails
                        JOptionPane.showMessageDialog(null, "The end-date is before the start-date!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (event.getClass().equals(Appointements.class) && userChoice == 1) {
                    // END DAY
                    newStartDay = (int) startDayComboBox.getSelectedItem();
                    newStartMonth = (String) startMonthComboBox.getSelectedItem();
                    newStartYear = (int) startYearComboBox.getSelectedItem();
                    newStartHour = (int) startHourComboBox.getSelectedItem();
                    newStartMinutes = (int) startMinutesComboBox.getSelectedItem();

                    newEndDay = (int) endDayComboBox.getSelectedItem();
                    newEndMonth = (String) endMonthComboBox.getSelectedItem();
                    newEndYear = (int) endYearComboBox.getSelectedItem();
                    newEndHour = (int) endHourComboBox.getSelectedItem();
                    newEndMinutes = (int) endMinutesComboBox.getSelectedItem();

                    long compEndDate2 = gr.hua.dit.oop2.eventManagement.compareDates(newEndYear,
                            getMonthNumber(newEndMonth), newEndDay, newEndHour, newEndMinutes);
                    long compStartDate = gr.hua.dit.oop2.eventManagement.compareDates(newStartYear,
                            getMonthNumber(newStartMonth), newStartDay, newStartHour, newStartMinutes);

                    // Make the comparison
                    if (compStartDate < compEndDate2) {
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

                    } else {
                        // Display an error message if the date comparison fails
                        JOptionPane.showMessageDialog(null, "The end-date is before the start-date!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // TASK
                    newEndDay = (int) endDayComboBox.getSelectedItem();
                    newEndMonth = (String) endMonthComboBox.getSelectedItem();
                    newEndYear = (int) endYearComboBox.getSelectedItem();
                    newEndHour = (int) endHourComboBox.getSelectedItem();
                    newEndMinutes = (int) endMinutesComboBox.getSelectedItem();
                    String newstatus = (String) statusComboBox.getSelectedItem();

                    taskevent.setTitle(newTitle);
                    taskevent.setDescription(newDescription);
                    taskevent.setEndDay(newEndDay);
                    taskevent.setEndMonth(getMonthNumber(newEndMonth));
                    taskevent.setEndYear(newEndYear);
                    taskevent.setEndHour(newEndHour);
                    taskevent.setEndMinute(newEndMinutes);
                    taskevent.setCompleteTask(newstatus);
                }

                // Store the updated event
                frame.dispose();
                gr.hua.dit.oop2.Calendar.store.storeIcs();
            }
        });

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

}