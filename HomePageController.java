/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Austi
 */
public class HomePageController extends Switchable implements Initializable, PropertyChangeListener {
    
    @FXML
    private Button workoutsButton;
    @FXML
    private Button goalsButton;
    
    HomePageModel homePageModel;
    
    Alert alert;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homePageModel = new HomePageModel();
        homePageModel.addPropertyChangeListener(this);
    }    

    @FXML
    private void handleWorkoutsButtonPressed(ActionEvent event) {
        homePageModel.switchScene("WorkoutsPage");
    }

    @FXML
    private void handleGoalsButtonPressed(ActionEvent event) {
        homePageModel.switchScene("GoalsPage");
    }
    
    @FXML
    private void handleAboutAppButtonPressed(ActionEvent event) {
        String header = "About this App:";
        String alertText = "This is an app that is made to help people track their fitness"
                + " and diet goals quickly and easily. It can help you keep track of your workouts,"
                + " your dietary goals, and even any other goals you might have. From the home page, you"
                + " can navigate to either the Workouts page or the Goals page.\n\tIn the Workouts page, you"
                + " can input whatever workout you have done by typing in the name of the workout, how many"
                + " sets you completed, how many reps were in each set, and how much weight was lifted during"
                + " the set. The app will not allow you to input negative values or leave one of the input"
                + " fields empty. Otherwise, you will receive an error. At the bottom of the workouts page,"
                + " you will be able to see how much total weight you have lifted and how many total repititions"
                + " you have completed. This can be very useful data for someone who is trying to get into working"
                + " out. You can also remove a workout by selectecting it and pressing the remove button. If you do"
                + " not select a workout, an error message will be brought up. You can also clear all workouts by"
                + " pressing the clear button.\n\tIn the Goals Page, you can add or remove any current goals that you"
                + " may be working on. With these goals, you can mark whether or not you have completed them or not."
                + " You can also add, remove, or clear any of the goals similarly to the workouts.\n\t"
                + " Finally, you can save or open any of the data you might put into the app and you can keep track of"
                + " your progress. Thank you for using the App. Enjoy!";
        homePageModel.showAlert(header, alertText);
    }
    
    @FXML
    private void handleAboutDeveloperButtonPressed(ActionEvent event) {
        String header = "About the Developer:";
        String alertText = "The developer of this app, Austin Neumann, is a sophomore studying"
                + " Computer Science at the University of Missouri - Columbia. He wrote this program"
                + " with the intention of helping himself and others keep track of both their fitness and"
                + " their diet. He is looking to one day work for a company like Google or Microsoft where"
                + " apps like this are created frequently. He is looking to finish his degree in May of 2023"
                + " and plans to continue his coding career afterwards.";
        homePageModel.showAlert(header, alertText);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case "SwitchScene":
                Switchable.switchTo(pce.getNewValue().toString());
                break;
            case "DisplayAlert":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("About");
                alert.setContentText(pce.getNewValue().toString());
                alert.setHeaderText(pce.getOldValue().toString());
                alert.showAndWait();
            default:
                break;
        }
    }
    
    
    
}
