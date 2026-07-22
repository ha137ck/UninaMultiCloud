package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.ReportControl;
import it.unina.uninamulticloud.entity.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MioProfiloBoundary {

    //variabili interfaccia
    @FXML Label nomeLabel;
    @FXML Label cognomeLabel;
    @FXML Label emailLabel;
    @FXML Label usernameLabel;
    @FXML Label matricolaLabel;
    @FXML Label genereLabel;
    @FXML Label dataNascitaLabel;

    private ReportControl reportControl = new ReportControl();

    @FXML
    public void initialize() {
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
    public void onReport() {
        try {
            // Carico FXML popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninamulticloud/fxml/ReportPopup.fxml"));
            Parent root = loader.load();

            // Creo nuovo Stage
            Stage popupStage = new Stage();
            popupStage.setTitle("Il Mio Report");

            // Imposto la finestra come MODALE
            // Questo blocca i click sulla schermata del profilo finché il popup non viene chiuso
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Inserisco la grafica nella finestra e la mostro
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.setResizable(false); // Impedisce all'utente di ridimensionare il popup

            // Usa showAndWait() invece di show(), così aspetta la chiusura
            popupStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Errore durante l'apertura del popup del report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onIndietro(){
        System.out.println("Torno alla home...");
        SceneManager.getInstance().switchScene("Home.fxml");
    }

    public void onLogout() {
        System.out.println("Logout in corso...");
        reportControl.logout();
        SceneManager.getInstance().switchScene("Login.fxml");
    }
}
