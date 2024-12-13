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
import org.example.Flight;

import java.sql.Connection;
import java.sql.Statement;

import static CodingLogicPackage.CodingLogic.generateFlightID;
import static Database.FlightDBQuery.addFlight;
import static ViewUI.PageManager.ADMIN_PAGE;

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
            this.pageManager.setScene(ADMIN_PAGE);
        });

        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        VBox root = new VBox(5);


        Button createFlight = new Button("Create Flight");
        createFlight.setOnAction(e -> {

//             flightNumbers = generateFlightID(1);
              String ticketID = String.valueOf(generateFlightID(1));
              Flight flight = new Flight(departureCity.getText(), arrivalCity.getText(), departureDate.getText(), arrivalDate.getText(), departureTime.getText(), arrivalTime.getText());
              if (addFlight(flight)) {
                    root.getChildren().add(new Label("Flight created successfully"));
              }
              else {
                  root.getChildren().add(new Label("Flight not created"));
              }
//            try {
//                Connection connection = myJDBC.getConnection();
//                Statement statement = connection.createStatement();
//                String sql = "INSERT INTO flights (flightID, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketsRemaining, ticketID) VALUES ('" + flightNumbers + "', '" + departureCity.getText() + "', '" + arrivalCity.getText() + "', '" + departureDate.getText() + "', '" + arrivalDate.getText() + "', '" + departureTime.getText() + "', '" + arrivalTime.getText() + "', '" + ticketsRemaining.getText() + "', '" + ticketID + "')";
//                statement.executeUpdate(sql);
//                if (statement.executeUpdate(sql) > 0) {
//                    new Label("Flight created successfully");
//                }
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
        });

        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #F0FFFF");
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Departure City"), departureCity), new VBox(new Label("Arrival City"), arrivalCity), new VBox(new Label("Departure Date"), departureDate), new VBox(new Label("Arrival Date"), arrivalDate), new VBox(new Label("Departure Time"), departureTime), new VBox(new Label("Arrival Time"), arrivalTime), createFlight, backButton));
        return new Scene(root, 800, 800);

    }
}
