package gr.hua.dit.oop2;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;

public class FunctionsPanel extends JPanel{
    JButton b1;
    
    public FunctionsPanel(){
        b1 = new JButton("Back");
        add(b1);
        b1.addActionListener(new Actions(this));
        setVisible(false);

    }
}
