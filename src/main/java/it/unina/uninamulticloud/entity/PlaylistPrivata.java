package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlaylistPrivata extends Playlist {

    public PlaylistPrivata(long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durataTotale) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione, durataTotale);
    }

    public PlaylistPrivata(String titolo, String descrizione) {
        super(titolo, descrizione);
    }

}