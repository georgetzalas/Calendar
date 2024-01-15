package gr.hua.dit.oop2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

public class AddPanel extends JPanel {
    /*private ArrayList<Event> events = gr.hua.dit.oop2.Calendar.events;
    private JButton b;
    private JPanel backButtonPanel, addpanel;
    private Integer choice;
    private JFrame frame = gr.hua.dit.oop2.CalendarWindow.frame;

    String[] responces = { "Duration", "End-date" };
    String[] options = { "Appointment", "Task" };
    private JButton saveButton = new JButton("Save");

    public AddPanel() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setBackground(new Color(123, 50, 250));
        frame.setResizable(false);
        frame.setSize(800, 700);

        setLayout(new BorderLayout());
        createBackButtonPanel();
        setBackground(new Color(123, 50, 250));
        add(backButtonPanel, BorderLayout.SOUTH);

        choice = JOptionPane.showOptionDialog(null, "What do you want to add", "Give us your choice",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        addpanel = new JPanel();
        addpanel.setLayout(new GridLayout(0, 1)); // Vertical layout
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (choice == 0) {

            JLabel titleLabel = new JLabel("Title:");
            JTextField titleTextField = new JTextField("           ");
            titleTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleTextField.getPreferredSize().height));
            titlePanel.add(titleLabel);
            titlePanel.add(titleTextField);

            JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel descriptionLabel = new JLabel("Description:");
            JTextField descriptionTextField = new JTextField("          ");
            descriptionTextField.setAutoscrolls(false);
            descriptionTextField
                    .setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
            descriptionPanel.add(descriptionLabel);
            descriptionPanel.add(descriptionTextField);

            JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startDayLabel = new JLabel("Start Day:");
            JComboBox<Integer> startDayComboBox = new JComboBox<>();
            for (int i = 1; i <= 31; i++) {
                startDayComboBox.addItem(i);
            }

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

            startMonthPanel.add(startMonthLabel);
            startMonthPanel.add(startMonthComboBox);

            JPanel startYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startYearLabel = new JLabel("Start Year:");
            JComboBox<Integer> startYearComboBox = new JComboBox<>();
            for (int i = 1900; i <= 2100; i++) {
                startYearComboBox.addItem(i);
            }

            startYearPanel.add(startYearLabel);
            startYearPanel.add(startYearComboBox);

            JPanel startHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startHourLabel = new JLabel("Start Hour:");
            JComboBox<Integer> startHourComboBox = new JComboBox<>();
            for (int i = 0; i <= 23; i++) {
                startHourComboBox.addItem(i);
            }
            // startHourComboBox.setSelectedItem(event.getStartHour());
            startHourPanel.add(startHourLabel);
            startHourPanel.add(startHourComboBox);

            JPanel startMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel startMinutesLabel = new JLabel("Start Minutes:");
            JComboBox<Integer> startMinutesComboBox = new JComboBox<>();
            for (int i = 0; i <= 59; i++) {
                startMinutesComboBox.addItem(i);
            }
            // startMinutesComboBox.setSelectedItem(event.getStartMinute());
            startMinutesPanel.add(startMinutesLabel);
            startMinutesPanel.add(startMinutesComboBox);

            Font largeFont = new Font("Arial", Font.PLAIN, 16); // Customize font properties
            titleLabel.setFont(largeFont);
            descriptionLabel.setFont(largeFont);

            Font dateFont = new Font("Arial", Font.PLAIN, 14); // Customize font properties
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
            // JComboBox<Integer> endDayComboBox = new JComboBox<>(new
            // DefaultComboBoxModel<>(
            // generateNumberArray(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));
            // endDayComboBox.setSelectedItem(event.getEndDay());
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
            // endMonthComboBox.setSelectedItem(getMonthName(event.getEndMonth()));
            endMonthPanel.add(endMonthLabel);
            endMonthPanel.add(endMonthComboBox);

            JPanel endYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endYearLabel = new JLabel("End Year:");
            JComboBox<Integer> endYearComboBox = new JComboBox<>();
            for (int i = 1900; i <= 2100; i++) {
                endYearComboBox.addItem(i);
            }
            // endYearComboBox.setSelectedItem(event.getEndYear());
            endYearPanel.add(endYearLabel);
            endYearPanel.add(endYearComboBox);

            JPanel endHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endHourLabel = new JLabel("End Hour:");
            JComboBox<Integer> endHourComboBox = new JComboBox<>();
            for (int i = 0; i <= 23; i++) {
                endHourComboBox.addItem(i);
            }
            // endHourComboBox.setSelectedItem(event.getEndHour());
            endHourPanel.add(endHourLabel);
            endHourPanel.add(endHourComboBox);

            JPanel endMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endMinutesLabel = new JLabel("End Minutes:");
            JComboBox<Integer> endMinutesComboBox = new JComboBox<>();
            for (int i = 0; i <= 59; i++) {
                endMinutesComboBox.addItem(i);
            }
            // endMinutesComboBox.setSelectedItem(event.getEndMinute());
            endMinutesPanel.add(endMinutesLabel);
            endMinutesPanel.add(endMinutesComboBox);

            Font dateFont1 = new Font("Arial", Font.PLAIN, 14); // Customize font properties
            endDayLabel.setFont(dateFont1);
            endMonthLabel.setFont(dateFont1);
            endYearLabel.setFont(dateFont1);
            endHourLabel.setFont(dateFont1);
            endMinutesLabel.setFont(dateFont1);
            int userChoice = JOptionPane.showOptionDialog(null, "What do you want to edit", "Give us your choice!",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responces, null);
            if (userChoice == 0) { // Assuming YES_OPTION corresponds to 0
                JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel durationLabel = new JLabel("Duration:");
                JComboBox<Integer> durationComboBox = new JComboBox<>();
                for (int i = 1; i <= 20000; i++) {
                    durationComboBox.addItem(i);
                }

                durationLabel.setFont(dateFont1);
                durationPanel.add(durationLabel);
                durationPanel.add(durationComboBox);
                int newduration = (int) durationComboBox.getSelectedItem();
                addpanel.add(titlePanel);
                addpanel.add(descriptionPanel);
                addpanel.add(startDatePanel);
                addpanel.add(startMonthPanel);
                addpanel.add(startYearPanel);
                addpanel.add(startHourPanel);
                addpanel.add(startMinutesPanel);
                addpanel.add(durationPanel);
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newTitle = titleTextField.getText();
                        String newDescription = descriptionTextField.getText();

                        int newStartDay = (int) startDayComboBox.getSelectedItem();
                        String newStartMonth = (String) startMonthComboBox.getSelectedItem();
                        int newStartYear = (int) startYearComboBox.getSelectedItem();
                        int newStartHour = (int) startHourComboBox.getSelectedItem();
                        int newStartMinutes = (int) startMinutesComboBox.getSelectedItem();
                        Appointements ev = new Appointements(newTitle, newDescription,
                                newStartDay, getMonthNumber((String) startMonthComboBox.getSelectedItem()),
                                newStartYear,
                                newStartHour, newStartMinutes, 0, 0,
                                0, 0, 0,
                                newduration);
                        events.add(ev);
                        frame.dispose();
                    }
                });
            } else if (userChoice == 1) {
                addpanel.add(titlePanel);
                addpanel.add(descriptionPanel);
                addpanel.add(startDatePanel);
                addpanel.add(startMonthPanel);
                addpanel.add(startYearPanel);
                addpanel.add(startHourPanel);
                addpanel.add(startMinutesPanel);
                addpanel.add(endDatePanel);
                addpanel.add(endMonthPanel);
                addpanel.add(endYearPanel);
                addpanel.add(endHourPanel);
                addpanel.add(endMinutesPanel);
                // editPanel.add(Box.createVerticalStrut(1));
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
                        Appointements ev = new Appointements(newTitle, newDescription,
                                newStartDay, getMonthNumber((String) startMonthComboBox.getSelectedItem()),
                                newStartYear,
                                newStartHour, newStartMinutes, newEndDay, newEndYear,
                                getMonthNumber((String) endMonthComboBox.getSelectedItem()), newEndHour, newEndMinutes,
                                -1);
                        events.add(ev);
                        frame.dispose();
                    }
                });
            }
            addpanel.setBackground(new Color(123, 50, 250));
            addpanel.setVisible(true);
        } else if (choice == 1) {
            JLabel titleLabel = new JLabel("Title:");
            JTextField titleTextField = new JTextField();
            titleTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleTextField.getPreferredSize().height));
            titlePanel.add(titleLabel);
            titlePanel.add(titleTextField);

            JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel descriptionLabel = new JLabel("Description:");
            JTextField descriptionTextField = new JTextField();
            descriptionTextField.setAutoscrolls(false);
            descriptionTextField
                    .setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
            descriptionPanel.add(descriptionLabel);
            descriptionPanel.add(descriptionTextField);
            JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endDayLabel = new JLabel("End Day:");
            JComboBox<Integer> endDayComboBox = new JComboBox<>();
            for (int i = 1; i <= 31; i++) {
                endDayComboBox.addItem(i);
            }
            // JComboBox<Integer> endDayComboBox = new JComboBox<>(new
            // DefaultComboBoxModel<>(
            // generateNumberArray(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));
            // endDayComboBox.setSelectedItem(event.getEndDay());
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
            // endMonthComboBox.setSelectedItem(getMonthName(event.getEndMonth()));
            endMonthPanel.add(endMonthLabel);
            endMonthPanel.add(endMonthComboBox);

            JPanel endYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endYearLabel = new JLabel("End Year:");
            JComboBox<Integer> endYearComboBox = new JComboBox<>();
            for (int i = 1900; i <= 2100; i++) {
                endYearComboBox.addItem(i);
            }
            // endYearComboBox.setSelectedItem(event.getEndYear());
            endYearPanel.add(endYearLabel);
            endYearPanel.add(endYearComboBox);

            JPanel endHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endHourLabel = new JLabel("End Hour:");
            JComboBox<Integer> endHourComboBox = new JComboBox<>();
            for (int i = 0; i <= 23; i++) {
                endHourComboBox.addItem(i);
            }
            // endHourComboBox.setSelectedItem(event.getEndHour());
            endHourPanel.add(endHourLabel);
            endHourPanel.add(endHourComboBox);

            JPanel endMinutesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel endMinutesLabel = new JLabel("End Minutes:");
            JComboBox<Integer> endMinutesComboBox = new JComboBox<>();
            for (int i = 0; i <= 59; i++) {
                endMinutesComboBox.addItem(i);
            }
            // endMinutesComboBox.setSelectedItem(event.getEndMinute());
            endMinutesPanel.add(endMinutesLabel);
            endMinutesPanel.add(endMinutesComboBox);

            Font dateFont1 = new Font("Arial", Font.PLAIN, 14); // Customize font properties
            endDayLabel.setFont(dateFont1);
            endMonthLabel.setFont(dateFont1);
            endYearLabel.setFont(dateFont1);
            endHourLabel.setFont(dateFont1);
            endMinutesLabel.setFont(dateFont1);
            addpanel.add(titlePanel);
            addpanel.add(descriptionPanel);
            addpanel.add(endDatePanel);
            addpanel.add(endMonthPanel);
            addpanel.add(endYearPanel);
            addpanel.add(endHourPanel);
            addpanel.add(endMinutesPanel);

            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String newTitle = titleTextField.getText();
                    String newDescription = descriptionTextField.getText();
                    int newEndDay = (int) endDayComboBox.getSelectedItem();
                    String newEndMonth = (String) endMonthComboBox.getSelectedItem();
                    int newEndYear = (int) endYearComboBox.getSelectedItem();
                    int newEndHour = (int) endHourComboBox.getSelectedItem();
                    int newEndMinutes = (int) endMinutesComboBox.getSelectedItem();
                    Task evt = new Task(newTitle, newDescription, 0, 0, 0, 0, 0, newEndDay,
                            getMonthNumber((String) newEndMonth), newEndYear, newEndHour, newEndMinutes, "IN-PROCESS");
                    events.add(evt);

                }
            });

            addpanel.setBackground(new Color(123, 50, 250));
            addpanel.setVisible(true);

        }
        
        addpanel.add(saveButton);
        frame.add(addpanel);
        frame.setVisible(true);

    }

    private void createBackButtonPanel() {
        backButtonPanel = new JPanel();

        b = new JButton("Back");

        b.addActionListener(new Actions(this));
        backButtonPanel.add(b);
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

    */
}
