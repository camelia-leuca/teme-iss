package com.example.controllers;

import com.example.dataAccess.BugDA;
import com.example.models.Bug;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgrammerController {

    @FXML
    private ListView<Bug> bugListView;

    private BugDA bugDAO;

    public ProgrammerController() {
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
    private void handleChangeStatus() {
        Bug selectedBug = bugListView.getSelectionModel().getSelectedItem();
        if (selectedBug == null) {
            showAlert("Error", "Please select a bug");
            return;
        }
        selectedBug.setStatus("In Progress");
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


    @FXML
    private void handleResolveBug() {
        Bug selectedBug = bugListView.getSelectionModel().getSelectedItem();
        if (selectedBug == null) {
            showAlert("Error", "Please select a bug");
            return;
        }

        bugDAO.deleteBug(selectedBug.getId());
        bugListView.getItems().remove(selectedBug);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}