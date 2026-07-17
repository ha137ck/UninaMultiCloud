package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Playlist;
import java.util.List;

public interface PlaylistDAO {

    Playlist findById(int idPlaylist);

    List<Playlist> findByProprietario(String matricola);

    void save(Playlist playlist, String matricolaProprietario);

    void delete(int idPlaylist);
}