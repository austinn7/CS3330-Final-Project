/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

/**
 *
 * @author Austi
 */
public class Goal implements TrackerItem, java.io.Serializable {
    private String goal;
    private boolean completed;
    
    public Goal(String goal, boolean completed){
        this.goal = goal;
        this.completed = completed;
    }
    
    @Override
    public String getItem(){
        return goal;
    }
    
    public boolean getCompleted(){
        return completed;
    }
}
