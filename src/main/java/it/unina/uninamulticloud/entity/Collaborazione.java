package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public class Collaborazione {
    private LocalDateTime dataInvito;
    private Utente collaboratore;
    private PlaylistCondivisa playlistCondivisa;

    public Collaborazione(LocalDateTime dataInvito, Utente collaboratore, PlaylistCondivisa playlistCondivisa) {
        this.dataInvito = dataInvito;
        this.collaboratore = collaboratore;
        this.playlistCondivisa = playlistCondivisa;
    }

    public LocalDateTime getDataInvito() {
        return dataInvito;
    }

    public void setDataInvito(LocalDateTime dataInvito) {
        this.dataInvito = dataInvito;
    }

    public Utente getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(Utente collaboratore) {
        this.collaboratore = collaboratore;
    }

    public PlaylistCondivisa getPlaylistCondivisa() {
        return playlistCondivisa;
    }

    public void setPlaylistCondivisa(PlaylistCondivisa playlistCondivisa) {
        this.playlistCondivisa = playlistCondivisa;
    }
}
