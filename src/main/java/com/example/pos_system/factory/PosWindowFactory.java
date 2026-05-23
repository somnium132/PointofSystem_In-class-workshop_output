package com.example.pos_system.factory;

import com.example.pos_system.controller.PosDashboardController;
import com.example.pos_system.model.UserAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class PosWindowFactory {

    public static Scene createDashboardScene(UserAccount user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PosWindowFactory.class.getResource("/com/example/pos_system/pos-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        
        PosDashboardController controller = fxmlLoader.getController();
        controller.initializeData(user);
        
        return scene;
    }
}
