package ViewUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    Stage stage;
    Scene startScene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setTitle("Flight Tracker");

        Button login = new Button("Login");
        Button register = new Button("Register");
        Button adminButton = new Button("Admin");

        register.setOnAction(e -> {
            this.stage.setScene(getRegisterScene());
        });
        login.setOnAction(e -> {
            this.stage.setScene(getLoginScene());
        });
        adminButton.setOnAction(e -> {
            this.stage.setScene(getAdminScreen());
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

        this.startScene = new Scene(vbox, 800, 800);
        primaryStage.setScene(this.startScene);
        primaryStage.show();

    }

    public Scene getLoginScene() {
        Text text = new Text("✈️ Login");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        Button loginButton = new Button("Login");
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));

        ArrayList<Label> errorMessages = new ArrayList<>();

        TextField usernameTextField = new TextField();
        Label usernameError = new Label();
        errorMessages.add(usernameError);

        PasswordField passwordTextField = new PasswordField();
        Label passwordError = new Label();
        errorMessages.add(passwordError);

        Label signInError = new Label();
        errorMessages.add(signInError);

        for (int i = 0; i < errorMessages.size(); i++) {
            errorMessages.get(i).setManaged(false);
        }

        loginButton.setOnAction(e -> {
            for (int i = 0; i < errorMessages.size(); i++) {
                errorMessages.get(i).setText("");
                errorMessages.get(i).setManaged(false);
                errorMessages.get(i).setTextFill(Color.RED);
            }
            String enteredUsername = usernameTextField.getText();
            String enteredPassword = passwordTextField.getText();

            if (enteredUsername.isBlank()) {
                usernameError.setText("Username is required");
            }
            if (enteredPassword.isBlank()) {
                passwordError.setText("Password is required");
            }

            boolean successfulLogin = false;
            if (usernameError.getText().isBlank() && passwordError.getText().isBlank() && !successfulLogin) {
                signInError.setText("Invalid username or password");
            }

            for (int i = 0; i < errorMessages.size(); i++) {
                if (!errorMessages.get(i).getText().isBlank()) {
                    errorMessages.get(i).setManaged(true);
                }
            }
        });


        root.getChildren().addAll(
                text,
                new Label("Username"), usernameTextField, usernameError,
                new Label("Password"), passwordTextField, passwordError,
                signInError,
                new HBox(5, backButton, loginButton));
        return new Scene(root, 800, 800);
    }

    public Scene getRegisterScene() {
        Text text = new Text("✈️ Register ");
        ArrayList<Label> errorMessages = new ArrayList<>();
        text.setFont(Font.font("System", FontWeight.BOLD, 22));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });

        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        Button loginButton = new Button("Register");

        TextField firstNameField = new TextField();
        Label firstNameError = new Label();
        errorMessages.add(firstNameError);

        TextField lastNameField = new TextField();
        Label lastNameError = new Label();
        errorMessages.add(lastNameError);

        TextField addressField = new TextField();
        Label addressFieldError = new Label();
        errorMessages.add(addressFieldError);

        TextField zipField = new TextField();
        Label zipFieldError = new Label();
        errorMessages.add(zipFieldError);

        TextField stateField = new TextField();
        Label stateFieldError = new Label();
        errorMessages.add(stateFieldError);

        TextField usernameTextField = new TextField();
        Label usernameTextFieldError = new Label();
        errorMessages.add(usernameTextFieldError);

        TextField passwordField = new PasswordField();
        Label passwordFieldError = new Label();
        errorMessages.add(passwordFieldError);

        TextField emailField = new TextField();
        Label emailFieldError = new Label();
        errorMessages.add(emailFieldError);

        TextField ssnField = new TextField();
        Label ssnFieldError = new Label();
        errorMessages.add(ssnFieldError);

        TextField questionField = new TextField();
        Label questionFieldError = new Label();
        errorMessages.add(questionFieldError);

        for (int i = 0; i < errorMessages.size(); i++) {
            errorMessages.get(i).setManaged(false);
        }

        loginButton.setOnAction(event -> {
            for (int i = 0; i < errorMessages.size(); i++) {
                errorMessages.get(i).setManaged(false);
                errorMessages.get(i).setTextFill(Color.RED);
                errorMessages.get(i).setText("");
            }
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String zip = zipField.getText();
            String state = stateField.getText();
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String ssn = ssnField.getText();
            String question = questionField.getText();

            // start validating content
            if (firstName.isBlank()) {
                firstNameError.setText("First name is required");
            }
            if (lastName.isBlank()) {
                lastNameError.setText("Last name is required");
            }
            if (address.isBlank()) {
                addressFieldError.setText("Address is required");
            }
            if (zip.isBlank()) {
                zipFieldError.setText("Zip is required");
            }
            if (state.isBlank()) {
                stateFieldError.setText("State is required");
            }
            if (username.isBlank()) {
                usernameTextFieldError.setText("Username is required");
            }
            // check username is unique
            boolean usernameIsTaken = false;
            if (usernameIsTaken) {
                usernameTextFieldError.setText("This username is taken, please enter a unique username");
            }
            if (password.isBlank()) {
                passwordFieldError.setText("Password is required");
            }
            if (email.isBlank()) {
                emailFieldError.setText("Email is required");
            }
            if (!email.isBlank() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                emailFieldError.setText("Email address must be in \"user@mail.com\" format");
            }
            if (ssn.isBlank()) {
                ssnFieldError.setText("SSN is required");
            }
            if (question.isBlank()) {
                questionFieldError.setText("Question is required");
            }

            for (int i = 0; i < errorMessages.size(); i++) {
                if (!errorMessages.get(i).getText().isBlank()) {
                    errorMessages.get(i).setManaged(true);
                }
            }
        });


        root.getChildren().addAll(
                text,
                new VBox(10,
                        new VBox(new Label("First Name"), firstNameField, firstNameError),
                        new VBox(new Label("Last Name"), lastNameField, lastNameError),
                        new VBox(new Label("Address"), addressField, addressFieldError),
                        new VBox(new Label("Zip Code"), zipField, zipFieldError),
                        new VBox(new Label("State"), stateField, stateFieldError),
                        new VBox(new Label("Username"), usernameTextField, usernameTextFieldError),
                        new VBox(new Label("Password"), passwordField, passwordFieldError),
                        new VBox(new Label("Email Address"), emailField, emailFieldError),
                        new VBox(new Label("SSN"), ssnField, ssnFieldError),
                        new VBox(new Label("Recovery Answer"), questionField, questionFieldError),
                        new VBox(new HBox(5, backButton, loginButton))
                )
        );

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(scroll, 800, 800);
    }

    public Scene getAdminScreen() {
        Text text = new Text("✈️ Admin Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        Button loginButton = new Button("Login");
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));

        ArrayList<Label> errorMessages = new ArrayList<>();

        TextField usernameTextField = new TextField();
        Label usernameError = new Label();
        errorMessages.add(usernameError);

        PasswordField passwordTextField = new PasswordField();
        Label passwordError = new Label();
        errorMessages.add(passwordError);

        PasswordField admincodeTextField = new PasswordField();
        Label admincodeError = new Label();
        errorMessages.add(admincodeError);

        Label signInError = new Label();
        errorMessages.add(signInError);

        for (int i = 0; i < errorMessages.size(); i++) {
            errorMessages.get(i).setManaged(false);
        }

        loginButton.setOnAction(e -> {
            for (int i = 0; i < errorMessages.size(); i++) {
                errorMessages.get(i).setText("");
                errorMessages.get(i).setManaged(false);
                errorMessages.get(i).setTextFill(Color.RED);
            }
            String enteredUsername = usernameTextField.getText();
            String enteredPassword = passwordTextField.getText();
            String adminCode = admincodeTextField.getText();

            if (enteredUsername.isBlank()) {
                usernameError.setText("Username is required");
            }
            if (enteredPassword.isBlank()) {
                passwordError.setText("Password is required");
            }
            if (adminCode.isBlank()) {
                admincodeError.setText("Admin code is required");
            }

            boolean successfulLogin = false;
            if (usernameError.getText().isBlank() && passwordError.getText().isBlank() && admincodeError.getText().isBlank() && !successfulLogin) {
                signInError.setText("Invalid username, password or Admin code");
            }

            for (int i = 0; i < errorMessages.size(); i++) {
                if (!errorMessages.get(i).getText().isBlank()) {
                    errorMessages.get(i).setManaged(true);
                }
            }
        });


        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Username"), usernameTextField, usernameError),
                        new VBox (new Label("Password"), passwordTextField, passwordError),
                        new VBox (new Label("Admin Code"), admincodeTextField, admincodeError),
                        signInError,
                new HBox(5, backButton, loginButton)));

        return new Scene(root, 800, 800);




    }

}