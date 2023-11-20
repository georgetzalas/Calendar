package gr.hua.dit.oop2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class icsOperations{
    protected File file;
    protected ArrayList<Event> events;
    private String fileName;

    public icsOperations(String fileName){
        this.fileName = fileName;
        file = new File(this.fileName);
        events = new ArrayList<>();
    }

    public boolean checkFile(){
        return file.exists() && file.isFile();
    }

    public void createFile(){
        try{
            file = new File(this.fileName);
            if(!file.createNewFile()){
                System.out.println("File already exists");
            }
        }catch(IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public ArrayList<Event> getEvents(){ return events; }

    public void setEvents(){
        this.events = events;
    }

}