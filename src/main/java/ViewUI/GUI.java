package ViewUI;

import Database.myJDBC;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Database.FlightDBQuery.*;
import org.example.Flight;

import static CodingLogicPackage.CodingLogic.generateFlightID;

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
            this.stage.setScene(getAdminLogin());
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
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("Select * from users where username = " + usernameTextField.getText() + "and password = " + passwordTextField.getText());
                if (resultSet.next()) {
                    this.stage.setScene(getUserScreen());
                } else {
                    this.stage.setScene(getLoginScene());
                    new Label("Invalid username or password");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
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

    public Scene getUserScreen(){
        Text text = new Text("✈️ User Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        Button searchFlights = new Button("Search for Flights");
        Button viewFlights = new Button ("View Booked Flights");

        searchFlights.setOnAction(e -> {
            this.stage.setScene(getSearchFlights());
        });
        viewFlights.setOnAction(e -> {
            this.stage.setScene(getViewFlights());
        });
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(10,
                        new VBox (new Label("Search for Flights"), searchFlights),
                        new VBox (new Label("View Booked Flights"), viewFlights),
                        backButton
                )

        );
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);
    }

    public Scene getSearchFlights(){
        Text text = new Text("✈️ Search for Flights ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        Button searchFlights = new Button("Search Flights");
        searchFlights.setOnAction(e -> {

            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE departureCity LIKE '%" + departureCity.getText() + "%' AND arrivalCity LIKE '%" + arrivalCity.getText() + "%' AND departureDate LIKE '%" + departureDate.getText() + "%' AND arrivalDate LIKE '%" + arrivalDate.getText() + "%' AND departureTime LIKE '%" + departureTime.getText() + "%' AND arrivalTime LIKE '%" + arrivalTime.getText() + "%'");
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Departure City"), departureCity),
                        new VBox (new Label("Arrival City"), arrivalCity),
                        new VBox (new Label("Departure Date"), departureDate),
                        new VBox (new Label("Arrival Date"), arrivalDate),
                        new VBox (new Label("Departure Time"), departureTime),
                        new VBox (new Label("Arrival Time"), arrivalTime),
                        searchFlights,
                        backButton
                )
        );
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(root, 800, 800);

    }

    public Scene getViewFlights(){
        Text text = new Text("✈️ View Booked Flights ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        TextField username = new TextField();
        Button viewFlights = new Button("View Flights");
        viewFlights.setOnAction(e -> {
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM flights WHERE username = " + username.getText());
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Username"), username),
                        viewFlights,
                        backButton
                )
        );
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

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
                else if ( i == errorMessages.size() - 1 && errorMessages.get(i).getText().isBlank()) {
                    try {
                        Connection connection = myJDBC.getConnection();
                        Statement statement = connection.createStatement();
                        String sql = "INSERT INTO users (firstName, lastName, address, zip, state, username, password, email, ssn, question) VALUES ('" + firstName + "', '" + lastName + "', '" + address + "', '" + zip + "', '" + state + "', '" + username + "', '" + password + "', '" + email + "', '" + ssn + "', '" + question + "')";
                        statement.executeUpdate(sql);
                        this.stage.setScene(getUserScreen());
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
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

    public Scene getAdminLogin() {
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

        loginButton.setOnAction(event -> {
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
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("Select * from users where username = " + usernameTextField.getText() + "and adminCode = " + admincodeTextField.getText() + "and password = " + passwordTextField.getText());
                if (resultSet.next()) {
                    this.stage.setScene(getAdminScreen());
                } else {
                    this.stage.setScene(getAdminLogin());
                    new Label("Invalid username or password");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox(new Label("Username"), usernameTextField, usernameError),
                        new VBox(new Label("Password"), passwordTextField, passwordError),
                        new VBox(new Label("Admin Code"), admincodeTextField, admincodeError),
                        signInError,
                        new HBox(5, backButton, loginButton)));

        return new Scene(root, 800, 800);


    }

    public Scene getAdminScreen() {
        Text text = new Text("✈️ Admin Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        Button createFlight = new Button("Create Flight");
        createFlight.setOnAction(e -> {
            this.stage.setScene(getCreateFlight());
        });
        Button deleteFlight = new Button("Delete Flight");
        deleteFlight.setOnAction(e -> {
            this.stage.setScene(getDeleteFlight());
        });
        Button updateFlight = new Button("Update Flight");
        updateFlight.setOnAction(e -> {
            this.stage.setScene(getUpdateFlight());
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        createFlight,
                        deleteFlight,
                        updateFlight,
                        backButton
                )
        );
        return new Scene(root, 800, 800);
    }
    public Scene getCreateFlight(){
        Text text = new Text("✈️ Create Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        TextField flightNumber = new TextField();
        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        TextField ticketID = new TextField();
        TextField ticketPrice = new TextField();

        Button createFlight = new Button("Create Flight");
        createFlight.setOnAction(e -> {
            String flightNumbers = String.valueOf(generateFlightID(Integer.parseInt(flightNumber.getText())));
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO flights (flightNumber, departureCity, arrivalCity, departureDate, arrivalDate, departureTime, arrivalTime, ticketID, ticketPrice) VALUES (" + flightNumbers + ", " + departureCity.getText() + ", " + arrivalCity.getText() + ", " + departureDate.getText() + ", " + arrivalDate.getText() + ", " + departureTime.getText() + ", " + arrivalTime.getText() + ", " + ticketID.getText() + ", " + ticketPrice.getText() + ")";
                statement.executeUpdate(sql);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Flight Number"), flightNumber),
                        new VBox (new Label("Departure City"), departureCity),
                        new VBox (new Label("Arrival City"), arrivalCity),
                        new VBox (new Label("Departure Date"), departureDate),
                        new VBox (new Label("Arrival Date"), arrivalDate),
                        new VBox (new Label("Departure Time"), departureTime),
                        new VBox (new Label("Arrival Time"), arrivalTime),
                        new VBox (new Label("Ticket ID"), ticketID),
                        new VBox (new Label("Ticket Price"), ticketPrice),
                        createFlight,
                        backButton
                )
        );
        return new Scene(root, 800, 800);
    }
    public Scene getDeleteFlight(){
        Text text = new Text("✈️ Delete Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        TextField flightNumber = new TextField();
        Button deleteFlight = new Button("Delete Flight");
        deleteFlight.setOnAction(e -> {
            try {
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM flights WHERE flightNumber = " + flightNumber.getText();
                statement.executeUpdate(sql);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Flight Number"), flightNumber),
                        deleteFlight,
                        backButton
                )
        );
        return new Scene(root, 800, 800);
    }
    public Scene getUpdateFlight(){
        Text text = new Text("✈️ Update Flight ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.stage.setScene(this.startScene);
        });
        TextField flightNumber = new TextField();
        TextField departureCity = new TextField();
        TextField arrivalCity = new TextField();
        TextField departureDate = new TextField();
        TextField arrivalDate = new TextField();
        TextField departureTime = new TextField();
        TextField arrivalTime = new TextField();
        TextField ticketID = new TextField();
        TextField ticketPrice = new TextField();

        Button updateFlight = new Button("Update Flight");
        updateFlight.setOnAction(e -> {
            try{
                Connection connection = myJDBC.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultset = statement.executeQuery("SELECT * FROM flights WHERE flightNumber = " + flightNumber.getText());
                if (resultset.next()){
                    try {
                        Connection connection1 = myJDBC.getConnection();
                        Statement statement1 = connection.createStatement();
                        String sql = "UPDATE flights SET departureCity = " + departureCity.getText() + ", arrivalCity = " + arrivalCity.getText() + ", departureDate = " + departureDate.getText() + ", arrivalDate = " + arrivalDate.getText() + ", departureTime = " + departureTime.getText() + ", arrivalTime = " + arrivalTime.getText() + ", ticketID = " + ticketID.getText() + ", ticketPrice = " + ticketPrice.getText() + " WHERE flightNumber = " + flightNumber.getText();
                        statement1.executeUpdate(sql);
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    new Label("Flight does not exist");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                text,
                new VBox(5,
                        new VBox (new Label("Flight Number"), flightNumber),
                        new VBox (new Label("Departure City"), departureCity),
                        new VBox (new Label("Arrival City"), arrivalCity),
                        new VBox (new Label("Departure Date"), departureDate),
                        new VBox (new Label("Arrival Date"), arrivalDate),
                        new VBox (new Label("Departure Time"), departureTime),
                        new VBox (new Label("Arrival Time"), arrivalTime),
                        new VBox (new Label("Ticket ID"), ticketID),
                        new VBox (new Label("Ticket Price"), ticketPrice),
                        updateFlight,
                        backButton
                )
        );
        return new Scene(root, 800, 800);
    }
}







