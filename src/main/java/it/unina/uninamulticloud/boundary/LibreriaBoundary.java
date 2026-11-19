package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LibreriaBoundary {

    @FXML private TableView<ElementoMultimediale> tabellaElementi;
    @FXML private TableColumn<ElementoMultimediale, String> colTitolo;
    @FXML private TableColumn<ElementoMultimediale, String> colDurata;
    @FXML private TableColumn<ElementoMultimediale, String> colTipo;
    @FXML private TableColumn<ElementoMultimediale, String> colFormato;
    @FXML private TableColumn<ElementoMultimediale, String> colDataCreazione;
    //come si mostra l'immagine?
    @FXML private TableColumn<ElementoMultimediale, String> immagineCopertina;

    @FXML private Label lblMessaggio;

    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //Chiamato al caricamento della pagina da FxmlLoader
    @FXML public void initialize() {
        //ogni colonna ricava il testo da mostrare a partire da un elemento
        colTitolo.setCellValueFactory(riga -> new SimpleStringProperty(riga.getValue().getTitolo()));
        colTipo.setCellValueFactory(riga -> new SimpleStringProperty(
                riga.getValue() instanceof Audio ? "Audio" : "Video"));
        colDurata.setCellValueFactory(riga -> new SimpleStringProperty(
                formattaDurata(riga.getValue().getDurata())));
        colFormato.setCellValueFactory(riga -> new SimpleStringProperty(riga.getValue().getFormato()));
        colDataCreazione.setCellValueFactory(riga ->
                                                                    new SimpleStringProperty(formattaData(riga.getValue().getDataCreazione())));

        caricaElementi();
    }

    @FXML public void onTornaAllaHome() {
        SceneManager.getInstance().switchScene("Home.fxml");
    }

    private void caricaElementi(){
        try{
            List<ElementoMultimediale> listaElementi = ElementoMultimedialeControl.getInstance().getElementiUtenteLoggato();
            tabellaElementi.setItems(FXCollections.observableArrayList(listaElementi));
            if (listaElementi.isEmpty()) lblMessaggio.setText("Non hai ancora pubblicato nessun elemento multimediale.");
            else lblMessaggio.setText("");

        }catch (SQLException e){
            lblMessaggio.setText("Errore nel caricamento della libreria: " + e.getMessage());
        }
    }


    private String formattaDurata(Duration d) {
        if (d == null) return "-";
        //operatore ternario
        return (d.toHours() > 0)
                ? String.format("%d:%02d:%02d", d.toHours(), d.toMinutesPart(), d.toSecondsPart())
                : String.format("%d:%02d", d.toMinutesPart(), d.toSecondsPart());
    }

    private String formattaData(LocalDateTime data) {
        return (data == null) ? "-" : data.format(FORMATO_DATA);
    }
}
