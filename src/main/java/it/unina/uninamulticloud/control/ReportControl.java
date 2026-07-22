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

}