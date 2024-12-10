package ViewUI.FlightPage;

import Database.myJDBC;
import ViewUI.Page;
import ViewUI.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchFlightPage extends Page {

    public SearchFlightPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Search for Flights ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(USER);
        });
        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        Button searchFlights = new Button("Search Flights");
        Label resultlist = new Label();
        Button book = new Button();
        searchFlights.setOnAction(e -> {

            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE departureCity LIKE '%" + departureCity.getText() + "%' AND arrivalCity LIKE '%" + arrivalCity.getText() + "%' AND departureDate LIKE '%" + departureDate.getText() + "%' AND arrivalDate LIKE '%" + arrivalDate.getText() + "%' AND departureTime LIKE '%" + departureTime.getText() + "%' AND arrivalTime LIKE '%" + arrivalTime.getText() + "%'");
                StringBuilder results = new StringBuilder();
                while (resultSet.next()) {
                    results.append((resultSet.getString("flightNumber") + " " + resultSet.getString("departureCity") + " " + resultSet.getString("arrivalCity") + " " + resultSet.getString("departureDate") + " " + resultSet.getString("arrivalDate") + " " + resultSet.getString("departureTime") + " " + resultSet.getString("arrivalTime") + " " + resultSet.getString("ticketID") + " " + resultSet.getString("ticketPrice")));

                }
                resultlist.setText(results.toString());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        book.setOnAction(b -> {


        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Departure City"), departureCity), new VBox(new Label("Arrival City"), arrivalCity), new VBox(new Label("Departure Date"), departureDate), new VBox(new Label("Arrival Date"), arrivalDate), new VBox(new Label("Departure Time"), departureTime), new VBox(new Label("Arrival Time"), arrivalTime), searchFlights, backButton));
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);

    }
}
