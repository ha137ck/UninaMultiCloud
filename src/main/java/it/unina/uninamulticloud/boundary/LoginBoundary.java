package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
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
        String email = emailField.getText();
        String password = passwordField.getText();

        // logica nel controller
        autenticazioneControl.login(email, password);
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
