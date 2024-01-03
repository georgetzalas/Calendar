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
    public void actionPerformed(ActionEvent e) {
        panel.setVisible(false);

        if(e.getActionCommand().equals("Add/Edit Events")){
            Calendar.calendarWindow.setFunctionPanelVis(true, panel);
        }else if(e.getActionCommand().equals("View Events")){
            Calendar.calendarWindow.setViewPanel(true, panel);
        }else if(e.getActionCommand().equals("Back")){
            Calendar.calendarWindow.setMainPanelVis(true, panel);
        }

    }    
}
