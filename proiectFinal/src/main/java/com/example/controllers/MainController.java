package com.example.controllers;

import com.example.dataAccess.UserDA;
import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = authenticate(username, password);
        if (user != null) {
            switch (user.getRole()) {
                case "admin":
                    loadView("/AdminView.fxml", "Admin Panel");
                    break;
                case "programmer":
                    loadView("/ProgrammerView.fxml", "Programmer Panel");
                    break;
                case "tester":
                    loadView("/TesterView.fxml", "Tester Panel");
                    break;
            }
        } else {
            showAlert("Invalid username or password");
        }
        usernameField.clear();
        passwordField.clear();
    }

    private User authenticate(String username, String password) {
        UserDA data = new UserDA();
        List<User> users = data.getAllUsers();

        for (User user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void loadView(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
