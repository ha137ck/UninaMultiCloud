package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.control.AutenticazioneControl;
import it.unina.uninamulticloud.entity.Universita;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.enums.Genere;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistrazioneBoundary {

    //variabili
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameField;
    @FXML private TextField matricolaField;
    @FXML private ComboBox<String> genereField;
    @FXML private DatePicker dataNascitaField;
    @FXML private Button registratiButton;
    @FXML private Hyperlink accediLink;
    @FXML private Label errorLabel;
    @FXML private ComboBox<String> universitaField;

    private Universita unina;
    private AutenticazioneControl autenticazioneControl;

    @FXML
    public void initialize() {
        autenticazioneControl = new AutenticazioneControl(this);
        for (Genere g : Genere.values()) {
            genereField.getItems().add(g.name());
        }

        unina = new Universita("Università degli Studi di Napoli Federico II", "Napoli");
        unina.setIdUniversita(1L);
        universitaField.getItems().add(unina.getNome());
    }

    @FXML
    public void onRegistrati(){
        if (!validateForm()) {
            return;
        }

        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String username = usernameField.getText().trim();
        String matricola = matricolaField.getText().trim();
        String genere = genereField.getValue();
        LocalDate dataNascita = dataNascitaField.getValue();
        LocalDateTime dataIscrizione = LocalDateTime.now();

        Utente nuovoUtente = new Utente(matricola, nome, cognome, username, password, email,
                dataNascita, dataIscrizione, Genere.valueOf(genere), unina);

        autenticazioneControl.registra(nuovoUtente);
    }


    @FXML
    public void onAccedi(){
        System.out.println("Navigazione verso la schermata di login...");
        SceneManager.getInstance().switchScene("Login.fxml");
    }

    @FXML
    public boolean validateForm(){
        // Controllo che i campi di testo non siano vuoti e che i menu/date siano selezionati
        if (nomeField.getText().trim().isEmpty() ||
                cognomeField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                passwordField.getText().trim().isEmpty() ||
                usernameField.getText().trim().isEmpty() ||
                matricolaField.getText().trim().isEmpty() ||
                genereField.getValue() == null ||
                dataNascitaField.getValue() == null) {
            showError("Compila tutti i campi obbligatori.");
            return false;
        }

        String emailInserita = emailField.getText().trim();
        if (!isEmailIstituzionale(emailInserita)) {
            showError("L'email deve essere nel formato nome.cognome@studenti.unina.it");
            return false;
        }

        String matricolaInserita = matricolaField.getText().trim();
        if (!isMatricolaValida(matricolaInserita)) {
            showError("La matricola deve contenere 2 lettere seguite da 7 numeri (es. AB1234567).");
            return false;
        }

        if (passwordField.getText().length() < 8) {
            showError("La password deve contenere almeno 8 caratteri.");
            return false;
        }

        if (universitaField.getValue() == null) {
            showError("Seleziona un'università.");
            return false;
        }

        return true;
    }

    @FXML
    public void showError(String msg){
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }

    public void showSuccess() {
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Registrazione completata con successo!");
    }

    private boolean isEmailIstituzionale(String email) {
        String emailRegex = "^[a-zA-Z]+\\.[a-zA-Z]+@studenti\\.unina\\.it$";
        return email.toLowerCase().matches(emailRegex);
    }

    private boolean isMatricolaValida(String matricola) {
        String matricolaRegex = "^[a-zA-Z]{2}[0-9]{7}$";
        return matricola.toUpperCase().matches(matricolaRegex);
    }

}


