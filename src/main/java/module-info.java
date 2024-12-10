module ViewUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens ViewUI to javafx.fxml;
    exports ViewUI;
    exports ViewUI.LoginPage;
    opens ViewUI.LoginPage to javafx.fxml;
    exports ViewUI.FlightPage;
    opens ViewUI.FlightPage to javafx.fxml;
}