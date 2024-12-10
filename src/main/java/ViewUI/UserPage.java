package ViewUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UserPage extends Page{

    public UserPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ User Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Logout");
        Button searchFlights = new Button("Search for Flights");
        Button viewFlights = new Button("View Booked Flights");

        searchFlights.setOnAction(e -> {
            this.pageManager.setScene(SEARCH_FLIGHT);
        });
        viewFlights.setOnAction(e -> {
            this.pageManager.setScene(VIEW_FLIGHT);
        });
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING);
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(10, new VBox(new Label("Search for Flights"), searchFlights), new VBox(new Label("View Booked Flights"), viewFlights), backButton)

        );
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);
    }
}
