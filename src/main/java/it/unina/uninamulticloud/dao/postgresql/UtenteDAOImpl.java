package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.UtenteDAO;
import it.unina.uninamulticloud.entity.Universita;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.enums.Genere;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;

public class UtenteDAOImpl implements UtenteDAO {

    //è l'unica ricerca implementata degli utenti è fatta per email
    @Override
    public Utente findByEmail(String email) {
        //recupero anche i dati dell'università con una join su Università
        //Alias per uni_nome per distinguerlo dal nome dell'utente
        String sql = "SELECT ut.*, uni.nome AS uni_nome, uni.citta AS uni_citta" +
                " FROM Utente ut JOIN Universita uni ON ut.idUniversita = uni.idUniversita WHERE ut.email = ?";
        //Richiesta di connessione
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //Uso il metodo per costruire l'oggetto utente
                    return mapResultSetToUtente(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella ricerca dell'utente", e); //è il modo corretto di gestire le eccezioni?
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
    public void saveUtente(Utente utente) {
        String query = "INSERT INTO Utente (matricola, nome, cognome, username, email, password, dataNascita, genere, dataIscrizione, idUniversita) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::TIPO_GENERE_UTENTE, ?, ?)";

        //Richiesta di connessione (fuori dal try with resources)
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

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
            pstmt.setLong(10, utente.getUniversita().getIdUniversita()); //se volessi togliere da java gli id e lasciarli solo nel db, come dovrei fare a collegare l'utente all'università?

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento dell'utente", e);
        }
    }

    /**
     * Lettura dei dati dal ResultSet e creazione dell'oggetto Utente
     * @param rs
     * @return L'oggetto Utente inizializzato con i dati del ResultSet
     * @throws SQLException
     */
    private Utente mapResultSetToUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setMatricola(rs.getString("matricola"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));

        if (rs.getDate("dataNascita") != null) {
            u.setDataNascita(rs.getDate("dataNascita").toLocalDate());
        }

        // Mappatura Genere
        String genereStr = rs.getString("genere");
        if (genereStr != null) {
            u.setGenere(Genere.valueOf(genereStr));
        }

        if (rs.getTimestamp("dataIscrizione") != null) {
            u.setDataIscrizione(rs.getTimestamp("dataIscrizione").toLocalDateTime());
        }
        //Creo l'oggetto Universita
        Universita uni = new Universita(rs.getString("uni_nome"), rs.getString("uni_citta"), rs.getLong("idUniversita"));
        //Collego l'università all'utente
        u.setUniversita(uni);

        return u;
    }

    /**
     * Fattorizza il codice per le query che controllano la presenza di un elemento nel database
     * @param query la query da eseguire
     * @param param il campo su cui effettuare la ricerca
     * @return true se esiste, false altrimenti
     */
    private boolean checkExists(String query, String param) {
        // richiesta di connessione
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, param);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Se rs.next() è true, vuol dire che la query ha trovato almeno un risultato
                return rs.next();
            }
        } catch (SQLException e) {
           throw new RuntimeException("Errore nella ricerca dell'utente", e);
        }
    }
}
