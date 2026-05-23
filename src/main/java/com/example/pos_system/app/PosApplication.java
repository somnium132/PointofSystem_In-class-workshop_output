package com.example.pos_system.app;

import com.example.pos_system.factory.AuthWindowFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class PosApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(AuthWindowFactory.createLoginScene());
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

