package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.JButton;

public class StatPanel extends JPanel{
    JButton b1;
    public StatPanel(){
        b1 = new JButton("Back");
        add(b1);
        b1.addActionListener(new Actions(this));
        //setVisible(false);
    }
}