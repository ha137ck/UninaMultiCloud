package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.ComposizioneDAO;
import it.unina.uninamulticloud.entity.Composizione;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Playlist;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComposizioneDAOImpl implements ComposizioneDAO {

    @Override
    public void save(int idPlaylist, long idElementoMultimediale) {
        String queryMax = "SELECT COALESCE(MAX(posizione), 0) FROM Composizione WHERE idPlaylist = ?";
        String queryInsert = "INSERT INTO Composizione (posizione, idPlaylist, idElementoMultimediale) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmtMax = conn.prepareStatement(queryMax);
             PreparedStatement pstmtInsert = conn.prepareStatement(queryInsert)) {

            // 1. Troviamo l'attuale posizione massima per quella playlist
            pstmtMax.setInt(1, idPlaylist);
            int nuovaPosizione = 1;
            try (ResultSet rs = pstmtMax.executeQuery()) {
                if (rs.next()) {
                    nuovaPosizione = rs.getInt(1) + 1;
                }
            }

            // 2. Inseriamo il nuovo elemento in coda
            pstmtInsert.setInt(1, nuovaPosizione);
            pstmtInsert.setInt(2, idPlaylist);
            pstmtInsert.setLong(3, idElementoMultimediale);
            pstmtInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idPlaylist, int posizione) {
        String queryDelete = "DELETE FROM Composizione WHERE idPlaylist = ? AND posizione = ?";
        String queryCompatta = "UPDATE Composizione SET posizione = posizione - 1 WHERE idPlaylist = ? AND posizione > ?";

        try (Connection conn = DBConnection.getInstance().getConnection()) {
            // È buona prassi usare una transazione per operazioni concatenate
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtDelete = conn.prepareStatement(queryDelete);
                 PreparedStatement pstmtCompatta = conn.prepareStatement(queryCompatta)) {

                // 1. Eliminiamo la riga
                pstmtDelete.setInt(1, idPlaylist);
                pstmtDelete.setInt(2, posizione);
                pstmtDelete.executeUpdate();

                // 2. Scaliamo di -1 tutte le posizioni successive per chiudere il buco
                pstmtCompatta.setInt(1, idPlaylist);
                pstmtCompatta.setInt(2, posizione);
                pstmtCompatta.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void swapPosizioni(int idPlaylist, int pos1, int pos2) {
        // Usa una posizione temporanea (-1) per evitare violazioni della Primary Key (posizione, idPlaylist)
        String query1 = "UPDATE Composizione SET posizione = -1 WHERE idPlaylist = ? AND posizione = ?";
        String query2 = "UPDATE Composizione SET posizione = ? WHERE idPlaylist = ? AND posizione = ?";
        String query3 = "UPDATE Composizione SET posizione = ? WHERE idPlaylist = ? AND posizione = -1";

        try (Connection conn = DBConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Inizio transazione

            try (PreparedStatement pstmt1 = conn.prepareStatement(query1);
                 PreparedStatement pstmt2 = conn.prepareStatement(query2);
                 PreparedStatement pstmt3 = conn.prepareStatement(query3)) {

                // Sposta il primo elemento sul parcheggio (-1)
                pstmt1.setInt(1, idPlaylist);
                pstmt1.setInt(2, pos1);
                pstmt1.executeUpdate();

                // Sposta il secondo elemento sulla vecchia posizione del primo
                pstmt2.setInt(1, pos1);
                pstmt2.setInt(2, idPlaylist);
                pstmt2.setInt(3, pos2);
                pstmt2.executeUpdate();

                // Sposta il primo elemento (dal parcheggio) sulla vecchia posizione del secondo
                pstmt3.setInt(1, pos2);
                pstmt3.setInt(2, idPlaylist);
                pstmt3.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Composizione> findByPlaylist(int idPlaylist) {
        List<Composizione> elementi = new ArrayList<>();
        String query = "SELECT * FROM Composizione WHERE idPlaylist = ? ORDER BY posizione ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    elementi.add(mapResultSetToComposizione(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementi;
    }

    // ==========================================
    // METODI DI SUPPORTO PRIVATI
    // ==========================================

    private Composizione mapResultSetToComposizione(ResultSet rs) throws SQLException {
        int posizione = rs.getInt("posizione");

        // Mock object per la Playlist (solo ID)
        Playlist playlist = new Playlist(rs.getInt("idPlaylist"), null, null, null) {};

        // Mock object per l'ElementoMultimediale (solo ID)
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

        return new Composizione(posizione, elemento, playlist);
    }
}