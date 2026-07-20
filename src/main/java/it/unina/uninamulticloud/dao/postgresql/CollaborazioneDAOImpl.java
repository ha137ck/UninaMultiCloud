package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.CollaborazioneDAO;
import it.unina.uninamulticloud.entity.Collaborazione;
import it.unina.uninamulticloud.entity.PlaylistCondivisa;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CollaborazioneDAOImpl implements CollaborazioneDAO {

    @Override
    public void save(Collaborazione collaborazione) {
        String query = "INSERT INTO Collaborazione (matricola, idPlaylist, dataInvito) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Estraiamo gli ID dagli oggetti associati
            pstmt.setString(1, collaborazione.getCollaboratore().getMatricola());
            pstmt.setInt(2, collaborazione.getPlaylistCondivisa().getIdPlaylist());
            pstmt.setTimestamp(3, Timestamp.valueOf(collaborazione.getDataInvito()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            // Qui verranno intercettate le eccezioni lanciate dai TRIGGER del DB
            System.err.println("Errore inserimento collaborazione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String matricola, int idPlaylist) {
        String query = "DELETE FROM Collaborazione WHERE matricola = ? AND idPlaylist = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, matricola);
            pstmt.setInt(2, idPlaylist);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Collaborazione> findByPlaylist(int idPlaylist) {
        List<Collaborazione> collaborazioni = new ArrayList<>();
        String query = "SELECT * FROM Collaborazione WHERE idPlaylist = ? ORDER BY dataInvito ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    collaborazioni.add(mapResultSetToCollaborazione(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collaborazioni;
    }

    @Override
    public List<Collaborazione> findByUtente(String matricola) {
        List<Collaborazione> collaborazioni = new ArrayList<>();
        String query = "SELECT * FROM Collaborazione WHERE matricola = ? ORDER BY dataInvito DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, matricola);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    collaborazioni.add(mapResultSetToCollaborazione(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collaborazioni;
    }

    // ==========================================
    // METODI DI SUPPORTO PRIVATI
    // ==========================================

    private Collaborazione mapResultSetToCollaborazione(ResultSet rs) throws SQLException {
        LocalDateTime dataInvito = rs.getTimestamp("dataInvito").toLocalDateTime();

        // 1. Creiamo un oggetto Utente e valorizziamo solo la matricola (la sua PK)
        Utente collaboratore = new Utente();
        collaboratore.setMatricola(rs.getString("matricola"));

        // 2. Creiamo un oggetto PlaylistCondivisa "vuoto" e valorizziamo solo l'ID
        // Usa il costruttore appropriato se diverso (es. passando parametri nulli per titolo ecc.)
        PlaylistCondivisa playlist = new PlaylistCondivisa(
                rs.getInt("idPlaylist"),
                null, // titolo fittizio
                null, // dataPubblicazione fittizia
                null  // descrizione fittizia
        );

        // Questo set richiede che la tua PlaylistCondivisa abbia un setIdPlaylist
        // (Ereditato dalla superclasse astratta Playlist)
        // playlist.setIdPlaylist(rs.getInt("idPlaylist"));

        return new Collaborazione(dataInvito, collaboratore, playlist);
    }
}