package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.entity.Utente;

public class ReportControl {

    public ReportControl() {
    }

    public Utente getProfiloUtente() {
        return AutenticazioneControl.getInstance().getUtenteLoggato();
    }

    public void logout() {
        AutenticazioneControl.getInstance().logout();
    }

    public int getNumeroPlaylistUtente() {
        Utente utente = AutenticazioneControl.getInstance().getUtenteLoggato();
        // int numero = playlistDAO.countByUtente(utente.getMatricola());
        return 5; // Numero di esempio
    }

    public int getNumeroPubblicazioniUtente() {
        Utente utente = AutenticazioneControl.getInstance().getUtenteLoggato();
        // int numero = pubblicazioneDAO.countByUtente(utente.getMatricola());
        return 12; // Numero di esempio
    }

}