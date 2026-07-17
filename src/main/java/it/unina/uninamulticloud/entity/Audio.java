package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class Audio extends ElementoMultimediale {

    private int bitRate;

    public Audio(){};

    public Audio(long idElementoMultimediale, String titolo, Duration durata,
                 String descrizione, LocalDateTime dataCreazione, String formato,
                 String filePath, Utente utente, Integer bitRate) {

        super(idElementoMultimediale, titolo, durata, descrizione,
                dataCreazione, formato, filePath, utente);

        this.bitRate = bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getBitRate(){return bitRate;}
}
