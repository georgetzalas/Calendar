package gr.hua.dit.oop2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Class for opening and displaying files
public class FileViewer {
    //Construstor
    public FileViewer(){
        final JFrame frame = new JFrame("");

        frame.setSize(300,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JTextArea for displaying file content
        JTextArea area = new JTextArea(); 
        frame.add(new JScrollPane(area));

        // Create a file chooser with current directory as default
        final JFileChooser fc = new JFileChooser(".");
        // Set file selection mode to files only
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Show the file chooser dialog
        int returnVal = fc.showOpenDialog(frame);

        // If a file is selected
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File f = fc.getSelectedFile();
            if (f != null) {
                // Save the absolute path of the selected file
                Calendar.filePath = f.getAbsolutePath();
                // Set the flag to proceed
                Calendar.proceed = true;
            }
            frame.removeAll();
        }
    }
}
