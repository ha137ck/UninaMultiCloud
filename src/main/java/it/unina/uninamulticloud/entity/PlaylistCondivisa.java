package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlaylistCondivisa extends Playlist{

    public PlaylistCondivisa (long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durataTotale) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione, durataTotale);
    }

    public PlaylistCondivisa(String titolo, String descrizione) {
        super(titolo, descrizione);
    }

}
