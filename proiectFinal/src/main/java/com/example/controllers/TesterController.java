package com.example.controllers;

import com.example.dataAccess.BugDA;
import com.example.models.Bug;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TesterController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ListView<Bug> bugListView;

    private BugDA bugDAO;

    public TesterController() {
        bugDAO = new BugDA();
    }

    @FXML
    private void initialize() {
        bugListView.getItems().addAll(bugDAO.getAllBugs());
        bugListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Bug selectedBug = bugListView.getSelectionModel().getSelectedItem();
                if (selectedBug != null) {
                    showBugDetails(selectedBug);
                }
            }
        });
    }

    @FXML
    private void handleAddBug() {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        Bug bug = new Bug();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setStatus("Unsolved");

        bugDAO.addBug(bug);
        bugListView.getItems().add(bug);
        clearFields();
    }

    private void showBugDetails(Bug bug) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BugView.fxml"));
            Parent root = loader.load();

            BugController controller = loader.getController();
            controller.setBug(bug);

            Stage stage = new Stage();
            stage.setTitle("Bug Details");
            stage.setScene(new Scene(root));
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleUpdateBug() {
        Bug selectedBug = bugListView.getSelectionModel().getSelectedItem();
        if (selectedBug != null) {
            selectedBug.setTitle(titleField.getText());
            selectedBug.setDescription(descriptionField.getText());
            bugDAO.updateBug(selectedBug);
            bugListView.refresh();
        } else {
            showAlert("Error", "Please select a bug to update");
        }
    }

    @FXML
    private void handleDeleteBug() {
        Bug selectedBug = bugListView.getSelectionModel().getSelectedItem();
        if (selectedBug != null) {
            bugDAO.deleteBug(selectedBug.getId());
            bugListView.getItems().remove(selectedBug);
        } else {
            showAlert("Error", "Please select a bug to delete");
        }
    }
}
