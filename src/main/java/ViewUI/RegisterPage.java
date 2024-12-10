package ViewUI;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static CodingLogicPackage.CodingLogic.isValidPassword;
import static Database.myJDBC.createCustomerQuery;
import static Database.myJDBC.getUsernameIsUniqueQuery;

public class RegisterPage extends Page {
    private final ArrayList<Label> errorMessages;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField addressField;
    private TextField zipField;
    private TextField stateField;
    private TextField usernameTextField;
    private TextField passwordField;
    private TextField emailField;
    private TextField ssnField;
    private TextField questionField;
    private Label firstNameError;
    private Label lastNameError;
    private Label addressFieldError;
    private Label zipFieldError;
    private Label stateFieldError;
    private Label usernameTextFieldError;
    private Label passwordFieldError;
    private Label emailFieldError;
    private Label ssnFieldError;
    private Label questionFieldError;

    public RegisterPage(PageManager pageManager) {
        super(pageManager);
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Register ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING);
        });

        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        Button loginButton = new Button("Register");

        firstNameField = new TextField();
        firstNameError = new Label();
        errorMessages.add(firstNameError);

        lastNameField = new TextField();
        lastNameError = new Label();
        errorMessages.add(lastNameError);

        addressField = new TextField();
        addressFieldError = new Label();
        errorMessages.add(addressFieldError);

        zipField = new TextField();
        zipFieldError = new Label();
        errorMessages.add(zipFieldError);

        stateField = new TextField();
        stateFieldError = new Label();
        errorMessages.add(stateFieldError);

        usernameTextField = new TextField();
        usernameTextFieldError = new Label();
        errorMessages.add(usernameTextFieldError);

        passwordField = new PasswordField();
        passwordFieldError = new Label();
        errorMessages.add(passwordFieldError);

        emailField = new TextField();
        emailFieldError = new Label();
        errorMessages.add(emailFieldError);

        ssnField = new TextField();
        ssnFieldError = new Label();
        errorMessages.add(ssnFieldError);

        questionField = new TextField();
        questionFieldError = new Label();
        errorMessages.add(questionFieldError);

        for (int i = 0; i < errorMessages.size(); i++) {
            errorMessages.get(i).setManaged(false);
        }

        loginButton.setOnAction(this::handleLoginButtonClick);

        root.getChildren().addAll(text, new VBox(10, new VBox(new Label("First Name"), firstNameField, firstNameError), new VBox(new Label("Last Name"), lastNameField, lastNameError), new VBox(new Label("Address"), addressField, addressFieldError), new VBox(new Label("Zip Code"), zipField, zipFieldError), new VBox(new Label("State"), stateField, stateFieldError), new VBox(new Label("Username"), usernameTextField, usernameTextFieldError), new VBox(new Label("Password"), passwordField, passwordFieldError), new VBox(new Label("Email Address"), emailField, emailFieldError), new VBox(new Label("SSN"), ssnField, ssnFieldError), new VBox(new Label("Recovery Answer"), questionField, questionFieldError), new VBox(new HBox(5, backButton, loginButton))));

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(scroll, 800, 800);
    }

    private void handleLoginButtonClick(ActionEvent event) {
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
        } else if (!getUsernameIsUniqueQuery(username)) {
            usernameTextFieldError.setText(String.format("This username (%s) is taken, please enter a unique username", username));
        }

        if (password.isBlank()) {
            passwordFieldError.setText("Password is required");
        } else if (!isValidPassword(password)) {
            passwordFieldError.setText("Password must contain a number, uppercase, lowercase, and symbol characters.");
        }

        if (email.isBlank()) {
            emailFieldError.setText("Email is required");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            emailFieldError.setText("Email address must be in \"user@mail.com\" format");
        }

        if (ssn.isBlank()) {
            ssnFieldError.setText("SSN is required");
        }
        if (!ssn.isBlank() && !ssn.matches("^[0-9]{9}$")) {
            ssnFieldError.setText("SSN must be in the format: 123456789");
        }
        if (question.isBlank()) {
            questionFieldError.setText("Question is required");
        }

        boolean hasError = false;
        for (int i = 0; i < errorMessages.size(); i++) {
            if (!errorMessages.get(i).getText().isBlank()) {
                errorMessages.get(i).setManaged(true);
                hasError = true;
            }
        }
        if (!hasError) {
            boolean isSuccessful = createCustomerQuery(firstName, lastName, password, address, zip, state, email, Integer.parseInt(ssn), question, username);
            if (isSuccessful) {
                this.pageManager.setScene(USER);
            }
        }
    }


}
