package ViewUI.LoginPage;

import ViewUI.Page;
import ViewUI.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static Database.myJDBC.userOrAdminLoginQuery;

public class CustomerLoginPage extends Page {
    public CustomerLoginPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Login");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        Button forgotPasswordButton = new Button("Forgot Password");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING);
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

        forgotPasswordButton.setOnAction(e -> {
            this.handleForgotPasswordAction(e, usernameTextField.getText());
        });

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

            if (usernameError.getText().isBlank() && passwordError.getText().isBlank()) {
                boolean successfulLogin = userOrAdminLoginQuery(enteredUsername, enteredPassword);
                if (!successfulLogin) {
                    signInError.setText("Invalid username or password.");
                }
            }
            boolean hasError = false;
            for (int i = 0; i < errorMessages.size(); i++) {
                if (!errorMessages.get(i).getText().isBlank()) {
                    hasError = true;
                    errorMessages.get(i).setManaged(true);
                }
            }
            if (!hasError) {
                this.pageManager.setScene(USER);
            }
        });


        root.getChildren().addAll(text,
                new Label("Username"), usernameTextField, usernameError,
                new Label("Password"), passwordTextField, passwordError, signInError,
                new VBox(5, new HBox(5, backButton, loginButton)), forgotPasswordButton);
        return new Scene(root, 800, 800);

    }
}
