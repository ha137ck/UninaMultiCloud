package it.unina.uninamulticloud.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Modifica {
    private LocalDateTime data;
    private String azioneEseguita;
    private Utente autoreModifica;
    private ElementoMultimediale elementoCoinvolto;
    private PlaylistCondivisa playlistModificata;

    public Modifica(LocalDateTime data, String azioneEseguita, Utente autoreModifica, ElementoMultimediale elementoCoinvolto, PlaylistCondivisa playlistModificata) {
        this.data = data;
        this.azioneEseguita = azioneEseguita;
        this.autoreModifica = autoreModifica;
        this.elementoCoinvolto = elementoCoinvolto;
        this.playlistModificata = playlistModificata;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getAzioneEseguita() {
        return azioneEseguita;
    }

    public void setAzioneEseguita(String azioneEseguita) {
        this.azioneEseguita = azioneEseguita;
    }

    public Utente getAutoreModifica() {
        return autoreModifica;
    }

    public void setAutoreModifica(Utente autoreModifica) {
        this.autoreModifica = autoreModifica;
    }

    public ElementoMultimediale getElementoCoinvolto() {
        return elementoCoinvolto;
    }

    public void setElementoCoinvolto(ElementoMultimediale elementoCoinvolto) {
        this.elementoCoinvolto = elementoCoinvolto;
    }

    public PlaylistCondivisa getPlaylistModificata() {
        return playlistModificata;
    }

    public void setPlaylistModificata(PlaylistCondivisa playlistModificata) {
        this.playlistModificata = playlistModificata;
    }
}
