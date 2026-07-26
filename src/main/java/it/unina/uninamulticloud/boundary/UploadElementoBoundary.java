package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;

public class UploadElementoBoundary {

    @FXML private TextField txtTitolo;
    @FXML private TextArea txtDescrizione;
    @FXML private Label lblNomeFile;
    @FXML private Label lblNomeCopertina;
    @FXML private Label errorLabel;

    ElementoMultimedialeControl elementoControl = ElementoMultimedialeControl.getInstance();

    File fileSelezionato;
    File copertinaSelezionata;

    public void onSelezionaFile() {
        FileChooser fileChooser = new FileChooser();
        //Imposta il titolo della finestra di dialogo
        fileChooser.setTitle("Seleziona il file audio o video da caricare");
        //Prende la lista dei filtri per le estensioni supportate
        fileChooser.getExtensionFilters().addAll(
                //creo la lista dei filtri
                new FileChooser.ExtensionFilter("Audio e video", "*.mp3", "*.wav", "*.mp4", "*.mkv", "*.mov"),
                new FileChooser.ExtensionFilter("Audio", "*.mp3" , "*.wav"),
                new FileChooser.ExtensionFilter("Video", "*.mp4", "*.mkv", "*.mov")
        );
        //Apre la finestra di dialogo e aspetta la scelta dell'utente
        File file = fileChooser.showOpenDialog(txtTitolo.getScene().getWindow());
        if(file != null){
            fileSelezionato = file;
            lblNomeFile.setText(file.getName());
        }
        //TODO gestione di file non supportati, controllo del tipo di file
    }

    public void onSelezionaCopertina() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona la copertina dell'album");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.png", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(txtTitolo.getScene().getWindow());
        if(file != null){
            copertinaSelezionata = file;
            lblNomeCopertina.setText(file.getName());
        }

    }

    public void onCarica() {
        String titolo = txtTitolo.getText();
        String descrizione = txtDescrizione.getText();

        if(titolo.isEmpty() || fileSelezionato == null){ showError("Inserisci tutti i campi obbligatori."); }

        try{
            //Passo i dati al controller
            elementoControl.caricaElemento(titolo, descrizione, fileSelezionato, copertinaSelezionata);

            //Pulisco i campi
            txtTitolo.clear();
            txtDescrizione.clear();
            lblNomeFile.setText("");
            lblNomeCopertina.setText("");

            showSuccess();
        }
        catch(Exception e){
            e.printStackTrace();
            showError("Errore durante il caricamento");
        }
    }

    public void onIndietro() {
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("Home.fxml");
    }

    public void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }

    public void showSuccess() {
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Caricamento effettuato con successo!");
    }
}
