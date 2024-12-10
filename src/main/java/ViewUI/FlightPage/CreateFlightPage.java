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

import static CodingLogicPackage.CodingLogic.generateFlightID;

public class CreateFlightPage extends Page {
    public CreateFlightPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Create Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(ADMIN);
        });

        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        TextField ticketsRemaining = new TextField();
        TextField ticketPrice = new TextField();

        Button createFlight = new Button("Create Flight");
        createFlight.setOnAction(e -> {
            int flightNumbers = generateFlightID(1);
            int ticketID = generateFlightID(1);
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO flights (flightID, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketsRemaining, ticketID) VALUES ('" + flightNumbers + "', '" + departureCity.getText() + "', '" + arrivalCity.getText() + "', '" + departureDate.getText() + "', '" + arrivalDate.getText() + "', '" + departureTime.getText() + "', '" + arrivalTime.getText() + "', '" + ticketsRemaining.getText() + "', '" + ticketID + "')";
                statement.executeUpdate(sql);
                if (statement.executeUpdate(sql) > 0) {
                    new Label("Flight created successfully");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Departure City"), departureCity), new VBox(new Label("Arrival City"), arrivalCity), new VBox(new Label("Departure Date"), departureDate), new VBox(new Label("Arrival Date"), arrivalDate), new VBox(new Label("Departure Time"), departureTime), new VBox(new Label("Arrival Time"), arrivalTime), new VBox(new Label("Ticket ID"), ticketsRemaining), new VBox(new Label("Ticket Price"), ticketPrice), createFlight, backButton));
        return new Scene(root, 800, 800);

    }
}
