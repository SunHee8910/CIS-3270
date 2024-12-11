package ViewUI.FlightPage;

import Database.myJDBC;
import ViewUI.Page;
import ViewUI.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.Statement;

import static ViewUI.PageManager.ADMIN_PAGE;

public class DeleteFlightPage extends Page {
    public DeleteFlightPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Delete Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(ADMIN_PAGE);
        });
        TextField flightNumber = new TextField();
        Button deleteFlight = new Button("Delete Flight");
        deleteFlight.setOnAction(e -> {
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM flights WHERE flightNumber = " + flightNumber.getText();
                statement.executeUpdate(sql);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Flight Number"), flightNumber), deleteFlight, backButton));
        return new Scene(root, 800, 800);

    }
}
