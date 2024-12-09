module ViewUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ViewUI to javafx.fxml;
    exports ViewUI;
}