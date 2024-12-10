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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.Flight;

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
        Label flights[] = new Label[100];
        Label rooot = new Label();
        VBox flightlist = new VBox(5);
        VBox root = new VBox(10);
        Button book = new Button("Book Flight");
            searchFlights.setOnAction(e -> {
                flightlist.getChildren().clear(); // Clear previous results

                try {
                    Connection connection = myJDBC.getConnection();
                    Statement statement = connection.createStatement();
                    StringBuilder query = new StringBuilder("SELECT * FROM flights WHERE 1=1");

                    if (!departureCity.getText().isBlank()) {
                        query.append(" AND departureCity LIKE '%").append(departureCity.getText()).append("%'");
                    }
                    else {
                        query.append(" AND departureCity LIKE '%").append(departureCity.getText()).append("%'");
                    }
                    if (!arrivalCity.getText().isBlank()) {
                        query.append(" AND arrivalCity LIKE '%").append(arrivalCity.getText()).append("%'");
                    }
                    else {
                        query.append(" AND arrivalCity LIKE '%").append(arrivalCity.getText()).append("%'");
                    }
                    if (!departureDate.getText().isBlank()) {
                        query.append(" AND departureDate LIKE '%").append(departureDate.getText()).append("%'");
                    }
                    else{
                        query.append(" AND departureDate LIKE '%").append(departureDate.getText()).append("%'");
                    }
                    if (!arrivalDate.getText().isBlank()) {
                        query.append(" AND arrivalDate LIKE '%").append(arrivalDate.getText()).append("%'");
                    }
                    else {
                        query.append(" AND arrivalDate LIKE '%").append(arrivalDate.getText()).append("%'");
                    }
                    if (!departureTime.getText().isBlank()) {
                        query.append(" AND departureTime LIKE '%").append(departureTime.getText()).append("%'");
                    }
                    else{
                        query.append(" AND departureTime LIKE '%").append(departureTime.getText()).append("%'");
                    }
                    if (!arrivalTime.getText().isBlank()) {
                        query.append(" AND arrivalTime LIKE '%").append(arrivalTime.getText()).append("%'");
                    }
                    else{
                        query.append(" AND arrivalTime LIKE '%").append(arrivalTime.getText()).append("%'");
                    }

                    ResultSet resultSet = statement.executeQuery(query.toString());
                    boolean hasResults = false;
                    int i = 0;
                    while (resultSet.next()) {
                        hasResults = true;
                        flights[i] = new Label();
                        flights[i].setText("Departure City: " +
                                resultSet.getString("departureCity") + "      " +
                                "Arrival City: " +
                                resultSet.getString("arrivalCity") + "      " +
                                "Departure Date: " +
                                resultSet.getString("departureDate") + "      " +
                                "Arrival Date: " +
                                resultSet.getString("arrivalDate") + "      " +
                                "Departure Time: " +
                                resultSet.getString("departureTime") + "      " +
                                "Arrival Time: " +
                                resultSet.getString("arrivalTime"));
                        flightlist.getChildren().add(new Label(flights[i].getText()));

                        flightlist.getChildren().add(new Button ("Book Flight"));
                        i++;
                    }

                    if (!hasResults) {
                        flightlist.getChildren().add(new Label("No flights found"));
                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        book.setOnAction(b -> {
            Flight flight = new Flight();


        });

        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Departure City"), departureCity), new VBox(new Label("Arrival City"), arrivalCity), new VBox(new Label("Departure Date"), departureDate), new VBox(new Label("Arrival Date"), arrivalDate), new VBox(new Label("Departure Time"), departureTime), new VBox(new Label("Arrival Time"), arrivalTime), searchFlights, backButton), flightlist);
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);

    }
}
