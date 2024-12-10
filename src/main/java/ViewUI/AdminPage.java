package ViewUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AdminPage extends Page{

    public AdminPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Admin Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Logout");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING);
        });
        Button createFlight = new Button("Create Flight");
        createFlight.setOnAction(e -> {
            this.pageManager.setScene(CREATE_FLIGHT);
        });
        Button deleteFlight = new Button("Delete Flight");
        deleteFlight.setOnAction(e -> {
            this.pageManager.setScene(DELETE_FLIGHT);
        });
        Button updateFlight = new Button("Update Flight");
        updateFlight.setOnAction(e -> {
            this.pageManager.setScene(UPDATE_FLIGHT);
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(text, new VBox(5, createFlight, deleteFlight, updateFlight, backButton));
        return new Scene(root, 800, 800);

    }
}
