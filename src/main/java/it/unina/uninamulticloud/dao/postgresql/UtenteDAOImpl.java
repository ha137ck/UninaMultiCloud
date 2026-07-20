package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.entity.Universita;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.enums.Genere;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.util.Optional;

public class UtenteDAOImpl implements UtenteDAO {

    @Override
    public Utente findByMatricola(String matricola) {
        String sql = "SELECT * FROM Utente WHERE matricola = ?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, matricola);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUtente(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca dell'utente", e);
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return checkExists("SELECT 1 FROM Utente WHERE email = ?", email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return checkExists("SELECT 1 FROM Utente WHERE username = ?", username);
    }

    @Override
    public boolean existsByMatricola(String matricola) {
        return checkExists("SELECT 1 FROM Utente WHERE matricola = ?", matricola);
    }

    @Override
    public void save(Utente utente) {
        String query = "INSERT INTO Utente (matricola, nome, cognome, username, email, password, dataNascita, genere, dataIscrizione, idUniversita) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::TIPO_GENERE_UTENTE, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, utente.getMatricola());
            pstmt.setString(2, utente.getNome());
            pstmt.setString(3, utente.getCognome());
            pstmt.setString(4, utente.getUsername());
            pstmt.setString(5, utente.getEmail());
            pstmt.setString(6, utente.getPassword());

            // Conversione date Java -> SQL
            pstmt.setDate(7, Date.valueOf(utente.getDataNascita()));
            pstmt.setString(8, utente.getGenere().name());
            pstmt.setTimestamp(9, Timestamp.valueOf(utente.getDataIscrizione()));

            // Estrae l'ID dall'oggetto Università collegato
            pstmt.setLong(10, utente.getUniversita().getIdUniversita());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
        }
    }

    private Utente mapResultSetToUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setMatricola(rs.getString("matricola"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));

        // La password va letta, altrimenti il login (che fa un .equals sulla password) fallisce
        u.setPassword(rs.getString("password"));

        if (rs.getDate("dataNascita") != null) {
            u.setDataNascita(rs.getDate("dataNascita").toLocalDate());
        }

        // Mappatura Genere
        String genereStr = rs.getString("genere");
        if (genereStr != null) {
            u.setGenere(Genere.valueOf(genereStr.toUpperCase()));
        }

        if (rs.getTimestamp("dataIscrizione") != null) {
            u.setDataIscrizione(rs.getTimestamp("dataIscrizione").toLocalDateTime());
        }

        // Istanzio un'Università vuota e le assegno l'ID e nome e citta vuoti per evitare NullPointerException tanto non ci serve per il login
        Universita uni = new Universita(null, null);
        uni.setIdUniversita(rs.getLong("idUniversita"));
        u.setUniversita(uni);

        return u;
    }

    private boolean checkExists(String query, String param) {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Se rs.next() è true, vuol dire che ha trovato un match
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
