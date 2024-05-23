package com.example.controllers;

import com.example.models.Bug;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BugController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label statusLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBug(Bug bug) {
        titleLabel.setText(bug.getTitle());
        descriptionLabel.setText(bug.getDescription());
        statusLabel.setText(bug.getStatus());
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }
}
