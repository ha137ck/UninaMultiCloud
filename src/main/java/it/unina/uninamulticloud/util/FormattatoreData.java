package it.unina.uninamulticloud.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattatoreData {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private FormattatoreData() {}
    //public static final FormattatoreData INSTANCE = new FormattatoreData();

    /**
     * Formatta la durata in formato HH:MM:SS
     * @param
     * @return la stringa formattata
     */
    public static String formattaDurata(Duration d) {
        if (d == null) return "-";
        //operatore ternario
        return (d.toHours() > 0)
                ? String.format("%d:%02d:%02d", d.toHours(), d.toMinutesPart(), d.toSecondsPart())
                : String.format("%d:%02d", d.toMinutesPart(), d.toSecondsPart());
    }
    public static String formattaData(LocalDateTime data) {
        return (data == null) ? "-" : data.format(FORMATO_DATA);
    }

}
