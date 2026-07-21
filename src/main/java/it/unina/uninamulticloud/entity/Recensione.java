package it.unina.uninamulticloud.entity;

public class Recensione {
    private String descrizione;
    private int punteggio;
    private Utente utente;
    private ElementoMultimediale elemento;
    private Album album;
    private Playlist playlist;

    //immagino di settare poi l'oggetto della recensione
    public Recensione(String descrizione, int punteggio, Utente utente) {
        this.descrizione = descrizione;
        this.punteggio = punteggio;
        this.utente = utente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ElementoMultimediale getElemento() {
        return elemento;
    }

    public void setElemento(ElementoMultimediale elemento) {
        this.elemento = elemento;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
