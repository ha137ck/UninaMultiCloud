package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.UniversitaDAO;
import it.unina.uninamulticloud.entity.Universita;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniversitaDAOImpl implements UniversitaDAO {

    @Override
    public Universita findById(long idUniversita) {
        Universita universita = null;
        String query = "SELECT * FROM Universita WHERE idUniversita = ?";
        //Richiesta di connessione
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, idUniversita);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    universita = mapResultSetToUniversita(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return universita;
    }

    @Override
    public List<Universita> findAll() {
        List<Universita> listaUniversita = new ArrayList<>();
        String query = "SELECT * FROM Universita ORDER BY nome ASC";
        //Richiesta di connesione
        Connection conn = DBConnection.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaUniversita.add(mapResultSetToUniversita(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaUniversita;
    }

    @Override
    public void save(Universita universita) {
        String query = "INSERT INTO Universita (nome, citta) VALUES (?, ?)";
        //Richiesta di connesione
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, universita.getNome());
            pstmt.setString(2, universita.getCitta());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        universita.setIdUniversita(generatedKeys.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Universita mapResultSetToUniversita(ResultSet rs) throws SQLException {
        Universita u = new Universita(
                rs.getString("nome"),
                rs.getString("citta"),
                rs.getLong("idUniversita")
        );
        return u;
    }
}
