package it.unina.uninamulticloud;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;

    private final double BASE_WIDTH = 1280.0;
    private final double BASE_HEIGHT = 720.0;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("UninaMultiCloud");
        this.primaryStage.setMinWidth(BASE_WIDTH);
        this.primaryStage.setMinHeight(BASE_HEIGHT);
    }

    public void switchScene(String fxmlFileName) {
        try {
            String fxmlPath = "/it/unina/uninamulticloud/fxml/" + fxmlFileName;
            URL fxmlLocation = getClass().getResource(fxmlPath);
            if (fxmlLocation == null) {
                throw new RuntimeException("File FXML non trovato: " + fxmlPath);
            }

            // 1. Carica il file FXML esattamente come lo vedi su SceneBuilder
            Parent root = FXMLLoader.load(fxmlLocation);

            // 2. Crea la scena passando direttamente il tuo root (lo StackPane)
            Scene scene = new Scene(root, BASE_WIDTH, BASE_HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore critico durante il caricamento di: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}