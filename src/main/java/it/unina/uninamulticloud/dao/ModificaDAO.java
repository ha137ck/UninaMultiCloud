package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Modifica;
import java.util.List;

public interface ModificaDAO {

    /**
     * Registra una nuova operazione effettuata su una playlist condivisa.
     */
    void save(Modifica modifica);

    /**
     * Recupera lo storico di tutte le modifiche fatte a una specifica playlist,
     * ordinate dalla più recente alla più vecchia.
     */
    List<Modifica> findByPlaylist(int idPlaylist);
}