package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class Video extends ElementoMultimediale{
    private String risoluzione;

        public Video(Long idElementoMultimediale, String titolo, Duration durata,
                     String descrizione, LocalDateTime dataCreazione, String formato,
                     String filePath, Utente autore, String risoluzione) {

            super(idElementoMultimediale, titolo, durata, descrizione,
                    dataCreazione, formato, filePath, autore);
            this.risoluzione = risoluzione;
        }

    public String getRisoluzione() {
        return risoluzione;
    }

    public void setRisoluzione(String risoluzione) {
        this.risoluzione = risoluzione;
    }
}
