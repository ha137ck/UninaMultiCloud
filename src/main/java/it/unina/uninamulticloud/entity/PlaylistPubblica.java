package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlaylistPubblica extends Playlist{

    public PlaylistPubblica (long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durataTotale) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione, durataTotale);
    }

    public PlaylistPubblica(String titolo, String descrizione) {
        super(titolo, descrizione);
    }

}
