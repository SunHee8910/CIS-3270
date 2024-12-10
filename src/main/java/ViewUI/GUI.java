package ViewUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    PageManager pageManager;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage)  {
        this.pageManager = new PageManager(primaryStage);
    }
}







