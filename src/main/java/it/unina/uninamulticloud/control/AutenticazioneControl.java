package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.SceneManager;
import it.unina.uninamulticloud.boundary.LoginBoundary;
import it.unina.uninamulticloud.boundary.RegistrazioneBoundary;
import it.unina.uninamulticloud.dao.UniversitaDAO;
import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.dao.postgresql.UniversitaDAOImpl;
import it.unina.uninamulticloud.dao.postgresql.UtenteDAOImpl;
import it.unina.uninamulticloud.entity.Utente;

public class AutenticazioneControl {
    private LoginBoundary loginView; // Il Controller deve poter parlare con la vista
    private RegistrazioneBoundary registrazioneView;
    private UtenteDAO utenteDAO = new UtenteDAOImpl();
    private UniversitaDAO universitaDAO = new UniversitaDAOImpl();

    public AutenticazioneControl(LoginBoundary view) {
        this.loginView = view;
    }

    public AutenticazioneControl(RegistrazioneBoundary view) {
        this.registrazioneView = view;
    }

    public void login(String email, String password) {

        // Validazione base
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            loginView.showError("Inserisci tutti i campi.");
            return;
        }

        Utente utenteTrovato = null;

        try {
            // Interazione con la Entity (il DAO)
            utenteTrovato = utenteDAO.findByEmail(email);
        } catch (Exception e) {
            loginView.showError("Errore durante il login. Riprova più tardi.");
            System.out.println("Errore durante il login: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (utenteTrovato != null && utenteTrovato.getPassword().equals(password)) {
            loginView.showSuccess();
            // passaggio alla schermata successiva
            SceneManager.getInstance().switchScene("Home.fxml");
        } else {
            loginView.showError("Credenziali non valide. Riprovare.");
        }
    }

    public void registra(Utente utente) {
        try {
            if(utenteDAO.findByEmail(utente.getEmail()) != null) {
                System.out.println("Errore: Email già registrata.");
                registrazioneView.showError("Email già registrata.");
                return;
            }
            //imposto l'universtià = Federico II
            utente.setUniversita(universitaDAO.findById(1));

            utenteDAO.saveUtente(utente);
            registrazioneView.showSuccess();
            SceneManager.getInstance().switchScene("login.fxml");

        } catch (Exception e){
            System.out.println("Errore durante la registrazione: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
