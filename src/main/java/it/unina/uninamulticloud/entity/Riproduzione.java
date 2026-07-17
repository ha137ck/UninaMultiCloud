package it.unina.uninamulticloud.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Riproduzione {
    private LocalDate data;
    private LocalTime orario;

    public Riproduzione(LocalDate data, LocalTime orario) {
        this.data = data;
        this.orario = orario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }
}
