package it.unina.uninamulticloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/** COME VORREI CHE FUZIONASSE QUESTA CLASSE:
 * Singleton che gestisce l'unica connessione JDBC dell'applicazione.
 * Le credenziali sono lette dal file db.properties.
 * La connessione viene aperta alla prima richiesta di connessione, per le richieste successive se la connessione non è stata chiusa allora viene restituita
 * la stessa istanza, altrimenti viene creata una nuova istanza.
 * La connessione viene mantenuta aperta fino a quando non viene chiamato closeConnection() e viene chiusa al termine dell'esecuzione dell'applicazione.
 */

public class DBConnection {

    private static final String CONFIG_PATH = "/it/unina/uninamulticloud/config/db.properties";

    //istanza privata e statica di DBConnection
    private static DBConnection instance;

    // credenziali lette una sola volta dal file di configurazione
    private final String url;
    private final String user;
    private final String password;
    //istanza privata della connessione a postgres
    private Connection connection = null;


    //construttore privato per evitare istanziazioni multiple
    private DBConnection() {
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getResourceAsStream(CONFIG_PATH)) {
            if (input == null) {
                throw new RuntimeException("File db.properties non trovato in " + CONFIG_PATH);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura di db.properties", e);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    //metodo statico per ottenere l'unica istanza di DBConnection
    public static DBConnection getInstance() {
        //pattern singleton
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    //metodo pubblico per ottenere la connessione, è solo qui che viene effettivamente creata la connessione
    public  Connection getConnection() {
        //pattern singleton
        try {
            // se la connessione non esiste oppure è stata chiusa, la (ri)apre
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel controllo dello stato della connessione", e);
        }
        return getInstance().connection;
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
