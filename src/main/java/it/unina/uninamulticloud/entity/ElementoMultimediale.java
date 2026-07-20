package it.unina.uninamulticloud.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class ElementoMultimediale {

    private Long idElementoMultimediale;
    private String titolo;
    private Duration durata;
    private String descrizione;
    private LocalDateTime dataCreazione;
    private String immagineCopertina; // o String path, a seconda di come la gestisci
    private String formato;
    private String filePath;
    private Utente autore; // FK matricola, nullable

    /* contruttore default*/
    public ElementoMultimediale() {}


    public ElementoMultimediale(Long idElementoMultimediale, String titolo, Duration durata,
                         String descrizione, LocalDateTime dataCreazione, String formato,
                         String filePath, Utente autore) {
        this.idElementoMultimediale = idElementoMultimediale;
        this.titolo = titolo;
        this.durata = durata;
        this.descrizione = descrizione;
        this.dataCreazione = dataCreazione;
        this.formato = formato;
        this.filePath = filePath;
        this.autore = autore;
    }

    public Long getIdElementoMultimediale() {
        return idElementoMultimediale;
    }

    public void setIdElementoMultimediale(long idElementoMultimediale) {
        this.idElementoMultimediale = idElementoMultimediale;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Duration getDurata() {
        return durata;
    }

    public void setDurata(Duration durata) {
        this.durata = durata;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getImmagineCopertina() {
        return immagineCopertina;
    }

    public void setImmagineCopertina(String immagineCopertina) {
        this.immagineCopertina = immagineCopertina;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Utente getAutore() {
        return autore;
    }

    public void setUtente(Utente autore) {
        this.autore = autore;
    }

    @Override
    public String toString() {
        return "ElementoMultimediale{" +
                "idElementoMultimediale=" + idElementoMultimediale +
                ", titolo='" + titolo + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
