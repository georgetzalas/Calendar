package gr.hua.dit.oop2;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.FlowLayout;

public class MainPanel extends JPanel{
    JButton b0 , b1 , b2 , b3;
    ImageIcon image; // ImageIcon for the background image
    JLabel imageLabel; // JLabel to display the background image

    // Constructor to initialize the MainPanel
    public MainPanel(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(new Color(123,50,250));

        // Initialize buttons for adding, editing, and viewing events
        b0 = new JButton("Add Events");
        b1 = new JButton("Edit Events");
        b2 = new JButton("View Events");
        
        // Set text and icon positions for button b0
        b0.setHorizontalTextPosition(JButton.CENTER);
        b0.setVerticalTextPosition(JButton.BOTTOM);
        b0.setIconTextGap(-20);

        // Set text and icon positions for button b1
        b1.setHorizontalTextPosition(JButton.CENTER);
        b1.setVerticalTextPosition(JButton.BOTTOM);
        b1.setIconTextGap(-20);

        // Set text and icon positions for button b2
        b2.setHorizontalTextPosition(JButton.CENTER);
        b2.setVerticalTextPosition(JButton.BOTTOM);
        b2.setIconTextGap(-20);

        add(b0);
        add(b1);
        add(b2);

        // Attach ActionListener to buttons for event handling
        b0.addActionListener(new Actions(this));
        b1.addActionListener(new Actions(this));
        b2.addActionListener(new Actions(this));
        
        // Load and display background image
        ImageIcon imageIcon = new ImageIcon("mainpic.png");
        Image image = imageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(image);

        imageLabel = new JLabel(scaledIcon);
        add(imageLabel);
        setVisible(true);
    }
}