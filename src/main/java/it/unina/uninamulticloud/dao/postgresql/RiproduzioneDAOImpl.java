package it.unina.uninamulticloud.dao.postgresql;

import it.unina.uninamulticloud.dao.RiproduzioneDAO;
import it.unina.uninamulticloud.entity.*;
import it.unina.uninamulticloud.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RiproduzioneDAOImpl implements RiproduzioneDAO {

    @Override
    public void insert(Riproduzione riproduzione) throws SQLException {
        String sql = "INSERT INTO Riproduzione (dataOrario, matricola, idElementoMultimediale) VALUES (?, ?, ?)";

        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.valueOf(riproduzione.getDataOrario()));
            statement.setString(2, riproduzione.getUtente().getMatricola());
            statement.setLong(3, riproduzione.getElementoMultimediale().getIdElementoMultimediale());

            statement.executeUpdate();
        }
    }

    @Override
    public List<Riproduzione> findByUtente(String matricola) throws SQLException {
        // Eseguiamo una JOIN con la tabella ElementoMultimediale. In questo modo, quando
        // ricostruiamo l'oggetto nel backend, possiamo inserire anche il titolo del brano/video,
        // informazione fondamentale per mostrare una cronologia sensata nella GUI JavaFX.
        String sql = "SELECT r.dataOrario, r.matricola, r.idElementoMultimediale, e.titolo " +
                "FROM Riproduzione r " +
                "JOIN ElementoMultimediale e ON r.idElementoMultimediale = e.idElementoMultimediale " +
                "WHERE r.matricola = ? " +
                "ORDER BY r.dataOrario DESC";

        List<Riproduzione> riproduzioni = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, matricola);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Riproduzione r = new Riproduzione();
                    r.setDataOrario(resultSet.getTimestamp("dataOrario").toLocalDateTime());
                    // Istanziamo e popoliamo l'oggetto Utente associato (caricamento shallow/leggero)
                    Utente u = new Utente();
                    u.setMatricola(resultSet.getString("matricola"));
                    r.setUtente(u);

                    // Istanziamo e popoliamo l'oggetto ElementoMultimediale associato con i dati della JOIN
                    ElementoMultimediale e;
                    // RISOLUZIONE DEL POLIMORFISMO: Leggiamo il tipo dal DB
                    String tipoElemento = resultSet.getString("tipo");

                    if ("audio".equalsIgnoreCase(tipoElemento)) {
                        e = new Audio(); // Istanza concreta della sottoclasse Audio
                    } else if ("video".equalsIgnoreCase(tipoElemento)) {
                        e = new Video(); // Istanza concreta della sottoclasse Video
                    } else {
                        throw new SQLException("Tipo elemento multimediale non valido nel database: " + tipoElemento);
                    }
                    e.setIdElementoMultimediale(resultSet.getLong("idElementoMultimediale"));
                    e.setTitolo(resultSet.getString("titolo")); // Tratto dalla JOIN per la UI
                    r.setElementoMultimediale(e);

                    riproduzioni.add(r);
                }
            }
        }
        return riproduzioni;
    }

    @Override
    public int countByElemento(long idElementoMultimediale) throws SQLException {
        String sql = "SELECT COUNT(*) AS totale FROM Riproduzione WHERE idElementoMultimediale = ?";

        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, idElementoMultimediale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("totale");
                }
            }
        }
        return 0;
    }
}