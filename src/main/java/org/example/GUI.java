package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent> {

    Button register;
    Button login;
    Button backButton;
    Button loginButton;
    Scene startScene;
    String currentScene = "start";

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
        root.getChildren().addAll(text, layout);
        this.startScene = new Scene(root, 800, 800);
        primaryStage.setScene(this.startScene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        if (actionEvent.getSource() == register) {
            stage.setScene(getRegisterScene());
        }
        if (actionEvent.getSource() == login) {
            stage.setScene(getLoginScene());
        }
        if (actionEvent.getSource() == backButton) {
            stage.setScene(this.startScene);
        }
        if (actionEvent.getSource() == loginButton) {
            if (this.currentScene.equals("login")){
                System.out.println("hit the login button on the login form");
            } else if (this.currentScene.equals("register")){
                System.out.println("hit the register button on the register form");
            }
        }
    }


    public Scene getLoginScene() {
        this.currentScene = "login";
        Text text = new Text("✈️ Login");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        this.backButton = new Button("Back");
        this.backButton.setOnAction(this);
        this.loginButton = new Button("Login");
        this.loginButton.setOnAction(this);
        VBox root = new VBox(5);

        TextField usernameTextField = new TextField();
        PasswordField passwordTextField = new PasswordField();


        root.getChildren().addAll(
                text,
                new Label("Username"),
                usernameTextField,
                new Label("Password"),
                passwordTextField,
                new HBox(5, this.backButton, this.loginButton));
        return new Scene(root, 800, 800);
    }

    public Scene getRegisterScene() {
        this.currentScene = "register";
        Text text = new Text("✈️ Register ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        this.backButton = new Button("Back");
        backButton.setOnAction(this);
        VBox root = new VBox(5);
        this.loginButton = new Button("Register");
        this.loginButton.setOnAction(this);


        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField addressField = new TextField();
        TextField zipField = new TextField();
        TextField stateField = new TextField();
        TextField usernameTextField = new TextField();
        TextField passwordField = new PasswordField();
        TextField emailField = new TextField();
        TextField ssnField = new TextField();
        TextField questionField = new TextField();


        root.getChildren().addAll(text, new VBox(5,
                new Label("First Name"),
                firstNameField,
                new Label("Last Name"),
                lastNameField,
                new Label("Address"),
                addressField,
                new Label("Zip Code"),
                zipField,
                new Label("State"),
                stateField,
                new Label("Username"),
                usernameTextField,
                new Label("Password"),
                passwordField,
                new Label("Email Address"),
                emailField,
                new Label("SSN"),
                ssnField,
                new Label("Recovery Answer"),
                questionField
                ), new HBox(5, this.backButton, this.loginButton));

        return new Scene(root, 800, 800);
    }
}