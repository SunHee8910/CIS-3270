package ViewUI.LoginPage;

import ViewUI.Page;
import ViewUI.PageManager;
import ViewUI.Username;
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

import static Database.myJDBC.userOrAdminLoginQuery;
import static ViewUI.PageManager.ADMIN_PAGE;
import static ViewUI.PageManager.LANDING;

public class AdminLoginPage extends Page {
    public AdminLoginPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Text text = new Text("✈️ Admin Screen ");
        text.setFont(Font.font("System", FontWeight.BOLD, 22));
        Button backButton = new Button("Back");
        Button forgotPasswordButton = new Button("Forgot Password");
        backButton.setOnAction(e -> {
            this.pageManager.setScene(LANDING, null);
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

        forgotPasswordButton.setOnAction(e -> {
            this.handleForgotPasswordAction(e, usernameTextField.getText());
        });

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
            } else if (!adminCode.equals("1234")) {
                admincodeError.setText("Admin code is invalid");
            }

            if (usernameError.getText().isBlank() && passwordError.getText().isBlank() && admincodeError.getText().isBlank()) {
                boolean isSuccessful = userOrAdminLoginQuery(enteredUsername, enteredPassword);
                if (!isSuccessful) {
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
                Username Username;
                this.pageManager.setScene(ADMIN_PAGE, Username = new Username(usernameTextField.getText()));
            }
        });


        root.getChildren().addAll(text, new VBox(5, new VBox(new Label("Username"), usernameTextField, usernameError), new VBox(new Label("Password"), passwordTextField, passwordError), new VBox(new Label("Admin Code"), admincodeTextField, admincodeError), signInError, new HBox(5, backButton, loginButton)));

        return new Scene(root, 800, 800);
    }
}
