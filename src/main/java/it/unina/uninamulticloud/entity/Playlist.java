package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public abstract class Playlist {
    private int idPlaylist;
    private String titolo;
    private LocalDateTime dataPubblicazione;
    private String descrizione;

    public Playlist(int idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione) {
        this.idPlaylist = idPlaylist;
        this.titolo = titolo;
        this.dataPubblicazione = dataPubblicazione;
        this.descrizione = descrizione;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
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

}
