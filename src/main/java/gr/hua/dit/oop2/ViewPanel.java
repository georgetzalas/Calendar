package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;

// Panel for viewing events
public class ViewPanel extends JPanel{
    private JPanel backButtonPanel, optionButtonsPanel, listPanel;
    private JButton back;
    private JButton day, week, month, pastday, pastweek, pastmonth, todo, due;
    private JList list;
    private JScrollPane scroll;
    private String[] data;

    // Constructor
    public ViewPanel(){
        setLayout(new BorderLayout());

        createBackButton();
        createOptionButtons();
        createListPanel();

        add(backButtonPanel, BorderLayout.SOUTH);
        add(optionButtonsPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);

        loadEvents();

        setVisible(false);
    }

    // Method to create the back button
    private void createBackButton(){
        backButtonPanel = new JPanel();

        back = new JButton("Back");
        back.addActionListener(new Actions(this));

        backButtonPanel.setBackground(new Color(123,50,250));
        //backButtonPanel.setLayout(new BorderLayout());
        backButtonPanel.add(back);
    }

    // Method to create option buttons
    private void createOptionButtons(){
        optionButtonsPanel = new JPanel();
        
        // Initialize option buttons
        day = new JButton("Day");
        week = new JButton("Week");
        month = new JButton("Month");
        pastday = new JButton("Past Day");
        pastweek = new JButton("Past Week");
        pastmonth = new JButton("Past Month");
        todo = new JButton("Todo");
        due = new JButton("Due");

        // ActionListener for each option button
        day.addActionListener(new ButtonActions());
        week.addActionListener(new ButtonActions());
        month.addActionListener(new ButtonActions());
        pastday.addActionListener(new ButtonActions());
        pastweek.addActionListener(new ButtonActions());
        pastmonth.addActionListener(new ButtonActions());
        todo.addActionListener(new ButtonActions());
        due.addActionListener(new ButtonActions());
        
        // Add option buttons to the panel
        optionButtonsPanel.add(day);
        optionButtonsPanel.add(week);
        optionButtonsPanel.add(month);
        optionButtonsPanel.add(pastday);
        optionButtonsPanel.add(pastweek);
        optionButtonsPanel.add(pastmonth);
        optionButtonsPanel.add(todo);
        optionButtonsPanel.add(due);
        
        optionButtonsPanel.setBackground(new Color(123,50,250));
    }

     // Method to create the list panel
    private void createListPanel(){
        listPanel = new JPanel();
        
        list = new JList(data);
        scroll = new JScrollPane(list); 

        listPanel.add(scroll);

        listPanel.setBackground(new Color(123,50,250));
    }

    // Method to load events into the list
    private void loadEvents(){
        eventList eventList= Calendar.list;
        if(eventList == null){
            System.out.println("Please provide a file before entering the View Panel");
            // Exit if no event list is available
            System.exit(1);
            //throw new NullPointerException("Please provide a file before entering the View Panel");
        }
        // Show events
        eventList.showEventsFromNow();

        // Get event data
        data = eventList.getData();

        // Set data to list
        list.setListData(data);

        // Clear data after loading
        eventList.clearData();
    }

    // ActionListener for option buttons
    private class ButtonActions implements ActionListener{
        private eventList elist = Calendar.list;

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform action based on the button clicked

            if(e.getActionCommand().equals("Day")) elist.day();
                           
            if(e.getActionCommand().equals("Week")) elist.week();
            
            if(e.getActionCommand().equals("Month")) elist.month();
            
            if(e.getActionCommand().equals("Past Day")) elist.pastday();
            
            if(e.getActionCommand().equals("Past Week")) elist.pastweek();
            
            if(e.getActionCommand().equals("Past Month")) elist.pastmonth();

            if(e.getActionCommand().equals("Todo")) elist.todo();

            if(e.getActionCommand().equals("Due")) elist.due();
             
             // Get updated data
            data = elist.getData();

            // Set data to list
            list.setListData(data);

            // Clear data
            elist.clearData();
        }
    }
   
}
