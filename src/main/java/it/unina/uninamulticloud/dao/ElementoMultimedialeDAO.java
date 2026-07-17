package it.unina.uninamulticloud.dao;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import java.sql.SQLException;
import java.util.List;

public interface ElementoMultimedialeDAO {

        /**
         * Inserisce un nuovo elemento multimediale nel database.
         * @param elemento L'istanza concreta (Audio o Video) da salvare.
         * @throws SQLException Se si verifica un errore SQL o di violazione dei vincoli.
         */
        void insert(ElementoMultimediale elemento) throws SQLException;

        /**
         * Recupera un singolo elemento multimediale tramite il suo ID.
         * @param idElementoMultimediale ID dell'elemento.
         * @return L'istanza dell'elemento (Audio o Video), o null se non trovato.
         * @throws SQLException In caso di errore di accesso al DB.
         */
        ElementoMultimediale findById(long idElementoMultimediale) throws SQLException;

        /**
         * Restituisce tutti gli elementi multimediali presenti sulla piattaforma.
         */
        List<ElementoMultimediale> findAll() throws SQLException;

        /**
         * Filtra gli elementi multimediali in base all'autore, accettando sia la matricola che lo username.
         * @param matricolaOrUsername Identificativo testuale dell'autore.
         */
        List<ElementoMultimediale> findByAutore(String matricolaOrUsername) throws SQLException;

        /**
         * Filtra gli elementi multimediali in base alla tipologia di playlist in cui sono inseriti.
         * @param tipoPlaylist Il tipo di playlist ('pubblica', 'privata', 'condivisa').
         */
        List<ElementoMultimediale> findByTipologiaPlaylist(String tipoPlaylist) throws SQLException;

}
