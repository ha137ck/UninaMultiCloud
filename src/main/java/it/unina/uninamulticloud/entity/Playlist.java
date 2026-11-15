package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Playlist {
    private long idPlaylist;
    private String titolo;
    private LocalDateTime dataPubblicazione;
    private String descrizione;
    private Duration durataTotale;
    private Utente proprietario; //lato N:1

    public Playlist(long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durataTotale) {
        this.idPlaylist = idPlaylist;
        this.titolo = titolo;
        this.dataPubblicazione = dataPubblicazione;
        this.descrizione = descrizione;
        this.durataTotale = durataTotale;
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

    public Duration getDurataTotale() {
        return durataTotale;
    }

    public void setDurataTotale(Duration durataTotale) {
        this.durataTotale = durataTotale;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
}
