package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public class PlaylistCondivisa extends Playlist{

    public PlaylistCondivisa (int idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione);
    }

}
