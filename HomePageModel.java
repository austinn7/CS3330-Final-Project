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
public class HomePageModel extends AbstractFitnessModel {
    public void showAlert(String header, String alertText){
        firePropertyChange("DisplayAlert", header, alertText);
    }
}
