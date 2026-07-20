package it.unina.uninamulticloud;

import it.unina.uninamulticloud.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Inizializza la finestra
        SceneManager.getInstance().init(primaryStage);

        // Carica la schermata di login
        SceneManager.getInstance().switchScene("login.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}