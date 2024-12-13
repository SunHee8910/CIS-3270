package ViewUI.FlightPage;

import Database.myJDBC;
import ViewUI.Page;
import ViewUI.PageManager;
import ViewUI.Username;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import Database.myJDBC;
import ViewUI.Page;
import ViewUI.PageManager;
import ViewUI.Username;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.Flight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static CodingLogicPackage.CodingLogic.generateFlightID;
import static ViewUI.PageManager.USER_PAGE;

public class SearchFlightPage extends Page {


    public SearchFlightPage(PageManager pageManager, Username user) {
        super(pageManager);
        this.user = user;
    }

    @Override
    public Scene getScene() {
        String username = user.getUsername();
        Text text = new Text("✈️ Search Flights ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F0FFFF");

        // Create the search form at the top
        VBox searchBox = createSearchForm();
        root.setTop(searchBox);

        // Create the list view for flights
        ListView<String> flightListView = new ListView<>();
        flightListView.setPrefHeight(200);
        root.setCenter(flightListView);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(USER_PAGE, this.user);
        });

        // Create the booking button
        Button bookButton = new Button("Book Flight");
        root.setBottom(bookButton);
        bookButton.setDisable(true);

        // Retrieve input fields
        TextField departureField = (TextField) searchBox.lookup("#departureField");
        TextField destinationField = (TextField) searchBox.lookup("#destinationField");
        DatePicker datePicker = (DatePicker) searchBox.lookup("#datePicker");
        TextField departureTimeField = (TextField) searchBox.lookup("#departureTimeField");

        // Search button action
        Button searchButton = (Button) searchBox.lookup("#searchButton");
        searchButton.setOnAction(event -> searchFlights(flightListView, departureField, destinationField, datePicker, departureTimeField));

        // Handle flight selection and booking
        flightListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            bookButton.setDisable(newSelection == null);  // Enable/Disable the book button
        });

        // Handle booking the selected flight
        bookButton.setOnAction(event -> {
            String selectedFlight = flightListView.getSelectionModel().getSelectedItem();
            if (selectedFlight != null) {
                // Assuming you have a method to book the flight (e.g., storing it in a database or log
                if (bookFlight(selectedFlight, username)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Flight Booking");
                    alert.setContentText("Your flight has been successfully booked");
                    alert.showAndWait();
                    this.pageManager.reload();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Flight Booking");
                    alert.setContentText("There was an error booking your flight. Please try again later.");
                    alert.showAndWait();
                }
            }
        });

        return new Scene(root, 800, 600);
    }

    // Create the search form UI
    private VBox createSearchForm() {
        Label departureLabel = new Label("Departure City:");
        TextField departureField = new TextField();
        departureField.setId("departureField");
        departureField.setPromptText("Enter Departure City");

        Label destinationLabel = new Label("Destination City:");
        TextField destinationField = new TextField();
        destinationField.setId("destinationField");
        destinationField.setPromptText("Enter Destination City");

        Label departureTimeLabel = new Label("Departure Time:");
        TextField departureTimeField = new TextField();
        departureTimeField.setId("departureTimeField");

        Label dateLabel = new Label("Travel Date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setId("datePicker");

        Button searchButton = new Button("Search Flights");
        searchButton.setId("searchButton");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(USER_PAGE, this.user);
        });

        VBox searchBox = new VBox(10, departureLabel, departureField, destinationLabel, destinationField, dateLabel, datePicker, departureTimeLabel, departureTimeField, searchButton, backButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(20));

        return searchBox;
    }

    private void searchFlights(ListView<String> flightListView, TextField departureField, TextField destinationField, DatePicker datePicker, TextField departureTimeField) {
        String departureCity = departureField.getText();
        String destinationCity = destinationField.getText();
        String departureDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String departureTime = departureTimeField.getText();

        // Clear previous results
        flightListView.getItems().clear();

        try {
            Connection connection = myJDBC.getConnection();
            Statement statement = connection.createStatement();
             StringBuilder query = new StringBuilder("SELECT distinct flights.* FROM flights WHERE 1=1 " +
                    "and flights.ticketID not in (" +
                    "select bookings.ticketID from bookings " +
                    "join users on bookings.userID = users.userID " +
                    "where lower(users.username) = lower('" + user.getUsername() + "')" +
                    ")");

            if (!departureCity.isBlank()) {
                query.append(" AND departureCity LIKE '%").append(departureCity).append("%'");
            }
            if (!destinationCity.isBlank()) {
                query.append(" AND arrivalCity LIKE '%").append(destinationCity).append("%'");
            }
            if (!departureDate.isBlank()) {
                query.append(" AND departureDate LIKE '%").append(departureDate).append("%'");
            }
            if (!departureTime.isBlank()) {
                query.append(" AND departureTime LIKE '%").append(departureTime).append("%'");
            }

            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String flightDetails ="FlightID: " + resultSet.getString("ticketID") + "  Departure City: " + resultSet.getString("departureCity") +
                        ", Arrival City: " + resultSet.getString("arrivalCity") +
                        ", Departure Date: " + resultSet.getString("departureDate") +
                        ", Departure Time: " + resultSet.getString("departureTime");
                flightListView.getItems().add(flightDetails);
            }

            if (flightListView.getItems().isEmpty()) {
                flightListView.getItems().add("No flights found");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean bookFlight(String selectedFlight, String username) {
        try{
            Connection connection = myJDBC.getConnection();
            Statement statement = connection.createStatement();
            String[] flightDetails = selectedFlight.split(" ");
            String ticketIDs = flightDetails[1].trim();
            int ticketID = Integer.parseInt(ticketIDs);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE ticketID = '" + ticketID + "'");
            if (resultSet.next()) {
                ResultSet userid = statement.executeQuery("SELECT userID FROM users WHERE username = '" + username + "'");
                if (userid.next()) {
                    int bookingID = generateFlightID(1);
                    String sql = "INSERT INTO bookings (bookingID, userID, ticketID) VALUES ('" + bookingID + "', '" + userid.getInt("userID") + "', '" + ticketID + "')";
                    if (statement.executeUpdate(sql) > 0) {
                        return true;
                    }
                }
            }
                else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}