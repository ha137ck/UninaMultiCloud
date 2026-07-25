package it.unina.uninamulticloud.boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class UploadElementoBoundary {

    @FXML
    private TextField txtTitolo;
    @FXML private TextArea txtDescrizione;
    @FXML private Label lblNomeFile;
    @FXML private Label lblNomeCopertina;

    File fileSelezionato;
    File copertinaSelezionata;

    public void onSelezionaFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona il file audio o video da caricare");
        //estensioni supportate
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio e video", "*.mp3", "*.wav", "*.mp4", "*.mkv"),
                new FileChooser.ExtensionFilter("Audio", "*.mp3" , "*.wav"),
                new FileChooser.ExtensionFilter("Video", "*.mp4", "*.mkv")
        );

        File file = fileChooser.showOpenDialog(txtTitolo.getScene().getWindow());
        if(file != null){
            fileSelezionato = file;
            lblNomeFile.setText(file.getName());
        }
        //gestione di file non supportati?
    }

    public void onSelezionaCopertina(ActionEvent actionEvent) {
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

    public void onCarica(ActionEvent actionEvent) {
        String titolo = txtTitolo.getText();
        String descrizione = txtDescrizione.getText();

        try{
            //Passo i dati al controller

            //Pulisco i campi

        }
        catch(Exception e){

        }
    }
}
