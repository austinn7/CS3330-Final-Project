/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Austi
 */
public class WorkoutsPageController extends Switchable implements Initializable, PropertyChangeListener, java.io.Serializable {

    @FXML
    private TableView<Workout> workoutTableView;
    @FXML
    private TextField workoutTextField;
    @FXML
    private TextField setsTextField;
    @FXML
    private TextField repitionsTextField;
    @FXML
    private TextField weightTextField;
    @FXML
    private Text totalVolumeText;
    @FXML
    private Text totalRepititionsText;
    
    private Stage stage;
    
    Alert alert = new Alert(Alert.AlertType.ERROR);
    
    WorkoutsPageModel workoutsPageModel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.stage = stage;
        workoutsPageModel = new WorkoutsPageModel();
        workoutsPageModel.addPropertyChangeListener(this);
        
        TableColumn<Workout, String> nameColumn = new TableColumn<>("Workout Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem()));
        TableColumn<Workout, Integer> setsColumn = new TableColumn<>("Sets");
        setsColumn.setMinWidth(83);
        setsColumn.setCellValueFactory(new PropertyValueFactory<>("sets"));
        TableColumn<Workout, Integer> repsColumn = new TableColumn<>("Reps");
        repsColumn.setMinWidth(84);
        repsColumn.setCellValueFactory(new PropertyValueFactory<>("repititions"));
        TableColumn<Workout, Integer> weightColumn = new TableColumn<>("Weight");
        weightColumn.setMinWidth(84);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        workoutTableView.getColumns().addAll(nameColumn, setsColumn, repsColumn, weightColumn);
        
        workoutsPageModel.addWorkout("Squat", "5", "3", "135");
    }    

    @FXML
    private void handleHomeButtonPressed(ActionEvent event) {
        workoutsPageModel.switchScene("HomePage");
    }

    @FXML
    private void handleGoalsButtonPressed(ActionEvent event) {
        workoutsPageModel.switchScene("GoalsPage");
    }

    @FXML
    private void handleAddButtonPressed(ActionEvent event) {
        if (workoutTextField.getText() == null || workoutTextField.getText().trim().isEmpty())
        {
            workoutsPageModel.showAlert("No workout entered.");
        }
        else if(workoutsPageModel.addWorkoutException(setsTextField.getText()) &&
                workoutsPageModel.addWorkoutException(repitionsTextField.getText()) &&
                workoutsPageModel.addWorkoutException(weightTextField.getText())) 
        {
            workoutsPageModel.addWorkout(workoutTextField.getText(), setsTextField.getText(), repitionsTextField.getText(), weightTextField.getText());
        }
        else 
        {
            workoutsPageModel.showAlert("Positive Integers Only");
        }
    }

    @FXML
    private void handleRemoveButtonPressed(ActionEvent event) {
        if(workoutTableView.getSelectionModel().isEmpty())
        {
            workoutsPageModel.showAlert("Please select workout to remove.");
        }
        else
        {
            workoutsPageModel.removeWorkout((Workout) workoutTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleClearButtonPressed(ActionEvent event) {
        workoutsPageModel.clearWorkouts();
    }
    
    @FXML
    private void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        workoutsPageModel.handleOpenFile(file);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        workoutsPageModel.handleSaveFile(file);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()){
            case "AddRepititions":
                totalRepititionsText.setText(pce.getNewValue().toString());
                break;
            case "AddVolume":
                totalVolumeText.setText(pce.getNewValue().toString());
                break;
            case "SetWorkouts":
                workoutTableView.setItems(FXCollections.observableArrayList((ArrayList<Workout>)pce.getNewValue()));
                break;
            case "Clear":
                workoutTextField.clear();
                setsTextField.clear();
                repitionsTextField.clear();
                weightTextField.clear();
            case "SwitchScene":
                Switchable.switchTo(pce.getNewValue().toString());
            case "DisplayAlert":
                alert.setTitle("ERROR");
                alert.setHeaderText("ERROR");
                alert.setContentText(pce.getNewValue().toString());
                alert.showAndWait();
            case "ExceptionAlert":
                /* 
                Code for ExceptionAlert is referenced from Professor Wergeles
                */
                alert.setTitle("Exception Dialog");
                alert.setHeaderText("Exception!");
                alert.setContentText(pce.getOldValue().toString());

                Label label = new Label("The exception stacktrace was:");
                TextArea textArea = new TextArea(pce.getNewValue().toString());
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);
                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);

                // Set expandable Exception into the dialog pane.
                alert.getDialogPane().setExpandableContent(expContent);
                alert.showAndWait();
                break;
            default:
                break;
        }
    }
    
}
