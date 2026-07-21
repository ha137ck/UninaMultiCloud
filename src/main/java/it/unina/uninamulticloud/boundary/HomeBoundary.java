package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
import it.unina.uninamulticloud.control.ElementoMultimedialeControl;
import it.unina.uninamulticloud.control.PlaylistControl;
import it.unina.uninamulticloud.entity.Utente;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.awt.*;

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

    private AutenticazioneControl autenticazioneControl;
    private ElementoMultimedialeControl elementoMultimedialeControl;
    private PlaylistControl playlistControl;

    @FXML
    public void initialize() {
        this.autenticazioneControl = new AutenticazioneControl(this);
        this.elementoMultimedialeControl = new ElementoMultimedialeControl();
        this.playlistControl = new PlaylistControl();

        Utente utenteAttivo = autenticazioneControl.getUtenteLoggato();
        if (utenteAttivo != null) {
            welcomeLabel.setText("Benvenuto/a " + utenteAttivo.getUsername());
        }else {
            System.out.println("Errore: utente non loggato.");
            SceneManager.getInstance().switchScene("Login.fxml");
        }

        //mostraPubblicazioni();
        //mostraPlaylist();
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
    public void onProfilo() {
        System.out.println("Apro schermata profilo utente...");
    }

    // Questi metodi riceveranno probabilmente l'ID dell'elemento cliccato come parametro
    public void onPlaylistCard(int idPlaylist) {
        System.out.println("Apro il dettaglio della playlist: " + idPlaylist);
    }

    public void onElementoCard(int idElemento) {
        System.out.println("Avvio la riproduzione dell'elemento: " + idElemento);
    }

}
