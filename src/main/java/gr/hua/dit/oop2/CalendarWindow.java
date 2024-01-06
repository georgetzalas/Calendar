package gr.hua.dit.oop2;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;



public class CalendarWindow {
    private JFrame frame = new JFrame("Calendar");
    private MainPanel mainPanel = new MainPanel();
    private AddPanel addPanel = new AddPanel();
    private FunctionsPanel functionsPanel = new FunctionsPanel();
    private ViewPanel viewPanel = new ViewPanel();
    private StatPanel statPanel = new StatPanel();
    private ImageIcon mainimage = new ImageIcon("mainpic.png");

    public CalendarWindow(){
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(mainimage.getImage());
        createMenuBar();
        
    }

    private void createMenuBar(){
        MenuBar mBar = new MenuBar();
        Menu m1 = new Menu("File");
        m1.add(new MenuItem("Open"));
        mBar.add(m1);
        frame.setMenuBar(mBar);    
    }

    public void setMainPanelVis(boolean visible, JPanel panel){
        frame.remove(panel);
        mainPanel.setVisible(visible);
        frame.add(mainPanel);
    }

    public void setAddPanelVis(boolean visible, JPanel panel){
        frame.remove(panel);
        mainPanel.setVisible(visible);
        frame.add(addPanel);
    }
    public void setFunctionPanelVis(boolean visible, JPanel panel){
        frame.remove(panel);
        functionsPanel.setVisible(visible);
        frame.add(functionsPanel);
    }

    public void setViewPanel(boolean visible, JPanel panel){
        frame.remove(panel);
        viewPanel.setVisible(visible);
        frame.add(viewPanel);
    }

    public void setStatPanelVis(boolean visible, JPanel panel){
        frame.remove(panel);
        statPanel.setVisible(visible);
        frame.add(statPanel);
    } 
}
