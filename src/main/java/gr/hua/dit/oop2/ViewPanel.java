package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.JButton;

public class ViewPanel extends JPanel{
    JButton b1;
    public ViewPanel(){
        b1 = new JButton("Back");
        add(b1);
        b1.addActionListener(new Actions(this));
        setVisible(false);
    }
}
