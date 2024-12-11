package ViewUI;

import ViewUI.LoginPage.ForgotPasswordPage;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import static Database.myJDBC.getUserRecoveryQuestionAndAnswerQuery;
import static ViewUI.PageManager.FORGOT_PASSWORD;

public abstract class Page {
    protected PageManager pageManager;
    protected Username user;

    public Page(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public void setUser(Username user) {
        this.user = user;
    }

    public abstract Scene getScene();{
}

    public void handleForgotPasswordAction(ActionEvent actionEvent, String username) {
        if (username.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid username");
            alert.show();
        } else {
            String[] recoveryInfo = getUserRecoveryQuestionAndAnswerQuery(username);
            if (recoveryInfo == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid username");
                return;
            }

            ((ForgotPasswordPage)this.pageManager.pages.get(FORGOT_PASSWORD)).setRecoveryInfo(recoveryInfo);
            this.pageManager.setScene(FORGOT_PASSWORD);
        }
    }
}
