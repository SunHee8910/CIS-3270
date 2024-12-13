package ViewUI;

import ViewUI.FlightPage.*;
import ViewUI.LoginPage.AdminLoginPage;
import ViewUI.LoginPage.CustomerLoginPage;
import ViewUI.LoginPage.ForgotPasswordPage;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PageManager {
    public static final int LANDING = 0;
    public static final int ADMIN_LOGIN = 1;
    public static final int CUSTOMER_LOGIN = 2;
    public static final int SEARCH_FLIGHT = 3;
    public static final int REGISTER = 4;
    public static final int USER_PAGE = 5;
    public static final int VIEW_FLIGHT = 6;
    public static final int ADMIN_PAGE = 7;
    public static final int CREATE_FLIGHT = 8;
    public static final int UPDATE_FLIGHT = 9;
    public static final int DELETE_FLIGHT = 10;
    public static final int FORGOT_PASSWORD = 11;

    ArrayList<Page> pages;
    Stage stage;
    int currentScene;

    public PageManager(Stage stage) {
        this.stage = stage;
        pages = new ArrayList<>();
        pages.add(new LandingPage(this));
        pages.add(new AdminLoginPage(this));
        pages.add(new CustomerLoginPage(this));
        pages.add(new SearchFlightPage(this, null));
        pages.add(new RegisterPage(this));
        pages.add(new UserPage(this, null));
        //pages.add(new ViewFlightPage(this));
        pages.add(new AdminPage(this));
        pages.add(new CreateFlightPage(this));
        pages.add(new UpdateFlightPage(this));
        pages.add(new DeleteFlightPage(this));
        pages.add(new ForgotPasswordPage(this));

        stage.setScene(pages.get(0).getScene());
        stage.setTitle("Flight Tracker");
        stage.show();
    }

    public void setScene(int newScene, Username user) {
        this.currentScene = newScene;
        Page page = pages.get(newScene);
        page.setUser(user);
        stage.setScene(page.getScene());
    }

    public void setScene(int newScene) {
        this.currentScene = newScene;
        stage.setScene(pages.get(newScene).getScene());
    }

    public void reload() {
        stage.setScene(pages.get(this.currentScene).getScene());
    }
}