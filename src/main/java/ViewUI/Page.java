package ViewUI;

import javafx.scene.Scene;

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

    public Page(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public abstract Scene getScene();
}
