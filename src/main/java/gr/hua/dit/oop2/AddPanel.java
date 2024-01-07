package gr.hua.dit.oop2;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class AddPanel extends JPanel {
    JButton b1;
    MyCanvas canvasPanel;
    JFrame frame;
    public AddPanel() {
        b1 = new JButton("Back");
        add(b1);
        b1.addActionListener(new Actions(this));
        //MyCanvas.CalendarFrame.CanvasVis();
        SwingUtilities.invokeLater(MyCanvas::new);
    }
}