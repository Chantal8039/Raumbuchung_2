package swe.fhbielefeld.raumbuchung20.modules;

import java.time.LocalDateTime;

public class Buchung {
    private int buchungsId;
    private Raum raumnummer;
    private LocalDateTime startzeit;
    private LocalDateTime endzeit;
    private User nutzer;

    public Buchung(int buchungsId, Raum raumnummer, LocalDateTime startzeit, LocalDateTime endzeit, User nutzer) {
        this.buchungsId = buchungsId;
        this.raumnummer = raumnummer;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.nutzer = nutzer;
    }

    public int getBuchungsId() {
        return buchungsId;
    }

    public Raum getRaumnummer() {
        return raumnummer;
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
        return "Raum: " + getRaumnummer() + " ";
    }
}
