package it.unina.uninamulticloud.control;

import it.unina.uninamulticloud.dao.ElementoMultimedialeDAO;
import it.unina.uninamulticloud.dao.postgresql.ElementoMultimedialeDAOImpl;
import it.unina.uninamulticloud.entity.Audio;
import it.unina.uninamulticloud.entity.ElementoMultimediale;
import it.unina.uninamulticloud.entity.Utente;
import it.unina.uninamulticloud.entity.Video;

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

        Utente utenteLoggato = null; //SessioneUtente.getIstanza().getUtenteLoggato();
        if (utenteLoggato == null) {
            throw new IllegalStateException("Utente non autenticato.");
        }

        try {
            // 2. Prendi direttamente i percorsi dei file già esistenti nel file system
            String percorsoMediaString = fileMedia.getAbsolutePath().replace("\\", "/");
            String percorsoCopertinaString = (fileCopertina != null) ?
                    fileCopertina.getAbsolutePath().replace("\\", "/") : null;

            // 3. Determina se è Audio o Video in base all'estensione
            boolean isAudio = determinaSeAudio(fileMedia.getName());
            ElementoMultimediale nuovoElemento = isAudio ? new Audio() : new Video();

            // 4. Popola l'oggetto Model
            nuovoElemento.setTitolo(titolo);
            nuovoElemento.setDescrizione(descrizione);
            nuovoElemento.setDurata(Duration.ofSeconds(180)); //TODO GESTIRE LA DURATA (per ora Valore didattico di default)
            nuovoElemento.setDataCreazione(LocalDateTime.now());
            nuovoElemento.setFilePath(percorsoMediaString);
            nuovoElemento.setImmagineCopertina(percorsoCopertinaString);
            nuovoElemento.setUtente(utenteLoggato);


            elementoMultimedialeDAO.insert(nuovoElemento);
        }
        catch (Exception e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    private boolean determinaSeAudio(String nomeFile) {
        String ext = nomeFile.toLowerCase();
        return ext.endsWith(".mp3") || ext.endsWith(".wav");
    }
}
