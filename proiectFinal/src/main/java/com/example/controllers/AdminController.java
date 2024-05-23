package com.example.controllers;

import com.example.dataAccess.UserDA;
import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AdminController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField roleField;
    @FXML
    private ListView<User> userListView;

    private UserDA userDAO;

    public AdminController() {
        userDAO = new UserDA();
    }

    @FXML
    private void initialize() {
        userListView.getItems().addAll(userDAO.getAllUsers());
    }

    @FXML
    private void handleAddUser() {
        String name = nameField.getText();
        String password = passwordField.getText();
        String role = roleField.getText();

        if (name.isEmpty() || password.isEmpty() || role.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        if (!role.equals("Tester") && !role.equals("Programmer") && !role.equals("Admin") && !role.equals("tester") && !role.equals("programmer") && !role.equals("admin")) {
            showAlert("Error", "Invalid role. Please choose tester, programmer, or admin.");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);

        userDAO.addUser(user);
        userListView.getItems().add(user);
        clearFields();
    }

    @FXML
    private void handleUpdateUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "Please select a user");
            return;
        }

        String name = nameField.getText();
        String password = passwordField.getText();
        String role = roleField.getText();

        if (name.isEmpty() && password.isEmpty() && role.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        if (!role.equals("Tester") && !role.equals("Programmer") && !role.equals("Admin") && !role.equals("tester") && !role.equals("programmer") && !role.equals("admin")) {
            showAlert("Error", "Invalid role. Please choose Tester, Programmer, or Admin.");
            return;
        }

        selectedUser.setName(name);
        selectedUser.setPassword(password);
        selectedUser.setRole(role);

        userDAO.updateUser(selectedUser);
        userListView.refresh();
        clearFields();
    }

    @FXML
    private void handleDeleteUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "Please select a user");
            return;
        }

        userDAO.deleteUser(selectedUser.getId());
        userListView.getItems().remove(selectedUser);
    }

    private void clearFields() {
        nameField.clear();
        passwordField.clear();
        roleField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
