package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.PlaylistControl;
import it.unina.uninamulticloud.entity.Playlist;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;

public class PlaylistBoundary {

    @FXML private Label nomeCopertinaField;
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextArea descrizioneField;
    @FXML private ComboBox<String> visibilitaField;
    @FXML private Label messaggioLabel;
    @FXML private Label aggiungiCollaboratoriLabel;

    PlaylistControl playlistControl = PlaylistControl.getInstance();

    File copertinaSelezionata;

    @FXML
    private void initialize() {
        visibilitaField.getItems().setAll("Pubblica", "Privata","Condivisa");
        visibilitaField.setValue("Privata");

        emailField.setVisible(false);
        emailField.setManaged(false); //rimuovo e ricalcola lo spazio occupato nel layout
        aggiungiCollaboratoriLabel.setVisible(false);
        aggiungiCollaboratoriLabel.setManaged(false);


        visibilitaField.valueProperty().addListener(
                (osservato, vecchio, nuovo) -> {
                    emailField.setVisible(nuovo.equals("Condivisa"));
                    emailField.setManaged(nuovo.equals("Condivisa"));
                    aggiungiCollaboratoriLabel.setVisible(nuovo.equals("Condivisa"));
                    aggiungiCollaboratoriLabel.setManaged(nuovo.equals("Condivisa"));
                });

    }

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
    public void onCrea(){
        String titolo = nomeField.getText();
        String descrizione = descrizioneField.getText();
        String visibilita = visibilitaField.getValue();
        try {
            //eventualmente per mostrare dati della playlist creata
            Playlist creata = playlistControl.creaPlaylist(titolo, descrizione, visibilita);
            nomeField.clear();
            descrizioneField.clear();
            showSuccess();
        }
        catch (IllegalArgumentException e){
            showError(e.getMessage());
        } catch ( SQLException e) {
            showError("Errore durante la creazione della playlist");
        }
    }

    @FXML
    public void onIndietro() {
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("Home.fxml");
    }

    @FXML
    public void showError(String msg) {
        messaggioLabel.setStyle("-fx-text-fill: red;");
        messaggioLabel.setText(msg);
    }

    @FXML
    public void showSuccess() {
        messaggioLabel.setStyle("-fx-text-fill: green;");
        messaggioLabel.setText("Playlist creata con successo!");
    }
}
