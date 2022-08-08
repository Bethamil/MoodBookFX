module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens emiel to javafx.fxml;
    exports emiel;
    exports emiel.controller;
    opens emiel.controller to javafx.fxml;
}
