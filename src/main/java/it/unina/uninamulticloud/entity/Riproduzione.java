package it.unina.uninamulticloud.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Riproduzione {
    private LocalDateTime dataOrario;
    private Utente utente;
    private ElementoMultimediale elementoMultimediale;

    public Riproduzione(){};
    public Riproduzione(LocalDateTime dataOrario) {
        this.dataOrario = dataOrario;
    }

    public LocalDateTime getDataOrario() {
        return dataOrario;
    }

    public void setDataOrario(LocalDateTime data) {
        this.dataOrario = dataOrario;
    }

    public Utente getUtente(){return utente;}

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ElementoMultimediale getElementoMultimediale(){ return elementoMultimediale;}

    public void setElementoMultimediale(ElementoMultimediale elementoMultimediale) {
        this.elementoMultimediale = elementoMultimediale;
    }

}
