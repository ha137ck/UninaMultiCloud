package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Playlist {
    private long idPlaylist;
    private String titolo;
    private LocalDateTime dataPubblicazione;
    private String descrizione;
    private Duration durata;

    public Playlist(long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durata) {
        this.idPlaylist = idPlaylist;
        this.titolo = titolo;
        this.dataPubblicazione = dataPubblicazione;
        this.descrizione = descrizione;
        this.durata = durata;
    }

    public long getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(long idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public LocalDateTime getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(LocalDateTime dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Duration getDurata() {
        return durata;
    }

    public void setDurata(Duration durata) {
        this.durata = durata;
    }
}
