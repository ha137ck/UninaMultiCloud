package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UtenteDAOImpl implements UtenteDAO {
    @Override
    public Optional<Utente> findByMatricola(String matricola) {
        String sql = "SELECT * FROM Utente WHERE matricola = ?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, matricola);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca dell'utente", e);
        }
        return Optional.empty();
    }

    private Utente mapResultSet(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setMatricola(rs.getString("matricola"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setDataNascita(rs.getDate("dataNascita").toLocalDate());
        //u.setGenere(rs.getGenere("genere"));
        u.setDataIscrizione(rs.getTimestamp("dataIscrizione").toLocalDateTime());
        //u.setIdUniversita(rs.getLong("idUniversita"));
        return u;
    }
}
