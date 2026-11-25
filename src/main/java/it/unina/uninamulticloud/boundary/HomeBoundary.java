package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.control.PlaylistControl;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeBoundary {

    //variabili interfaccia
    @FXML private Label welcomeLabel;
    @FXML private Button cercaButton;
    @FXML private Button creaPlaylistButton;
    @FXML private Button caricaElementoButton;
    @FXML private Button caricaAlbumButton;
    @FXML private Button libreriaButton;
    @FXML private Button profiloButton;
    @FXML private FlowPane pubblicazioniFlowPane;
    @FXML private FlowPane playlistFlowPane;
    @FXML private FlowPane albumFlowPane;

    private AutenticazioneControl autenticazioneControl = AutenticazioneControl.getInstance();
    private ElementoMultimedialeControl elementoMultimedialeControl = ElementoMultimedialeControl.getInstance();
    private PlaylistControl playlistControl;


    @FXML
    public void initialize() {

        // Controllo se l'utente è loggato, altrimenti reindirizzo alla schermata di login
        if(autenticazioneControl.getUtenteLoggato() == null) {
            System.out.println("Accesso negato, reindirizzamento alla schermata di login...");
            SceneManager.getInstance().switchScene("Login.fxml");
            return;
        }

        welcomeLabel.setText(autenticazioneControl.getMessaggioBenvenuto());

        //TODO mostraPubblicazioni();
        mostraPubblicazioni();


        //TODO mostraPlaylist();
    }

    @FXML
    public void onCaricaElemento() {
        System.out.println("Apro schermata caricamento elemento...");
        SceneManager.getInstance().switchScene("UploadElemento.fxml");
    }

    @FXML
    public void onCreaPlaylist() {
        System.out.println("Apro schermata creazione playlist...");
        SceneManager.getInstance().switchScene("UploadPlaylist.fxml");
    }

    @FXML
    public void onCerca() {
        SceneManager.getInstance().switchScene("Ricerca.fxml");
        System.out.println("Apro schermata di ricerca...");
    }

    @FXML
    public void onMioProfilo() {
        System.out.println("Apro schermata profilo utente...");
        SceneManager.getInstance().switchScene("MioProfilo.fxml");
    }

    //per aprire la libreria
    @FXML
    private void onApriLibreria() {
        SceneManager.getInstance().switchScene("Libreria.fxml");
    }

    // Questi metodi riceveranno probabilmente l'ID dell'elemento cliccato come parametro
    public void onPlaylistCard(long idPlaylist) {
        System.out.println("Apro il dettaglio della playlist: " + idPlaylist);
    }

    public void onElementoCard(long idElemento) {
        System.out.println("Avvio la riproduzione dell'elemento: " + idElemento);
    }

    private void mostraPubblicazioni() {
        pubblicazioniFlowPane.getChildren().clear();

        try {
            List<ElementoMultimediale> pubblicazioni = elementoMultimedialeControl.getElementiUtenteLoggato();

            if (pubblicazioni.isEmpty()) {
                Label emptyLabel = new Label("Non hai ancora pubblicato nessun elemento.");
                emptyLabel.setStyle("-fx-text-fill: #64748B; -fx-font-size: 14px;");
                pubblicazioniFlowPane.getChildren().add(emptyLabel);
                return;
            }

            for (ElementoMultimediale elemento : pubblicazioni) {
                // Verifica che la cartella (/fxml/ o /view/) coincida con quella in cui tieni Home.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninamulticloud/fxml/MediaCard.fxml"));
                Parent cardNode = loader.load();

                MediaCardBoundary cardController = loader.getController();
                String tipoBadge = (elemento instanceof Audio) ? "Audio" : "Video";

                cardController.setData(
                        elemento.getTitolo(),
                        elemento.getDescrizione(),
                        elemento.getImmagineCopertina(),
                        tipoBadge,
                        () -> onElementoCard(elemento.getIdElementoMultimediale())
                );

                pubblicazioniFlowPane.getChildren().add(cardNode);
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero delle pubblicazioni: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore nel caricamento di MediaCard.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}