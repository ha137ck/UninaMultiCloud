package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Composizione;
import java.util.List;

public interface ComposizioneDAO {

    /**
     * Inserisce un nuovo elemento in coda alla playlist.
     * Calcola in automatico MAX(posizione) + 1.
     */
    void save(int idPlaylist, long idElementoMultimediale);

    /**
     * Elimina un elemento da una specifica posizione e ricompatta
     * le posizioni successive per non lasciare "buchi".
     */
    void delete(int idPlaylist, int posizione);

    /**
     * Scambia la posizione di due elementi all'interno della stessa playlist.
     */
    void swapPosizioni(int idPlaylist, int pos1, int pos2);

    /**
     * Recupera tutti gli elementi di una playlist, ordinati per posizione.
     */
    List<Composizione> findByPlaylist(int idPlaylist);
}
