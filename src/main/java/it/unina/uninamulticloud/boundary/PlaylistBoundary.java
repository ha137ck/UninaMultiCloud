package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.PlaylistControl;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class PlaylistBoundary {

    @FXML private Label nomeCopertinaField;
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextArea descrizioneField;
    @FXML private ComboBox<String> visibilitaField;
    @FXML private Label errorLabel;

    //PlaylistControl playlistControl = PlaylistControl.getInstance();

    File copertinaSelezionata;

    @FXML
    public void onSelezionaCopertina() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona la copertina della playlist");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.png", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(nomeField.getScene().getWindow());
        if(file != null){
            copertinaSelezionata = file;
            nomeCopertinaField.setText(file.getName());
        }

    }

    @FXML
    public void onIndietro() {
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("Home.fxml");
    }

    @FXML
    public void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }
}
