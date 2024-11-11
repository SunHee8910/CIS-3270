package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent> {

    Button register;
    Button login;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Flight Tracker");

        register = new Button("Register");
        register.setOnAction(this);

        login = new Button("Login");
        login.setOnAction(this);
        VBox root = new VBox(50);
        HBox layout = new HBox(10); // 10 pixels spacing between buttons
        Text text = new Text("✈️ Welcome to Flight Tracker ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        root.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER); // Center the buttons
        layout.getChildren().addAll(register, login);
        root.getChildren().addAll(text,layout );
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == register) {
            System.out.println("register clicked");
        }
        if (actionEvent.getSource() == login) {
            System.out.println("login clicked");
        }
    }
}