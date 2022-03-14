/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 *
 * @author Austi
 */
public class GoalsPageModel extends AbstractFitnessModel {
    ArrayList<Goal> goalList = new ArrayList<>();
    
    public void addGoal(String goal, boolean completed){
        String goalContent = goal;
        boolean isCompleted = false;
        
        Goal newGoal = new Goal(goalContent, isCompleted);
        
        goalList.add(newGoal);
        firePropertyChange("setGoals", null, goalList);
        //firePropertyChange("ClearText", null, null);
    }
    
    public void removeGoal(Goal selectedGoal){
        goalList.remove(selectedGoal);
        
        firePropertyChange("setGoals", null, goalList);
    }
    
    public void clearGoals(){
        goalList.clear();
        
        firePropertyChange("setGoals", null, goalList);
    }
    
    public void handleOpenFile(File file){
        if (file != null) {
            FileInputStream fileIn; 
            try {
                fileIn = new FileInputStream(file.getPath());
                ObjectInputStream in = new ObjectInputStream(fileIn); 
                
                goalList.clear();
                firePropertyChange("SetGoals", null, goalList);
                goalList = (ArrayList<Goal>) in.readObject();
                
                in.close(); 
                fileIn.close(); 
                firePropertyChange("SetGoals", null, goalList);
                
            } catch (FileNotFoundException ex) {
                String message = "File not found exception occured while opening " + file.getPath(); 
                displayExceptionAlert(message, ex); 
                
            } catch (IOException ex) {
                String message = "IO exception occured while opening " + file.getPath(); 
                displayExceptionAlert(message, ex); 
                
            } catch (ClassNotFoundException ex) {
                String message = "Class not found exception occured while opening " + file.getPath(); 
                displayExceptionAlert(message, ex); 
            }
        }
    }
    
    public void handleSaveFile(File file){
        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file.getPath());
                ObjectOutputStream out = new ObjectOutputStream(fileOut); 
                
                out.writeObject(goalList);
                out.close(); 
                fileOut.close(); 
              
            } catch (FileNotFoundException ex) {
                String message = "File not found exception occured while saving to " + file.getPath(); 
                displayExceptionAlert(message, ex); 
                
            } catch (IOException ex) {
                String message = "IOException occured while saving to " + file.getPath();
                displayExceptionAlert(message, ex); 
            }
        }   
    }
    
    public void displayExceptionAlert(String message, Exception ex) {
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        
        firePropertyChange("ExceptionAlert", message, exceptionText);
    }
    
}
