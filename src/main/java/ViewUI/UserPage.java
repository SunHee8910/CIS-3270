package ViewUI;

import Database.myJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static ViewUI.PageManager.LANDING;
import static ViewUI.PageManager.SEARCH_FLIGHT;

public class UserPage extends Page {

    public UserPage(PageManager pageManager, Username user) {
        super(pageManager);
        this.user = user;
    }

    @Override
    public Scene getScene() {
        String username = user.getUsername();
        Label welcome = new Label("Welcome, " + username);
        Text text = new Text("✈️ User Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Logout");
        Button searchFlights = new Button("Search for Flights");
        ObservableList<String> flights = FXCollections.observableArrayList();

        // Create a ListView and set the items to the flight list
        ListView<String> flightListView = new ListView<>();

        try {
            Connection connection = myJDBC.getConnection();
            Statement statement = connection.createStatement();
            ResultSet userid = statement.executeQuery("SELECT userID FROM users WHERE username = '" + username + "'");
            ResultSet bookids = statement.executeQuery("SELECT bookingID FROM bookings WHERE userID = '" + userid + "'");
            while (bookids.next()) {
                ResultSet ticketids = statement.executeQuery("SELECT ticketID FROM bookigs WHERE bookingID = '" + bookids + "'");
                ResultSet flightDetails = statement.executeQuery("SELECT departureCity, arrivalCity, departureTime, arrivalTime, departureDate, arrivalDate FROM flights WHERE ticketID = '" + ticketids + "'");
                flightListView.getItems().add(flightDetails.getString("departureCity") + " to " + flightDetails.getString("arrivalCity") + "  Departure Date: " + flightDetails.getString("departureDate") + "  Arrival Date: " + flightDetails.getString("arrivalDate") + "  Departure Time: " + flightDetails.getString("departureTime") + "  Arrival Time: " + flightDetails.getString("arrivalTime") + "   Booking ID: " + bookids.getString("bookingID") + "   Ticket ID: " + ticketids.getString("ticketID"));
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // Make sure it's scrollable
        flightListView.setPrefHeight(200);

        searchFlights.setOnAction(e -> {
            Username user1= new Username(username);
            this.pageManager.setScene(SEARCH_FLIGHT, user1);
        });
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING, user);
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, welcome, new VBox(10, new VBox(new Label("Search for Flights"), searchFlights), flightListView, backButton));

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);
    }
}