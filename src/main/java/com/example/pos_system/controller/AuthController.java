package com.example.pos_system.controller;

import com.example.pos_system.factory.AuthWindowFactory;
import com.example.pos_system.factory.PosWindowFactory;
import com.example.pos_system.repository.AuthRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final AuthRepository authRepository = new AuthRepository();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Missing data", "Enter your username and password.");
            return;
        }
        try {
            com.example.pos_system.model.UserAccount user = authRepository.authenticate(username, password);
            if (user != null) {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(PosWindowFactory.createDashboardScene(user));
                stage.setTitle("Point of Sale Dashboard");
            } else {
                showError("Login failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showError("Login error", e.getMessage());
        }
    }

    @FXML
    private void handleOpenSignUp(ActionEvent event) throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(AuthWindowFactory.createSignUpScene());
        stage.setTitle("Sign Up");
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

