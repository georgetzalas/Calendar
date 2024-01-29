package gr.hua.dit.oop2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class Actions implements ActionListener{
    private JPanel panel;

    public Actions(JPanel panel){
        this.panel = panel;
    }

    @Override
    // Method called when an action occurs
    public void actionPerformed(ActionEvent e) {
        panel.setVisible(false);

        // Checks which button was pressed based on the command
        if(e.getActionCommand().equals("Add Events")){
            // Calls a method to display the panel for adding events
            Calendar.calendarWindow.setAddPanelVis(true, panel);
        }else if(e.getActionCommand().equals("Edit Events")){
            // Calls a method to display the panel for editing events
            Calendar.calendarWindow.setFunctionPanelVis(true, panel);
        }else if(e.getActionCommand().equals("View Events")){
            // Calls a method to display the panel for viewing events
            Calendar.calendarWindow.setViewPanel(true, panel);
        }else if(e.getActionCommand().equals("Back")){
            // Calls a method to display the main panel
            Calendar.calendarWindow.setMainPanelVis(true, panel);
        }

    }    
}
