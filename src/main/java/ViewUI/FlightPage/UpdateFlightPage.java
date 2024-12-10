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
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateFlightPage extends Page {
    public UpdateFlightPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Update Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(ADMIN);
        });
        TextField flightNumber = new TextField();
        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        TextField ticketID = new TextField();
        TextField ticketPrice = new TextField();

        Button updateFlight = new Button("Update Flight");
        updateFlight.setOnAction(e -> {
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultset = statement.executeQuery("SELECT * FROM flights WHERE flightNumber = " + flightNumber.getText());
                if (resultset.next()) {
                    try {
                        Connection connection1 = myJDBC.getConnection();
                        Statement statement1 = connection.createStatement();
                        String sql = "UPDATE flights SET departureCity = " + departureCity.getText() + ", arrivalCity = " + arrivalCity.getText() + ", departureDate = " + departureDate.getText() + ", arrivalDate = " + arrivalDate.getText() + ", departureTime = " + departureTime.getText() + ", arrivalTime = " + arrivalTime.getText() + ", ticketID = " + ticketID.getText() + ", ticketPrice = " + ticketPrice.getText() + " WHERE flightNumber = " + flightNumber.getText();
                        statement1.executeUpdate(sql);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    new Label("Flight does not exist");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Flight Number"), flightNumber), new VBox(new Label("Departure City"), departureCity), new VBox(new Label("Arrival City"), arrivalCity), new VBox(new Label("Departure Date"), departureDate), new VBox(new Label("Arrival Date"), arrivalDate), new VBox(new Label("Departure Time"), departureTime), new VBox(new Label("Arrival Time"), arrivalTime), new VBox(new Label("Ticket ID"), ticketID), new VBox(new Label("Ticket Price"), ticketPrice), updateFlight, backButton));
        return new Scene(root, 800, 800);

    }
}
