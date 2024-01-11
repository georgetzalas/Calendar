package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;

public class ViewPanel extends JPanel{
    private JPanel backButtonPanel, optionButtonsPanel, listPanel;
    private JButton back;
    private JButton day, week, month, pastday, pastweek, pastmonth, todo, due;
    private JList list;
    private JScrollPane scroll;
    private String[] data;

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

    private void createBackButton(){
        backButtonPanel = new JPanel();

        back = new JButton("Back");
        back.addActionListener(new Actions(this));

        backButtonPanel.setBackground(new Color(123,50,250));
        //backButtonPanel.setLayout(new BorderLayout());
        backButtonPanel.add(back);
    }

    private void createOptionButtons(){
        optionButtonsPanel = new JPanel();
    
        day = new JButton("Day");
        week = new JButton("Week");
        month = new JButton("Month");
        pastday = new JButton("Past Day");
        pastweek = new JButton("Past Week");
        pastmonth = new JButton("Past Month");
        todo = new JButton("Todo");
        due = new JButton("Due");

        day.addActionListener(new ButtonActions());
        week.addActionListener(new ButtonActions());
        month.addActionListener(new ButtonActions());
        pastday.addActionListener(new ButtonActions());
        pastweek.addActionListener(new ButtonActions());
        pastmonth.addActionListener(new ButtonActions());
        todo.addActionListener(new ButtonActions());
        due.addActionListener(new ButtonActions());
        
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

    private void createListPanel(){
        listPanel = new JPanel();
        
        list = new JList(data);
        scroll = new JScrollPane(list); 

        listPanel.add(scroll);

        listPanel.setBackground(new Color(123,50,250));
    }

    private void loadEvents(){
        Calendar.list.showEventsFromNow();
        data = Calendar.list.getData();
        list.setListData(data);
        Calendar.list.clearData();
    }

    private class ButtonActions implements ActionListener{
        private eventList elist = Calendar.list;

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("Day")) elist.day();
                           
            if(e.getActionCommand().equals("Week")) elist.week();
            
            if(e.getActionCommand().equals("Month")) elist.month();
            
            if(e.getActionCommand().equals("Past Day")) elist.pastday();
            
            if(e.getActionCommand().equals("Past Week")) elist.pastweek();
            
            if(e.getActionCommand().equals("Past Month")) elist.pastmonth();

            if(e.getActionCommand().equals("Todo")) elist.todo();

            if(e.getActionCommand().equals("Due")) elist.due();
             
            data = elist.getData();
            list.setListData(data);
            elist.clearData();
        }
    }
   
}
