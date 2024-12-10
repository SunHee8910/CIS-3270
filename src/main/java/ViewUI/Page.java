package ViewUI;

import ViewUI.LoginPage.ForgotPasswordPage;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import static Database.myJDBC.getUserRecoveryQuestionAndAnswerQuery;

public abstract class Page {
    public PageManager pageManager;
    public static int ADMIN_LOGIN = 0;
    public static int CUSTOMER_LOGIN = 1;
    public static int LANDING = 2;
    public static int SEARCH_FLIGHT = 3;
    public static int REGISTER = 4;
    public static int USER = 5;
    public static int VIEW_FLIGHT = 6;
    public static int ADMIN = 7;
    public static int CREATE_FLIGHT = 8;
    public static int UPDATE_FLIGHT = 9;
    public static int DELETE_FLIGHT = 10;
    public static int FORGOT_PASSWORD = 11;

    public Page(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public abstract Scene getScene();

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
