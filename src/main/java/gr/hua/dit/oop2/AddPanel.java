package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
public class AddPanel extends JPanel{
    JButton b1;
    //private MyCanvas canv = new MyCanvas();
    //private JFrame boxFrame;

    public AddPanel() {
        b1 = new JButton("Back");
        add(b1);
        b1.addActionListener(new Actions(this));
        //add(setCanvasVis());
        /*
        //setVisible(false);
        boxFrame = new JFrame();
        boxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boxFrame.setBounds(30, 30, 450, 450);
        boxFrame.getContentPane().add(MyCanvas(), BorderLayout.CENTER);
        */
    }
}