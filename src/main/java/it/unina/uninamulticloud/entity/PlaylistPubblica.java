package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public class PlaylistPubblica extends Playlist{

    public PlaylistPubblica (int idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione);
    }

}
