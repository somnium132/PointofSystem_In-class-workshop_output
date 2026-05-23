package com.example.pos_system.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public final class AuthWindowFactory {
    private AuthWindowFactory() {
    }

    public static Scene createLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(AuthWindowFactory.class.getResource("/com/example/pos_system/login-view.fxml"));
        return new Scene(loader.load(), 420, 300);
    }

    public static Scene createSignUpScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(AuthWindowFactory.class.getResource("/com/example/pos_system/signup-view.fxml"));
        return new Scene(loader.load(), 420, 360);
    }
}

