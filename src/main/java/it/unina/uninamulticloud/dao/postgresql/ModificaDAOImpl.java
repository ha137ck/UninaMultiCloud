package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.ModificaDAO;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Modifica;
import it.unina.uninamulticloud.entity.PlaylistCondivisa;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ModificaDAOImpl implements ModificaDAO {

    @Override
    public void save(Modifica modifica) {
        String query = "INSERT INTO Modifica (dataModifica, azioneEseguita, matricola, idElementoMultimediale, idPlaylist) " +
                "VALUES (?, ?::TIPO_AZIONE, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(modifica.getData()));
            pstmt.setString(2, modifica.getAzioneEseguita()); // Deve essere "INSERIMENTO", "ELIMINAZIONE" o "SPOSTAMENTO"

            pstmt.setString(3, modifica.getAutoreModifica().getMatricola());
            pstmt.setLong(4, modifica.getElementoCoinvolto().getIdElementoMultimediale());
            pstmt.setInt(5, modifica.getPlaylistModificata().getIdPlaylist());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Modifica> findByPlaylist(int idPlaylist) {
        List<Modifica> storico = new ArrayList<>();
        // Ordiniamo in modo decrescente (dalla più recente alla meno recente)
        String query = "SELECT * FROM Modifica WHERE idPlaylist = ? ORDER BY dataModifica DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    storico.add(mapResultSetToModifica(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storico;
    }

    // ==========================================
    // METODI DI SUPPORTO PRIVATI
    // ==========================================

    private Modifica mapResultSetToModifica(ResultSet rs) throws SQLException {
        LocalDateTime dataModifica = rs.getTimestamp("dataModifica").toLocalDateTime();
        String azione = rs.getString("azioneEseguita");

        // Creazione fittizia dell'Utente (solo ID)
        Utente autore = new Utente();
        autore.setMatricola(rs.getString("matricola"));

        // Creazione fittizia dell'ElementoMultimediale (solo ID)
        // Usiamo una classe concreta anonima o supponiamo che ElementoMultimediale sia gestibile
        // In alternativa, se hai un DAO ElementoMultimediale, potresti richiamarlo,
        // ma per le performance in JDBC puro conviene fare un mock object
        ElementoMultimediale elemento = new ElementoMultimediale() {
            @Override
            public Long getIdElementoMultimediale() {
                try {
                    return rs.getLong("idElementoMultimediale");
                } catch (SQLException e) {
                    return null;
                }
            }
        };

        // Creazione fittizia della Playlist (solo ID)
        PlaylistCondivisa playlist = new PlaylistCondivisa(
                rs.getInt("idPlaylist"),
                null, null, null
        );

        return new Modifica(dataModifica, azione, autore, elemento, playlist);
    }
}