package it.unina.uninamulticloud;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().init(primaryStage);
        SceneManager.getInstance().switchScene("Login.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
