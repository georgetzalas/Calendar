package gr.hua.dit.oop2;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
    JButton b1, b2;
    public MainPanel(){
        b1 = new JButton("Add/Edit Events");
        b2 = new JButton("View Events");
        add(b1);
        add(b2);
        b1.addActionListener(new Actions(this));
        b2.addActionListener(new Actions(this));
        setVisible(true);
    }
}
