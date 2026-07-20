package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.control.AutenticazioneControl;
import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.dao.postgresql.UtenteDAOImpl;
import it.unina.uninamulticloud.entity.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginBoundary {

    //Variabili interfaccia
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button accediButton;
    @FXML private Hyperlink registratiLink;
    @FXML private Label errorLabel;

    private AutenticazioneControl autenticazioneControl = new AutenticazioneControl(this);

    @FXML
    public void onAccedi() {
        String identificativo = emailField.getText();
        String password = passwordField.getText();

        // La logica si sposta nel Controller!
        autenticazioneControl.login(identificativo, password);
    }

    @FXML
    public void onRegistrati() {
        // TODO: SceneManager per caricare la schermata di registrazione
        System.out.println("Navigazione verso la schermata di registrazione...");
    }

    public void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }

    public void showSuccess() {
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Login effettuato con successo!");
    }
}
