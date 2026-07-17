package it.unina.uninamulticloud.entity;

import java.time.LocalDate;

public class Modifica {
    private LocalDate data;
    private String azioneEseguita;

    public Modifica(LocalDate data, String azioneEseguita) {
        this.data = data;
        this.azioneEseguita = azioneEseguita;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getAzioneEseguita() {
        return azioneEseguita;
    }

    public void setAzioneEseguita(String azioneEseguita) {
        this.azioneEseguita = azioneEseguita;
    }
}
