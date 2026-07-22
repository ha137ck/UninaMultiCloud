package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ReportControl;
import it.unina.uninamulticloud.entity.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MioProfiloBoundary {

    //variabili interfaccia
    @FXML Label nomeLabel;
    @FXML Label cognomeLabel;
    @FXML Label emailLabel;
    @FXML Label usernameLabel;
    @FXML Label matricolaLabel;
    @FXML Label genereLabel;
    @FXML Label dataNascitaLabel;

    private ReportControl reportControl;

    @FXML
    public void initialize() {
        this.reportControl = new ReportControl();

        // Recupera i dati dell'utente loggato
        Utente utente = reportControl.getProfiloUtente();

        if(utente == null){
            System.out.println("Attenzione: Accesso negato al profilo. Reindirizzamento...");
            SceneManager.getInstance().switchScene("/it/unina/uninamulticloud/fxml/Login.fxml");
            return;
        }

        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        emailLabel.setText(utente.getEmail());
        usernameLabel.setText(utente.getUsername());
        matricolaLabel.setText(utente.getMatricola());
        genereLabel.setText(utente.getGenere() != null ? utente.getGenere().name() : "");
        dataNascitaLabel.setText(utente.getDataNascita() != null ? utente.getDataNascita().toString() : "");

    }

    @FXML
    public void onIndietro(){
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("/it/unina/uninamulticloud/fxml/Home.fxml");
    }

    public void onLogout() {
        System.out.println("Logout in corso...");
        reportControl.logout();
        SceneManager.getInstance().switchScene("/it/unina/uninamulticloud/fxml/Login.fxml");
    }
}
