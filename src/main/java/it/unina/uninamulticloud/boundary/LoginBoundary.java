package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginBoundary {

    //Variabili interfaccia
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private AutenticazioneControl autenticazioneControl = AutenticazioneControl.getInstance();

    @FXML
    public void onAccedi() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validazione base
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            showError("Inserisci tutti i campi.");
            return;
        }

        // logica nel controller
        String errore = autenticazioneControl.login(email, password);

        if (errore == null) {
            showSuccess();
            // passaggio alla schermata successiva
            SceneManager.getInstance().switchScene("Home.fxml");
        } else {
            showError(errore);
        }
    }

    @FXML
    public void onRegistrati() {
        System.out.println("Navigazione verso la schermata di registrazione...");
        SceneManager.getInstance().switchScene("Registrazione.fxml");
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