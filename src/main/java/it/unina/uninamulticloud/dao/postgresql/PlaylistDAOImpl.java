package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.PlaylistDAO;
import it.unina.uninamulticloud.entity.*;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOImpl implements PlaylistDAO {

    @Override
    public Playlist findById(int idPlaylist) {
        Playlist playlist = null;
        String query = "SELECT * FROM Playlist WHERE idPlaylist = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    playlist = mapResultSetToPlaylist(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    @Override
    public List<Playlist> findByProprietario(String matricola) {
        List<Playlist> lista = new ArrayList<>();
        String query = "SELECT * FROM Playlist WHERE matricola = ? ORDER BY dataPubblicazione DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, matricola);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToPlaylist(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Playlist playlist, String matricolaProprietario) {
        String query = "INSERT INTO Playlist (titolo, dataPubblicazione, descrizione, tipo, matricola) " +
                "VALUES (?, ?, ?, ?::TIPO_PLAYLIST, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idPlaylist) {
        String query = "DELETE FROM Playlist WHERE idPlaylist = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPlaylist);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Playlist mapResultSetToPlaylist(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");

        int id = rs.getInt("idPlaylist");
        String titolo = rs.getString("titolo");
        LocalDateTime dataPub = rs.getTimestamp("dataPubblicazione") != null ?
                rs.getTimestamp("dataPubblicazione").toLocalDateTime() : null;
        String descrizione = rs.getString("descrizione");

        Playlist p = null;

        if ("pubblica".equalsIgnoreCase(tipo)) {
            p = new PlaylistPubblica(id, titolo, dataPub, descrizione);
        } else if ("privata".equalsIgnoreCase(tipo)) {
            p = new PlaylistPrivata(id, titolo, dataPub, descrizione);
        } else if ("condivisa".equalsIgnoreCase(tipo)) {
            p = new PlaylistCondivisa(id, titolo, dataPub, descrizione);
        }

        return p;
    }
}
