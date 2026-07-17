package it.unina.uninamulticloud.entity;

import java.time.LocalDateTime;

public class PlaylistPrivata extends Playlist {

    public PlaylistPrivata(int idPlaylist, String titolo, LocalDateTime dataPubblicazione, String descrizione) {
        super(idPlaylist, titolo, dataPubblicazione, descrizione);
    }

}