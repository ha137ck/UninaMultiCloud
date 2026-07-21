package it.unina.uninamulticloud.entity;

public class Ordine {
    private int posizione;
    private Album album;
    private ElementoMultimediale elemento;

    public Ordine(int posizione, Album album, ElementoMultimediale elemento) {
        this.posizione = posizione;
        this.album = album;
        this.elemento = elemento;
    }

    public int getPosizione() {
        return posizione;
    }

    public Album getAlbum() {
        return album;
    }

    public ElementoMultimediale getElemento() {
        return elemento;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setElemento(ElementoMultimediale elemento) {
        this.elemento = elemento;
    }

    @Override
    public String toString() {
        return "Ordine{" + "posizione=" + posizione + ", album=" + album + ", elemento=" + elemento + '}';
    }
}
