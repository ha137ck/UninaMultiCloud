package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.unina.uninamulticloud.util.FormattatoreData.formattaData;
import static it.unina.uninamulticloud.util.FormattatoreData.formattaDurata;

public class LibreriaBoundary {

    @FXML private TableView<ElementoMultimediale> tabellaElementi;
    @FXML private TableColumn<ElementoMultimediale, String> colTitolo;
    @FXML private TableColumn<ElementoMultimediale, String> colDurata;
    @FXML private TableColumn<ElementoMultimediale, String> colTipo;
    @FXML private TableColumn<ElementoMultimediale, String> colFormato;
    @FXML private TableColumn<ElementoMultimediale, String> colDataCreazione;
    @FXML private TableColumn<ElementoMultimediale, ElementoMultimediale> immagineCopertina;

    @FXML private Label lblMessaggio;


    //Chiamato al caricamento della pagina da FxmlLoader
    @FXML public void initialize() {
        //ogni colonna ricava il testo da mostrare a partire da un elemento
        colTitolo.setCellValueFactory(riga -> new SimpleStringProperty(riga.getValue().getTitolo()));
        colTipo.setCellValueFactory(riga -> new SimpleStringProperty(
                riga.getValue() instanceof Audio ? "Audio" : "Video"));
        colDurata.setCellValueFactory(riga -> new SimpleStringProperty(
                formattaDurata(riga.getValue().getDurata()))); //formattaDurata è un metodo custom di FormattatoreData
        colFormato.setCellValueFactory(riga -> new SimpleStringProperty(riga.getValue().getFormato()));
        colDataCreazione.setCellValueFactory(riga ->
                                                                    new SimpleStringProperty(formattaData(riga.getValue().getDataCreazione())));

        immagineCopertina.setCellValueFactory(riga ->
                new javafx.beans.property.ReadOnlyObjectWrapper<>(riga.getValue()));
        immagineCopertina.setCellFactory(colonna -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
            }

            @Override
            protected void updateItem(ElementoMultimediale elemento, boolean empty) {
                super.updateItem(elemento, empty);

                if (empty || elemento == null || elemento.getImmagineCopertina() == null
                        || elemento.getImmagineCopertina().isBlank()) {
                    setGraphic(null);
                    return;
                }

                try {
                    String percorso = elemento.getImmagineCopertina();
                    String url = percorso.startsWith("file:") || percorso.startsWith("http")
                            ? percorso
                            : new File(percorso).toURI().toString();
                    Image immagine = new Image(url, 30, 30, true, true, true);
                    if (immagine.isError()) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(immagine);
                        setGraphic(imageView);
                    }
                } catch (IllegalArgumentException e) {
                    setGraphic(null);
                }
            }
        });

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
}
