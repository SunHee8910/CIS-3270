package ViewUI.FlightPage;

import ViewUI.Page;
import ViewUI.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewFlightPage extends Page {
    public ViewFlightPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ View Booked Flights ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(USER);
        });
        Button viewFlights = new Button("View Flights");
        viewFlights.setOnAction(e -> {
//            try {
//                Connection connection = myJDBC.getConnection();
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE username = '" + username.getText() + "'");
//            }
//            catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5,
                //  new VBox (new Label("Username"), username),
                viewFlights, backButton));
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);

    }
}
