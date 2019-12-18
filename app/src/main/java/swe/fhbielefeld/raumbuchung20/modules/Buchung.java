package swe.fhbielefeld.raumbuchung20.modules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Buchung {
    private int buchungsId;
    private Raum raum;
    private LocalDateTime startzeit;
    private LocalDateTime endzeit;
    private User nutzer;

    public Buchung(/*int buchungsId,*/ Raum raum, LocalDateTime startzeit, LocalDateTime endzeit, User nutzer) {
        //this.buchungsId = buchungsId;
        this.raum = raum;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.nutzer = nutzer;
    }

    /*public int getBuchungsId() {
        return buchungsId;
    }*/

    public Raum getRaum() {
        return raum;
    }

    public LocalDateTime getStartzeit() {
        return startzeit;
    }

    public LocalDateTime getEndzeit() {
        return endzeit;
    }

    public User getNutzer() {
        return nutzer;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String start = getStartzeit().format(formatter);
        String end = getEndzeit().format(formatter);
        return "Raum: " + getRaum().getRaumnummer() + "\nvon: " + start + " bis: " + end;
    }
}
