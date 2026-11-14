package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlaylistPubblica extends Playlist{

    public PlaylistPubblica (long idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione, Duration durata) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione, durata);
    }

}
