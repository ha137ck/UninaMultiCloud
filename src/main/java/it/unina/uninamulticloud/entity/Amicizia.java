package it.unina.uninamulticloud.entity;

import it.unina.uninamulticloud.entity.enums.StatoRichiesta;

import java.time.LocalDateTime;

public class Amicizia {
    private LocalDateTime dataRichiesta;
    private StatoRichiesta stato;
    private Utente richiedente;
    private Utente amico;

    public Amicizia(LocalDateTime dataRichiesta, StatoRichiesta stato, Utente richiedente, Utente amico) {
        this.dataRichiesta = dataRichiesta;
        this.stato = stato;
        this.richiedente = richiedente;
        this.amico = amico;
    }

    public LocalDateTime getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(LocalDateTime dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public StatoRichiesta getStato() {
        return stato;
    }

    public void setStato(StatoRichiesta stato) {
        this.stato = stato;
    }

    public Utente getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(Utente richiedente) {
        this.richiedente = richiedente;
    }

    public Utente getAmico() {
        return amico;
    }

    public void setAmico(Utente amico) {
        this.amico = amico;
    }
}
