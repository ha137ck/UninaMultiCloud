package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Collaborazione;
import java.util.List;

public interface CollaborazioneDAO {

    /**
     * Inserisce un nuovo collaboratore in una playlist condivisa.
     */
    void save(Collaborazione collaborazione);

    /**
     * Rimuove un collaboratore da una playlist.
     * Serve la PK composta (matricola e idPlaylist).
     */
    void delete(String matricola, int idPlaylist);

    /**
     * Recupera tutti i collaboratori invitati a una specifica playlist.
     */
    List<Collaborazione> findByPlaylist(int idPlaylist);

    /**
     * Recupera tutte le playlist condivise a cui un utente è stato invitato.
     */
    List<Collaborazione> findByUtente(String matricola);
}
