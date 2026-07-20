package it.unina.uninamulticloud.entity;

public class Composizione {

    private int posizione;
    private ElementoMultimediale elemento;
    private Playlist playlist;

    public Composizione(int posizione, ElementoMultimediale elemento, Playlist playlist) {
        this.posizione = posizione;
        this.elemento = elemento;
        this.playlist = playlist;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public ElementoMultimediale getElemento() {
        return elemento;
    }

    public void setElemento(ElementoMultimediale elemento) {
        this.elemento = elemento;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
