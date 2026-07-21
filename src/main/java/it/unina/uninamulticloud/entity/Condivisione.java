package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public class Condivisione {

    private LocalDateTime dataInvito;
    private Utente utente;
    private PlaylistCondivisa playlistCondivisa;

    public Condivisione(LocalDateTime dataInvito, Utente utente, PlaylistCondivisa playlistCondivisa) {
        this.dataInvito = dataInvito;
        this.utente = utente;
        this.playlistCondivisa = playlistCondivisa;
    }

    public LocalDateTime getDataInvito() {
        return dataInvito;
    }

    public void setDataInvito(LocalDateTime dataInvito) {
        this.dataInvito = dataInvito;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public PlaylistCondivisa getPlaylistCondivisa() {
        return playlistCondivisa;
    }

    public void setPlaylistCondivisa(PlaylistCondivisa playlistCondivisa) {
        this.playlistCondivisa = playlistCondivisa;
    }

    @Override
    public String toString() {
        return "Condivisione{" +
                "dataInvito=" + dataInvito +
                ", utente=" + utente +
                ", playlistCondivisa=" + playlistCondivisa +
                '}';
    }
}
