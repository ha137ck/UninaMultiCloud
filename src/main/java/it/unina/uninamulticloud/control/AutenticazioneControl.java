package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.boundary.LoginBoundary;
import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.dao.postgresql.UtenteDAOImpl;
import it.unina.uninamulticloud.entity.Utente;

public class AutenticazioneControl {
    private LoginBoundary view; // Il Controller deve poter parlare con la vista
    private UtenteDAO utenteDAO = new UtenteDAOImpl();

    public AutenticazioneControl(LoginBoundary view) {
        this.view = view;
    }

    public void login(String email, String password) {
        // 1. Validazione base
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            view.showError("Inserisci tutti i campi.");
            return;
        }

        // 2. Interazione con la Entity (il DAO)
        Utente utenteTrovato = utenteDAO.findByMatricola(email);

        // 3. Logica di business
        if (utenteTrovato != null && utenteTrovato.getPassword().equals(password)) {
            view.showSuccess();
            // passaggio alla schermata successiva
            SceneManager.getInstance().switchScene("Home.fxml");
        } else {
            view.showError("Credenziali non valide. Riprovare.");
        }
    }
}
