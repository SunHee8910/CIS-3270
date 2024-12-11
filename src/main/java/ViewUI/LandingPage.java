package ViewUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static ViewUI.PageManager.*;

public class LandingPage extends Page {

    public LandingPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Button login = new Button("Login");
        Button register = new Button("Register");
        Button adminButton = new Button("Admin");

        register.setOnAction(e -> {
            this.pageManager.setScene(REGISTER);
        });
        login.setOnAction(e -> {
            this.pageManager.setScene(CUSTOMER_LOGIN);
        });
        adminButton.setOnAction(e -> {
            this.pageManager.setScene(ADMIN_LOGIN);
        });

        VBox vbox = new VBox(50);
        vbox.setPadding(new Insets(10));
        HBox hbox = new HBox(10); // 10 pixels spacing between buttons
        Text header = new Text("✈️ Welcome to Flight Tracker ");

        header.setFont(Font.font("System", FontWeight.BOLD, 22));
        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER); // Center the buttons
        hbox.getChildren().addAll(register, login);
        VBox buttons = new VBox(5, hbox, adminButton);
        buttons.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(header, buttons);

        return new Scene(vbox, 800, 800);
    }
}
