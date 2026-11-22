package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.enums.CriterioRicerca;
import it.unina.uninamulticloud.entity.enums.OggettoRicerca;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import static it.unina.uninamulticloud.util.FormattatoreData.formattaDurata;
import java.sql.SQLException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RicercaBoundary {

    @FXML private TableView<ElementoMultimediale> risultatiTableView;
    @FXML private TableColumn<ElementoMultimediale, String> titoloTableColumn;
    @FXML private TableColumn<ElementoMultimediale, String> autoreTableColumn;
    @FXML private TableColumn<ElementoMultimediale, String> durataTableColumn;

    @FXML private TextField barraRicercaTextField;
    @FXML private Button cercaButton;
    @FXML private ComboBox<OggettoRicerca> oggettoComboBox;
    @FXML private ComboBox<CriterioRicerca> criterioComboBox;

    @FXML private Label messaggioLabel;

    ObservableList<ElementoMultimediale> elementiObservableList;

    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private void initialize() {
        // inizializzo i campi dei ComboBox con gli enum
        oggettoComboBox.getItems().setAll(OggettoRicerca.values());
        oggettoComboBox.setValue(OggettoRicerca.ELEMENTO_MULTIMEDIALE);

        criterioComboBox.getItems().setAll(CriterioRicerca.values());
        criterioComboBox.setValue(CriterioRicerca.TITOLO);
        //Listener per aggiornare il tipo di oggetto della ricerca
        oggettoComboBox.valueProperty().addListener(
                (osservato, vecchio, nuovo)
                                                                                    -> mostraTabella(nuovo));

        // Collega ogni colonna alla proprietà corrispondente dell'oggetto ElementoMultimediale
        titoloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreTableColumn.setCellValueFactory(riga -> {
            Utente autore = riga.getValue().getAutore();
            return new SimpleStringProperty((autore == null) ? "Sconosciuto" : autore.getUsername() );
        });
        durataTableColumn.setCellValueFactory(riga ->
                new SimpleStringProperty(formattaDurata(riga.getValue().getDurata())));
    }

    //per adattare la tabella in base al tipo di oggetto selezionato(playlist, album, elemento)
    private void mostraTabella(OggettoRicerca nuovo) {
    }

    @FXML
    public void onEseguiRicerca() {
        try {
            String valoreRicerca = barraRicercaTextField.getText();
            CriterioRicerca criterioSelezionato = criterioComboBox.getValue();
            OggettoRicerca oggettoSelezionato = oggettoComboBox.getValue();

            //da implementare lo switch per il tipo di oggetto selezionato

            List<ElementoMultimediale> risultatiList =
                    ElementoMultimedialeControl.getInstance().cerca(criterioSelezionato, valoreRicerca);

            //Converto la lista in un ObservableList per la visualizzazione nella tabella
            elementiObservableList = FXCollections.observableArrayList(risultatiList);
            risultatiTableView.setItems(elementiObservableList);

            messaggioLabel.setText("Trovati " + risultatiList.size() + " elementi.");
        }
        catch (SQLException e) {
            messaggioLabel.setText("Errore durante la ricerca: " + e.getMessage());
        }
    }

    @FXML
    public void onIndietro(MouseEvent mouseEvent) {
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("Home.fxml");
    }
}
