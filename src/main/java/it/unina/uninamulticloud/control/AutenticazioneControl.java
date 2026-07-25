package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.dao.UniversitaDAO;
import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.dao.postgresql.UniversitaDAOImpl;
import it.unina.uninamulticloud.dao.postgresql.UtenteDAOImpl;
import it.unina.uninamulticloud.entity.Utente;

public class AutenticazioneControl {

    private static AutenticazioneControl instance;

    private UtenteDAO utenteDAO;
    private UniversitaDAO universitaDAO;
    private static Utente utenteLoggato; // Variabile per memorizzare l'utente loggato

    private AutenticazioneControl() {
        this.utenteDAO = new UtenteDAOImpl();
        this.universitaDAO = new UniversitaDAOImpl();
    }

    public static AutenticazioneControl getInstance() {
        if (instance == null) {
            instance = new AutenticazioneControl();
        }
        return instance;
    }

    public String login(String email, String password) {
        Utente utenteTrovato = null;

        try {
            // Interazione con la Entity (il DAO)
            utenteTrovato = utenteDAO.findByEmail(email);

        } catch (Exception e) {

            System.out.println("Errore durante il login: " + e.getMessage());
            e.printStackTrace();
            return "Errore durante il login. Riprova più tardi.";

        }

        if (utenteTrovato != null && utenteTrovato.getPassword().equals(password)) {

            utenteLoggato = utenteTrovato;
            return null; // Nessun errore

        } else {

            return "Credenziali non valide. Riprovare.";

        }
    }

    public String registra(Utente utente) {

        try {

            if(utenteDAO.findByEmail(utente.getEmail()) != null) {
                System.out.println("Errore: Email già registrata.");
                return "Email già registrata.";
            }

            if(utenteDAO.existsByUsername(utente.getUsername())) {
                System.out.println("Errore: Username già registrato.");
                return "Username già registrato.";
            }

            if(utenteDAO.existsByMatricola(utente.getMatricola())) {
                System.out.println("Errore: Matricola già registrata.");
                return "Matricola già registrata.";
            }

            //imposto l'universtià = Federico II
            utente.setUniversita(universitaDAO.findById(1));

            utenteDAO.saveUtente(utente);
            return null; // Nessun errore

        } catch (Exception e){
            System.out.println("Errore durante la registrazione: " + e.getMessage());
            e.printStackTrace();
            return "Errore interno durante la registrazione.";
        }
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public String getMessaggioBenvenuto() {
        if (utenteLoggato == null) {
            return "Errore: utente non loggato.";
        }

        String saluto = "Benvenut*";

        if (utenteLoggato.getGenere() == null) {
            saluto = "Benvenuto ricchione, tuo padre ti odia";
        } else if (utenteLoggato.getGenere().name().equals("M")) {
            saluto = "Benvenuto";
        } else if (utenteLoggato.getGenere().name().equals("F")) {
            saluto = "Benvenuta";
        } else {
            saluto = "Benvenuto ricchione, tuo padre ti odia";
        }

        return saluto + " " + utenteLoggato.getNome();
    }

    public void logout() {
        utenteLoggato = null;
    }
}