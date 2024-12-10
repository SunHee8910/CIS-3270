package ViewUI;


import ViewUI.FlightPage.*;
import ViewUI.LoginPage.AdminLoginPage;
import ViewUI.LoginPage.CustomerLoginPage;
import ViewUI.LoginPage.ForgotPasswordPage;
import javafx.stage.Stage;

import java.util.ArrayList;

import static ViewUI.Page.*;

public class PageManager {
    ArrayList<Page> pages;
    Stage stage;

    PageManager(Stage stage) {
        this.stage = stage;
        pages = new ArrayList<Page>();
        pages.add(ADMIN_LOGIN, new AdminLoginPage(this));
        pages.add(CUSTOMER_LOGIN, new CustomerLoginPage(this));
        pages.add(LANDING, new LandingPage(this));
        pages.add(SEARCH_FLIGHT, new SearchFlightPage(this));
        pages.add(REGISTER, new RegisterPage(this));
        pages.add(USER, new UserPage(this));
        pages.add(VIEW_FLIGHT, new ViewFlightPage(this));
        pages.add(ADMIN, new AdminPage(this));
        pages.add(CREATE_FLIGHT, new CreateFlightPage(this));
        pages.add(UPDATE_FLIGHT, new UpdateFlightPage(this));
        pages.add(DELETE_FLIGHT, new DeleteFlightPage(this));
        pages.add(FORGOT_PASSWORD, new ForgotPasswordPage(this));

        stage.setScene(pages.get(LANDING).getScene());
        stage.setTitle("Flight Tracker");
        stage.show();
    }

    public void setScene(int newScene) {
        stage.setScene(pages.get(newScene).getScene());
    }
}
