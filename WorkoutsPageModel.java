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
public class WorkoutsPageModel extends AbstractFitnessModel{
    ArrayList<Workout> workoutsArrayList = new ArrayList<>();
    
    public void addWorkout(String workoutName, String sets, String repititions, String weight){
        String name = workoutName;
        int numberSets = Integer.parseInt(sets);
        int numberRepititions = Integer.parseInt(repititions);
        int numberWeight = Integer.parseInt(weight);
        
        Workout thisWorkout = new Workout(name, numberSets, numberRepititions, numberWeight);
        
        workoutsArrayList.add(thisWorkout);
        
        Workout.addRepititions(thisWorkout.getRepititions());
        Workout.addVolume(thisWorkout.getSets(), thisWorkout.getRepititions(), thisWorkout.getWeight());
        
        firePropertyChange("SetWorkouts", null, workoutsArrayList);
        firePropertyChange("AddRepititions", null, Workout.getTotalRepititions());
        firePropertyChange("AddVolume", null, Workout.getVolume());
        //firePropertyChange("Clear", null, null);
    }
    
    public void removeWorkout(Workout chosenWorkout){
        workoutsArrayList.remove(chosenWorkout);
        Workout.addRepititions(-(chosenWorkout.getRepititions()));
        Workout.addVolume(-chosenWorkout.getRepititions(), chosenWorkout.getSets(), chosenWorkout.getWeight());
        
        firePropertyChange("SetWorkouts", null, workoutsArrayList);
        firePropertyChange("AddRepititions", null, Workout.getTotalRepititions());
        firePropertyChange("AddVolume", null, Workout.getVolume());
    }
    
    public void clearWorkouts(){
        workoutsArrayList.clear();
        Workout.setZero();
        
        firePropertyChange("SetWorkouts", null, workoutsArrayList);
        firePropertyChange("AddRepititions", null, Workout.getTotalRepititions());
        firePropertyChange("AddVolume", null, Workout.getVolume());
    }
    
    public void showAlert(String alertText){
        firePropertyChange("DisplayAlert", null, alertText);
    }
    
    public boolean addWorkoutException(String string){
        boolean positive;
        try {
            int userNumber = Integer.parseInt(string);
            if(userNumber > 0){
                positive = true;
            }
            else{
                positive = false;
            }
        }
        catch(Exception e){
                positive = false;
        }
        return positive; 
    }
    /*
    Following code is referenced from Professor Wergeles.
    */
    
    public void handleOpenFile(File file){
        if (file != null) {
            FileInputStream fileIn; 
            try {
                fileIn = new FileInputStream(file.getPath());
                ObjectInputStream in = new ObjectInputStream(fileIn); 
                
                workoutsArrayList.clear();
                firePropertyChange("SetWorkouts", null, workoutsArrayList);
                Workout.setZero();
                firePropertyChange("AddRepititions", null, Workout.getTotalRepititions());
                firePropertyChange("AddVolume", null, Workout.getVolume());
                
                workoutsArrayList = (ArrayList<Workout>) in.readObject();
                
                in.close(); 
                fileIn.close(); 
                firePropertyChange("SetWorkouts", null, workoutsArrayList);
                
                for (int i = 0; i < workoutsArrayList.size(); i++) {
                    Workout.addRepititions(workoutsArrayList.get(i).getRepititions());
                    Workout.addVolume(workoutsArrayList.get(i).getSets(), workoutsArrayList.get(i).getRepititions(), workoutsArrayList.get(i).getWeight());
                } 
                firePropertyChange("AddRepititions", null, Workout.getTotalRepititions());
                firePropertyChange("AddVolume", null, Workout.getVolume());
                
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
                
                out.writeObject(workoutsArrayList);
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
