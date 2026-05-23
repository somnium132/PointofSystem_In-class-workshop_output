module com.example.pos_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.pos_system.controller to javafx.fxml;
    opens com.example.pos_system.model to javafx.base;
    exports com.example.pos_system.app;
}
