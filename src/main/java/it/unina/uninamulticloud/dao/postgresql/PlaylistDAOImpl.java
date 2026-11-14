package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.PlaylistDAO;
import it.unina.uninamulticloud.entity.*;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * IL CODICE PER L'APERTURA DELLA CONNESSIONE E RESULTSET è
 * */
public class PlaylistDAOImpl implements PlaylistDAO {

    @Override
    public Playlist findById(int idPlaylist) {
        Playlist playlist = null;
        String query = "SELECT *,  EXTRACT(EPOCH FROM durataTotale) as durataTotaleSecondi" +
                " FROM Playlist WHERE idPlaylist = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idPlaylist);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    playlist = mapResultSetToPlaylist(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca della playlist", e);
        }
        return playlist;
    }

    @Override
    public List<Playlist> findByProprietario(String matricola) {
        List<Playlist> lista = new ArrayList<>();
        String query = "SELECT *, EXTRACT(EPOCH FROM durataTotale) as durataTotaleSecondi" +
                " FROM Playlist WHERE matricola = ? ORDER BY dataPubblicazione DESC";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, matricola);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToPlaylist(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca delle playlist", e);
        }
        return lista;
    }

    @Override
    public void save(Playlist playlist, String matricolaProprietario) {
        String query = "INSERT INTO Playlist (titolo, dataPubblicazione, descrizione, tipo, matricola) " +
                "VALUES (?, ?, ?, ?::TIPO_PLAYLIST, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        //creo lo statement con l'opzione RETURN_GENERATED_KEYS per ottenere l'ID generato.
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, playlist.getTitolo());
            pstmt.setTimestamp(2, Timestamp.valueOf(playlist.getDataPubblicazione()));
            pstmt.setString(3, playlist.getDescrizione());

            if (playlist instanceof PlaylistPubblica) {
                pstmt.setString(4, "pubblica");
            } else if (playlist instanceof PlaylistPrivata) {
                pstmt.setString(4, "privata");
            } else if (playlist instanceof PlaylistCondivisa) {
                pstmt.setString(4, "condivisa");
            } else {
                throw new IllegalArgumentException("Tipo di playlist sconosciuto");
            }

            pstmt.setString(5, matricolaProprietario);
            //esegue l'inserimento e restituisce il numero di righe interessate (1 in questo caso)
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Recupera l'ID generato e lo assegna all'oggetto Playlist.
                        //l'id viene recuperato dalla tabella GeneratedKeys alla colonna 1 che corrisponde all'ID generato.
                        playlist.setIdPlaylist(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
           throw new RuntimeException("Errore nella creazione della playlist", e);
        }
    }

    /**
     * Restituisce tutte le playlist di un determinato tipo
     * @param tipo
     * @return Lista di playlist di tipo specificato
     */
    public List<Playlist> findByTipo(String tipo) {
        List<Playlist> lista = new ArrayList<>();
        String query = "SELECT *, EXTRACT(EPOCH FROM durataTotale) as durataTotaleSecondi" +
                " FROM Playlist WHERE tipo = ?::TIPO_PLAYLIST ORDER BY dataPubblicazione DESC";
        //Richiesta di connesione
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    //aggiungo gli oggetti "mappati" alla lista
                    lista.add(mapResultSetToPlaylist(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca delle playlist", e);
        }
        return lista;
    }

    public void updateTitolo(int idPlaylist, String nuovoTitolo) {
        String query = "UPDATE Playlist SET titolo = ? WHERE idPlaylist = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nuovoTitolo);
            pstmt.setInt(2, idPlaylist);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella modifica del titolo della playlist", e);
        }
    }

    @Override
    public void delete(int idPlaylist) {
        String query = "DELETE FROM Playlist WHERE idPlaylist = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Errore nella cancellazione della playlist", e);
        }
    }

    //non usa le lambda
    private Playlist mapResultSetToPlaylist(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");

        long id = rs.getLong("idPlaylist");
        String titolo = rs.getString("titolo");
        LocalDateTime dataPub = rs.getTimestamp("dataPubblicazione") != null ?
                rs.getTimestamp("dataPubblicazione").toLocalDateTime() : null;
        String descrizione = rs.getString("descrizione");
        Duration durataTotale = Duration.ofSeconds(rs.getLong("durataTotaleSecondi"));

        Playlist p = null;

        if ("pubblica".equalsIgnoreCase(tipo)) {
            p = new PlaylistPubblica(id, titolo, dataPub, descrizione, durataTotale);
        } else if ("privata".equalsIgnoreCase(tipo)) {
            p = new PlaylistPrivata(id, titolo, dataPub, descrizione, durataTotale);
        } else if ("condivisa".equalsIgnoreCase(tipo)) {
            p = new PlaylistCondivisa(id, titolo, dataPub, descrizione, durataTotale);
        }
        else {
            throw new SQLException("Tipo di playlist non valido: " + tipo);
        }
        return p;
    }
}