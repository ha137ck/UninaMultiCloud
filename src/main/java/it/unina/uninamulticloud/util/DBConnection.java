package it.unina.uninamulticloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private static final String CONFIG_PATH = "/it/uninamulticloud/config/db.properties";

    private DBConnection() {
        try {
            Properties props = new Properties();
            try (InputStream input = DBConnection.class.getResourceAsStream(CONFIG_PATH)) {
                if (input == null) {
                    throw new RuntimeException("File db.properties non trovato in " + CONFIG_PATH);
                }
                props.load(input);
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Impossibile inizializzare la connessione al DB", e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                instance = new DBConnection();
                return instance.connection;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel controllo dello stato della connessione", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella chiusura della connessione", e);
        }
    }
}
