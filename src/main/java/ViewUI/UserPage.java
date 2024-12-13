package ViewUI;

import Database.FlightDBQuery;
import Database.myJDBC;
import ViewUI.Page;
import ViewUI.PageManager;
import ViewUI.Username;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.Flight;
import org.example.UserFlight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import static Database.FlightDBQuery.deleteFlight;
import static Database.FlightDBQuery.getUserFlights;
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
        Button deleteFlight = new Button("Delete Flight");
        deleteFlight.setDisable(true);

        // Create a ListView and set the items to the flight list
        ListView<String> flightListView = new ListView<>();
        ArrayList<UserFlight> flights = getUserFlights(username);
        flights.forEach(userFlight -> {
            flightListView.getItems().add(userFlight.toString());
        });

        // Make sure it's scrollable
        flightListView.setPrefHeight(200);

        searchFlights.setOnAction(e -> {
            Username user1 = new Username(username);
            this.pageManager.setScene(SEARCH_FLIGHT, user1);
        });
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING, user);
        });
        flightListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.printf("new value: %s\n", newValue);
            deleteFlight.setDisable(newValue == null);
        });
        deleteFlight.setOnAction(e -> {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setContentText(String.format("Are you sure you want to delete this flight?\n\n%s", flightListView.getSelectionModel().getSelectedItem()));
            Optional<ButtonType> confirmed = deleteAlert.showAndWait();
            if (confirmed.get() == ButtonType.OK) {
                UserFlight selectedFlight = flights.get(flightListView.getSelectionModel().getSelectedIndex());
                boolean isSuccessful = FlightDBQuery.deleteFlight(selectedFlight.getBookingID());
                if (isSuccessful) {
                    this.pageManager.reload(); // Reload UI to reflect changes
                }
            }
        });

        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #F0FFFF");
        VBox innerContent = new VBox(text, welcome, new VBox(10, searchFlights), flightListView, deleteFlight);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(innerContent, backButton);

        root.getChildren().addAll(vbox);
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);
    }
}