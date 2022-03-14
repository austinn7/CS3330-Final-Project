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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Austi
 */
public class GoalsPageController extends Switchable implements Initializable, java.io.Serializable, PropertyChangeListener {
    private Stage stage;

    @FXML
    private TextField goalsTextField;
    @FXML
    private TableView<Goal> goalsTableView;
    
    GoalsPageModel goalsPageModel;
    
    Alert error = new Alert(Alert.AlertType.ERROR);
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.stage = stage;
        goalsPageModel = new GoalsPageModel();
        
        goalsPageModel.addPropertyChangeListener(this);
        goalsPageModel.addGoal("Do 10 Pushups", false);
        goalsPageModel.addGoal("Eat Donut", true);
        
        TableColumn<Goal, String> goalColumn = new TableColumn<>("Goal");
        goalColumn.setMinWidth(300);
        goalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem()));
        
        TableColumn<Goal, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
        
        goalsTableView.getColumns().addAll(goalColumn, completedColumn);
    }    

    @FXML
    private void handleHomeButtonPressed(ActionEvent event) {
        goalsPageModel.switchScene("HomePage");
    }

    @FXML
    private void handleWorkoutsButtonPressed(ActionEvent event) {
        goalsPageModel.switchScene("WorkoutsPage");
    }


    @FXML
    private void handleAddButtonPressed(ActionEvent event) {
        if (goalsTextField.getText() == null || goalsTextField.getText().trim().isEmpty()){
            goalsPageModel.inputError("Please Insert a goal.");
        }
        else {
            goalsPageModel.addGoal(goalsTextField.getText(), true);
        }
    }

    @FXML
    private void handleRemoveButtonPressed(ActionEvent event) {
        if(goalsTableView.getSelectionModel().isEmpty()) {
            goalsPageModel.inputError("Please make a selection");
        }
        else {
            goalsPageModel.removeGoal((Goal) goalsTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleClearButtonPressed(ActionEvent event) {
        goalsPageModel.clearGoals();
    }

    /*
    The code for handleOpen and handleSave is written by Professor Wergeles.
    */
    
    @FXML
    private void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        goalsPageModel.handleOpenFile(file);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        goalsPageModel.handleSaveFile(file);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch(pce.getPropertyName()) {
            case "SwitchScene":
                Switchable.switchTo(pce.getNewValue().toString());
                break;
            case "setGoals":
                goalsTableView.setItems(FXCollections.observableArrayList((ArrayList<Goal>)pce.getNewValue()));
                break;
            case "ExceptionAlert":
                /* 
                The following code is referrenced from Professor Wergeles involving Object Serialization.
                */
                error.setTitle("Exception Dialog");
                error.setHeaderText("Exception!");
                error.setContentText(pce.getOldValue().toString());

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
                error.getDialogPane().setExpandableContent(expContent);
                error.showAndWait();
                break;
            default:
                break;
        }
        
    }
    
    

    
}
