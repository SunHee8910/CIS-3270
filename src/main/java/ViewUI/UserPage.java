package ViewUI;

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
        ListView<String> flightListView = new ListView<>(flights);

        // Make sure it's scrollable
        flightListView.setPrefHeight(200);

        searchFlights.setOnAction(e -> {
            this.pageManager.setScene(SEARCH_FLIGHT, user);
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