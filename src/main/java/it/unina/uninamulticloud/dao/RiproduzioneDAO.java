package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Riproduzione;
import java.sql.SQLException;
import java.util.List;

public interface RiproduzioneDAO {

    /**
     * Registra un nuovo evento di riproduzione nel database.
     * * @param riproduzione L'oggetto entità contenente i dati della riproduzione.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void insert(Riproduzione riproduzione) throws SQLException;

    /**
     * Recupera lo storico cronologico delle riproduzioni effettuate da uno specifico utente.
     * * @param matricola La matricola dell'utente.
     * @return Una lista di riproduzioni ordinate dalla più recente.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<Riproduzione> findByUtente(String matricola) throws SQLException;


    //da valutare
    int countByElemento(long idElementoMultimediale) throws SQLException;

}