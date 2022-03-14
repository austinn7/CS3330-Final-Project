/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Austi
 */
public abstract class AbstractFitnessModel {
    protected PropertyChangeSupport propertyChangeSupport;
    
    public AbstractFitnessModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue){
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public void switchScene(String scene){
        firePropertyChange("SwitchScene", null, scene);
    }
    
    public void inputError(String string){
        firePropertyChange("Error", null, string);
    }
}
