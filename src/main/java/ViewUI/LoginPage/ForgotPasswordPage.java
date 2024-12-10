package ViewUI.LoginPage;

import ViewUI.Page;
import ViewUI.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ForgotPasswordPage extends Page {
    String[] recoveryInfo;

    public ForgotPasswordPage(PageManager pageManager) {
        super(pageManager);
    }

    @Override
    public Scene getScene() {
        Label question = new Label(recoveryInfo[0]);
        TextField answer = new TextField();

        Button button = new Button("Get Password");
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> this.pageManager.setScene(LANDING));
        button.setOnAction(e -> {
            String recoveryAnswer = recoveryInfo[1];
            String password = recoveryInfo[2];
            String enteredAnswer = answer.getText();

            if (enteredAnswer.equalsIgnoreCase(recoveryAnswer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText(String.format("Your password is '%s'", password));
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect recovery answer");
                alert.show();
            }
        });
        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(question, answer, button, backButton);

        return new Scene(root, 800, 800);
    }

    public void setRecoveryInfo(String[] recoveryInfo) {
        this.recoveryInfo = recoveryInfo;
    }
}
