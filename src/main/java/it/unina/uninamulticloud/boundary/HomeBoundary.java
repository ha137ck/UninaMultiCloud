package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.control.PlaylistControl;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeBoundary {

    //variabili interfaccia
    @FXML private Label welcomeLabel;
    @FXML private Button homeButton; // TODO a che serve se siamo gia nella home?
    @FXML private Button cercaButton;
    @FXML private Button creaPlaylistButton;
    @FXML private Button caricaElementoButton;
    @FXML private Button caricaAlbumButton;
    @FXML private Button profiloButton;
    @FXML private FlowPane pubblicazioniFlowPane;
    @FXML private FlowPane playlistFlowPane;

    private AutenticazioneControl autenticazioneControl = AutenticazioneControl.getInstance();
    private ElementoMultimedialeControl elementoMultimedialeControl;
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

        //TODO mostraPlaylist();
    }

    @FXML
    public void onCaricaElemento() {
        System.out.println("Apro schermata caricamento elemento...");
    }

    @FXML
    public void onCreaPlaylist() {
        System.out.println("Apro schermata creazione playlist...");
    }

    @FXML
    public void onCerca() {
        System.out.println("Apro schermata di ricerca...");
    }

    @FXML
    public void onMioProfilo() {
        System.out.println("Apro schermata profilo utente...");
        SceneManager.getInstance().switchScene("MioProfilo.fxml");
    }

    // Questi metodi riceveranno probabilmente l'ID dell'elemento cliccato come parametro
    public void onPlaylistCard(int idPlaylist) {
        System.out.println("Apro il dettaglio della playlist: " + idPlaylist);
    }

    public void onElementoCard(int idElemento) {
        System.out.println("Avvio la riproduzione dell'elemento: " + idElemento);
    }

}