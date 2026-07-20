package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.ElementoMultimedialeDAO;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.Video;
import it.unina.uninamulticloud.util.DBConnection;

import javax.xml.datatype.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ElementoMultimedialeDAOImpl implements ElementoMultimedialeDAO {

    @Override
    public void insert(ElementoMultimediale elemento) throws SQLException {
        // idElementoMultimediale è gestito come BIGSERIAL (PK), pertanto viene omesso dall'insert statement
        String sql = "INSERT INTO ElementoMultimediale (titolo, durata, descrizione, " +
                "immagineCopertina, formato, filePath, tipo, risoluzione, bitRate, matricola) " +
                "VALUES (?, ?::interval, ?, ?, ?, ?, ?::TIPO_ELEMENTO_MULTIMEDIALE, ?, ?, ?)";

        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, elemento.getTitolo());
            // Il formato INTERVAL di PostgreSQL accetta stringhe standard nel formato "HH:MM:SS"
            // Duration.toString() restituisce una stringa nel formato "PT1H2M3S" accettato da postgresql
            statement.setString(2, elemento.getDurata().toString());
            statement.setString(3, elemento.getDescrizione());

            // Gestione dell'immagine di copertina (memorizzata come varchar nel DB)
            if (elemento.getImmagineCopertina() != null) {
                statement.setString(4, elemento.getImmagineCopertina());
            } else {
                // In caso di valore nullo, bisogna specificare il tipo SQL: VARCHAR
                statement.setNull(4, java.sql.Types.VARCHAR);
            }

            statement.setString(5, elemento.getFormato());
            statement.setString(6, elemento.getFilePath());

            // RISOLUZIONE POLIMORFICA DELLA GERARCHIA
            if (elemento instanceof Audio audio) {
                statement.setString(8, "audio");
                statement.setNull(9, Types.VARCHAR);          // risoluzione non presente per audio
                statement.setInt(10, audio.getBitRate());     // bitRate specifico per audio
            } else if (elemento instanceof Video video) {
                statement.setString(8, "video");
                statement.setString(9, video.getRisoluzione()); // risoluzione specifica per video
                statement.setNull(10, Types.INTEGER);          // bitRate non presente per video
            } else {
                throw new IllegalArgumentException("Sottoclasse di ElementoMultimediale non supportata.");
            }

            // Navigazione verso l'oggetto Utente (Autore) per estrarre la FK matricola
            if (elemento.getAutore() != null) {
                statement.setString(11, elemento.getAutore().getMatricola());
            } else {
                statement.setNull(11, Types.VARCHAR);
            }

            statement.executeUpdate();
        }
    }

    @Override
    public ElementoMultimediale findById(long idElementoMultimediale) throws SQLException {
        String sql = "SELECT e.*, u.username, u.nome, u.cognome " +
                "FROM ElementoMultimediale e " +
                "LEFT JOIN Utente u ON e.matricola = u.matricola " +
                "WHERE e.idElementoMultimediale = ?";

        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, idElementoMultimediale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<ElementoMultimediale> findAll() throws SQLException {
        String sql = "SELECT e.*, u.username, u.nome, u.cognome " +
                "FROM ElementoMultimediale e " +
                "LEFT JOIN Utente u ON e.matricola = u.matricola " +
                "ORDER BY e.dataCreazione DESC";

        return executeQueryList(sql, null);
    }

    @Override
    public List<ElementoMultimediale> findByAutore(String matricolaOrUsername) throws SQLException {
        // Consente la ricerca flessibile richiesta combinando matricola o username dell'utente
        String sql = "SELECT e.*, u.username, u.nome, u.cognome " +
                "FROM ElementoMultimediale e " +
                "JOIN Utente u ON e.matricola = u.matricola " +
                "WHERE u.matricola = ? OR u.username = ? " +
                "ORDER BY e.dataCreazione DESC";

        return executeQueryList(sql, statement -> {
            statement.setString(1, matricolaOrUsername);
            statement.setString(2, matricolaOrUsername);
        });
    }

    @Override
    public List<ElementoMultimediale> findByTipologiaPlaylist(String tipoPlaylist) throws SQLException {
        // Estrae gli elementi associati a playlist di una determinata tipologia tramite doppia JOIN
        String sql = "SELECT DISTINCT e.*, u.username, u.nome, u.cognome " +
                "FROM ElementoMultimediale e " +
                "JOIN Composizione c ON e.idElementoMultimediale = c.idElementoMultimediale " +
                "JOIN Playlist p ON c.idPlaylist = p.idPlaylist " +
                "LEFT JOIN Utente u ON e.matricola = u.matricola " +
                "WHERE p.tipo = ?::TIPO_PLAYLIST " +
                "ORDER BY e.idElementoMultimediale DESC";

        return executeQueryList(sql, statement ->
                statement.setString(1, tipoPlaylist.toLowerCase())
        );
    }


    // METODI di utilità


    /**
     * metodo per mappare una riga del ResultSet nell'apposita istanza concettuale.
     * Risolve a runtime l'istanza corretta (Audio o Video) basandosi sul discriminante del database.
     */
    private ElementoMultimediale mapRow(ResultSet resultSet) throws SQLException {
        String tipo = resultSet.getString("tipo");
        ElementoMultimediale e;

        // Risoluzione della Gerarchia del Database per rientrare nel modello Astratto/Concreto OOP
        if ("audio".equalsIgnoreCase(tipo)) {
            Audio audio = new Audio();
            audio.setBitRate(resultSet.getInt("bitRate"));
            e = audio;
        } else if ("video".equalsIgnoreCase(tipo)) {
            Video video = new Video();
            video.setRisoluzione(resultSet.getString("risoluzione"));
            e = video;
        } else {
            throw new SQLException("Rilevato un tipo non riconosciuto per l'elemento multimediale: " + tipo);
        }

        // Popolamento dei campi comuni ereditati dalla classe base astratta
        e.setIdElementoMultimediale(resultSet.getLong("idElementoMultimediale"));
        e.setTitolo(resultSet.getString("titolo"));

        //come gestire duration?
        //e.setDurata(resultSet.getString("durata") ); // Ottenuto convertendo l'intervallo Postgres in Stringa
        e.setDescrizione(resultSet.getString("descrizione"));

        if (resultSet.getTimestamp("dataCreazione") != null) {
            e.setDataCreazione(resultSet.getTimestamp("dataCreazione").toLocalDateTime());
        }

        e.setImmagineCopertina(resultSet.getString("immagineCopertina"));
        e.setFormato(resultSet.getString("formato"));
        e.setFilePath(resultSet.getString("filePath"));

        // Ricostruzione dell'associazione ad Oggetti (Shallow loading dell'autore dell'elemento)
        String matricola = resultSet.getString("matricola");
        if (matricola != null) {
            Utente utente = new Utente();
            utente.setMatricola(matricola);
            utente.setUsername(resultSet.getString("username"));
            utente.setNome(resultSet.getString("nome"));
            utente.setCognome(resultSet.getString("cognome"));
            e.setUtente(utente);
        }

        return e;
    }

    /**
     * Interfaccia funzionale adibita al binding dei parametri sulle query custom.
     */
    @FunctionalInterface
    private interface ParameterBinder {
        void bind(PreparedStatement statement) throws SQLException;
    }

    /**
     * Esegue una query strutturata riducendo la duplicazione del codice di gestione delle risorse JDBC.
     */
    private List<ElementoMultimediale> executeQueryList(String sql, ParameterBinder binder) throws SQLException {
        List<ElementoMultimediale> lista = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(mapRow(resultSet));
                }
            }
        }
        return lista;
    }
}
