package it.unina.uninamulticloud;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().init(primaryStage);
        SceneManager.getInstance().switchScene("Login.fxml");
        // Imposta la larghezza e l'altezza minima della finestra (es. 800x600)
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
