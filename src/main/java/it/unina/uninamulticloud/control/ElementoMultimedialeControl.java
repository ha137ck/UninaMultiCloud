package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.dao.ElementoMultimedialeDAO;
import it.unina.uninamulticloud.dao.postgresql.ElementoMultimedialeDAOImpl;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.Video;
import it.unina.uninamulticloud.util.SessionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ElementoMultimedialeControl {


    /*
    // Definizione della directory fissa sul file system
    // Puoi personalizzare questo percorso a tuo piacimento (es. "C:/UninaMultiCloudStorage")
    public static final String BASE_STORAGE_DIR = System.getProperty("user.home") + File.separator + "UninaMultiCloudStorage";
    public static final String MEDIA_DIR = BASE_STORAGE_DIR + File.separator + "media";
    public static final String COVERS_DIR = BASE_STORAGE_DIR + File.separator + "covers";
    */

    //pattern singleton
    private static ElementoMultimedialeControl instance;
    private static Utente utenteLoggato;
    private ElementoMultimedialeDAO elementoMultimedialeDAO;

    private ElementoMultimedialeControl() {
       this.elementoMultimedialeDAO = new ElementoMultimedialeDAOImpl();
    }

    public static ElementoMultimedialeControl getInstance() {
        if (instance == null) {
            instance = new ElementoMultimedialeControl();
        }
        return instance;
    }





    /**
     * Gestisce il processo completo di caricamento di un nuovo elemento multimediale.
     * * @param titolo Titolo del brano/video (obbligatorio)
     * @param descrizione Descrizione opzionale
     * @param fileMedia File audio/video selezionato da FileSystem
     * @param fileCopertina File immagine copertina (opzionale)
     * @throws IOException Se si verifica un errore durante la copia dei file
     * @throws SQLException Se si verifica un errore durante il salvataggio nel DB
     */
    public void caricaElemento(String titolo, String descrizione, File fileMedia, File fileCopertina)
            throws IOException, SQLException {

        // 1. Validazione input
        if (fileMedia == null || !fileMedia.exists()) {
            throw new IllegalArgumentException("Selezionare un file multimediale valido.");
        }

        //Recupera l'utente loggato
        Utente utenteLoggato = AutenticazioneControl.getInstance().getUtenteLoggato(); //SessionManager.getUtenteLoggato();
        if (utenteLoggato == null) {
            throw new IllegalStateException("Utente non autenticato.");
        }

            // 2. Prendi direttamente i percorsi dei file già esistenti nel file system
            String percorsoMediaString = fileMedia.getAbsolutePath().replace("\\", "/");
            String percorsoCopertinaString = (fileCopertina != null) ? fileCopertina.getAbsolutePath().replace("\\", "/") : null;

            // 3. Determina se è Audio o Video in base all'estensione
            boolean isAudio = determinaSeAudio(fileMedia.getName());
            String formato = estraiEstensione(fileMedia.getName());
            ElementoMultimediale nuovoElemento = isAudio ? new Audio() : new Video();

            // 4. Popola l'oggetto Model
            nuovoElemento.setTitolo(titolo);
            nuovoElemento.setDescrizione(descrizione);
            nuovoElemento.setDurata(Duration.ofSeconds(180)); //TODO GESTIRE LA DURATA (per ora Valore didattico di default)
            nuovoElemento.setDataCreazione(LocalDateTime.now());
            nuovoElemento.setFilePath(percorsoMediaString);
            nuovoElemento.setImmagineCopertina(percorsoCopertinaString);
            nuovoElemento.setUtente(utenteLoggato);
            nuovoElemento.setFormato(formato);

        //TODO Valori didattici di default, in attesa di un calcolo reale dai metadati del file
            if(nuovoElemento instanceof Audio audio) {
                audio.setBitRate(320);
            }
            if(nuovoElemento instanceof Video video) {
                video.setRisoluzione("720pp");
            }

            //5. Salva nel db
            elementoMultimedialeDAO.insert(nuovoElemento);
    }

    private static final HashSet<String> ESTENSIONI_VIDEO = new HashSet<>(Set.of("mp4", "mkv", "mov"));
    private static final HashSet<String>  ESTENSIONI_AUDIO = new HashSet<>(Set.of("mp3", "wav"));

    private String estraiEstensione(String nomeFile) {
        if(nomeFile == null ||nomeFile.lastIndexOf(".") == -1)
            throw new IllegalArgumentException("Nomefile non valido: " + nomeFile);
       return nomeFile.substring(nomeFile.lastIndexOf(".") + 1).toLowerCase(); //per sicurezza in minuscolo
    }

    private boolean determinaSeAudio(String nomeFile) {
        String estensione = estraiEstensione(nomeFile);
       if(ESTENSIONI_AUDIO.contains(estensione))
           return true;
       else if(ESTENSIONI_VIDEO.contains(estensione))
           return false;
       else
           throw new IllegalArgumentException("Formato del file non valido: " + nomeFile);
    }
}
