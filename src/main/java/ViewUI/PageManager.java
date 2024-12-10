package ViewUI;


import ViewUI.FlightPage.*;
import ViewUI.LoginPage.AdminLoginPage;
import ViewUI.LoginPage.CustomerLoginPage;
import javafx.stage.Stage;

import static ViewUI.Page.*;

public class PageManager {
    Page[] pages;
    Stage stage;

    PageManager(Stage stage) {
        this.stage = stage;
        pages = new Page[11];
        pages[ADMIN_LOGIN] = new AdminLoginPage(this);
        pages[CUSTOMER_LOGIN] = new CustomerLoginPage(this);
        pages[LANDING] = new LandingPage(this);
        pages[SEARCH_FLIGHT] = new SearchFlightPage(this);
        pages[REGISTER] = new RegisterPage(this);
        pages[USER] = new UserPage(this);
        pages[VIEW_FLIGHT] = new ViewFlightPage(this);
        pages[ADMIN] = new AdminPage(this);
        pages[CREATE_FLIGHT] = new CreateFlightPage(this);
        pages[UPDATE_FLIGHT] = new UpdateFlightPage(this);
        pages[DELETE_FLIGHT] = new DeleteFlightPage(this);

        stage.setScene(pages[LANDING].getScene());
        stage.setTitle("Flight Tracker");
        stage.show();
    }

    public void setScene(int newScene) {
        stage.setScene(pages[newScene].getScene());
    }
}
