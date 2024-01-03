package gr.hua.dit.oop2;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalendarWindow {
    private JFrame frame = new JFrame("Test");
    private MainPanel mainPanel = new MainPanel();
    private FunctionsPanel functionsPanel = new FunctionsPanel();
    private ViewPanel viewPanel = new ViewPanel();

    public CalendarWindow(){
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
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
}
