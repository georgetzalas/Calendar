package gr.hua.dit.oop2;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon; // ImageIcon for the main window icon

public class CalendarWindow {
    private JFrame frame = new JFrame("Calendar");
    private MainPanel mainPanel;
    private AddPanel addPanel;
    private FunctionsPanel functionsPanel;
    private ViewPanel viewPanel;
    private ImageIcon mainimage;

    // Constructor
    public CalendarWindow(){
        mainPanel = new MainPanel();

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setVisible(true);
        // Load the main window icon
        mainimage = new ImageIcon("mainpic.png");
        // Set the icon for the frame
        frame.setIconImage(mainimage.getImage());
        // Create the menu bar
        createMenuBar();
        
    }
    // Method to create the menu bar
    private void createMenuBar(){
        MenuBar mBar = new MenuBar();
        Menu m1 = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new FileViewer(); //Open a file viewer when "Open" is clicked
            }
            
        });
        m1.add(open);
        mBar.add(m1);
        frame.setMenuBar(mBar);    
    }

    // Method to set the visibility of the main panel
    public void setMainPanelVis(boolean visible, JPanel panel){
        mainPanel = new MainPanel();

        frame.remove(panel);
        mainPanel.setVisible(visible);
        frame.add(mainPanel);
    }
    //Method to set the visibility of the panel for adding events
    public void setAddPanelVis(boolean visible, JPanel panel){
        addPanel = new AddPanel();

        frame.remove(panel);
        addPanel.setVisible(visible);
        frame.add(addPanel);
    }
    // Method to set the visibility of the panel for functions
    public void setFunctionPanelVis(boolean visible, JPanel panel){
        functionsPanel = new FunctionsPanel();

        frame.remove(panel);
        functionsPanel.setVisible(visible);
        frame.add(functionsPanel);
    }

    // Method to set the visibility of the panel for viewing events
    public void setViewPanel(boolean visible, JPanel panel){
        viewPanel = new ViewPanel();

        frame.remove(panel);
        viewPanel.setVisible(visible);
        frame.add(viewPanel);
    }

}
