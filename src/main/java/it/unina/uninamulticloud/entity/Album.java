package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Album {
    private Long idAlbum;
    private String titolo;
    private LocalDateTime dataPubblicazione;
    private String descrizione;
    private Utente autore;
    private List<ElementoMultimediale> listaElementi;

    public Album(Long idAlbum, String titolo, LocalDateTime dataPubblicazione, String descrizione, Utente autore,
                                                                        List<ElementoMultimediale> listaElementi) {
        this.idAlbum = idAlbum;
        this.titolo = titolo;
        this.dataPubblicazione = dataPubblicazione;
        this.descrizione = descrizione;
        this.autore = autore;
        this.listaElementi = listaElementi;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
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

    public Utente getAutore() {
        return autore;
    }
    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    public List<ElementoMultimediale> getListaElementi() {
        return listaElementi;
    }

    public void setListaElementi(List<ElementoMultimediale> listaElementi) {
        this.listaElementi = listaElementi;
    }
}
