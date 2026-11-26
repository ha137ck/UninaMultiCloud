package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.dao.PlaylistDAO;
import it.unina.uninamulticloud.dao.postgresql.PlaylistDAOImpl;
import it.unina.uninamulticloud.entity.*;

import java.sql.SQLException;

public class PlaylistControl {

    private final PlaylistDAO playlistDAO;
    private static PlaylistControl instance;

    private PlaylistControl(){
        this.playlistDAO = new PlaylistDAOImpl();
    }

    public static PlaylistControl getInstance(){
        if(instance == null){
            instance = new PlaylistControl();
        }
        return instance;
    }

    /**
     * Crea e salva una nuova playlist (inizialmente vuota) di proprietà dell'utente loggato.
     * @return la playlist salvata, con l'id assegnato dal database
     * @throws IllegalArgumentException se nome o visibilità non sono validi
     */
    public Playlist creaPlaylist(String titolo, String descrizione, String tipo) throws SQLException {

        if(titolo == null || titolo.isBlank()) throw new IllegalArgumentException("Il nome della playlist è obbligatorio.");
        if(tipo == null) throw new IllegalArgumentException("Seleziona la visibilità");

        Utente proprietario = AutenticazioneControl.getInstance().getUtenteLoggato();
        if(proprietario == null) throw new IllegalStateException("Utente non autenticato.");

        Playlist playlist;

        switch (tipo) {
            case "Pubblica":
                playlist = new PlaylistPubblica(titolo,descrizione);
                break;
                case "Privata":
                playlist = new PlaylistPrivata(titolo, descrizione);
                break;
                case "Condivisa":
                    playlist = new PlaylistCondivisa(titolo, descrizione);
                default:
                throw new IllegalArgumentException("Tipo di playlist non valido");
        }

        playlist.setProprietario(proprietario);
        playlistDAO.save(playlist, proprietario.getMatricola());
        return playlist;
    }
}
