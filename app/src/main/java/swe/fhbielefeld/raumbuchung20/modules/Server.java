package swe.fhbielefeld.raumbuchung20.modules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server {
    private ArrayList<Buchung> buchungsList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Raum> raumList = new ArrayList<>();
    private static Server instance;
    private Server(){

        raumList.add(new Raum("F013"));
        raumList.add(new Raum("A102"));
        raumList.add(new Raum("B017"));
        raumList.add(new Raum("D019"));
        raumList.add(new Raum("F112"));
        raumList.add(new Raum("B115"));

        userList.add(new User("admin","admin"));
        userList.add(new User("chantal","chantal"));
        userList.add(new User("fouad","fouad"));
        userList.add(new User("marko","marko"));
        userList.add(new User("alwin","alwin"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startZeit = LocalDateTime.parse("09.01.2020 12:00",formatter);
        LocalDateTime endZeit = LocalDateTime.parse("09.01.2020 14:00",formatter);
        buchungsList.add(new Buchung(findRaum("D019"),startZeit, endZeit, findUser("chantal")));
        startZeit = LocalDateTime.parse("10.01.2020 12:00",formatter);
        endZeit = LocalDateTime.parse("10.01.2020 14:00",formatter);
        buchungsList.add(new Buchung(findRaum("F013"),startZeit, endZeit, findUser("fouad")));
    }

    public static Server getInstance(){
        if (Server.instance == null) {
            Server.instance = new Server ();
        }
        return Server.instance;
    }

    public User findUser(String name){
        for (User u : userList){
            if(u.getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public ArrayList<Buchung> findBuchungenByRaum(String raum){
        ArrayList<Buchung> filter = new ArrayList<>();
        for (Buchung b : buchungsList){
            if(b.getRaum().getRaumnummer().equals(raum)){
                filter.add(b);
            }
        }
        return filter;
    }

    public ArrayList<Buchung> findBuchungenByDatum(LocalDateTime datum){
        ArrayList<Buchung> filter = new ArrayList<>();
        for (Buchung b : buchungsList){
            if(b.getStartzeit().getDayOfMonth() == datum.getDayOfMonth()){
                if(b.getStartzeit().getMonthValue() == datum.getMonthValue()) {
                    if(b.getStartzeit().getYear() == datum.getYear()) {
                        filter.add(b);
                    }
                }
            }
        }
        return filter;
    }

    public void addBuchung(Buchung b){
        if(b != null){
            buchungsList.add(b);
        }
    }

    public ArrayList<Buchung> getBuchungen(){
        return buchungsList;
    }

    public void deleteBuchung(int position) {
        buchungsList.remove(position);
    }

    public Raum findRaum(String raumnummer){
        for (Raum r : raumList){
            if(r.getRaumnummer().equals(raumnummer)){
                return r;
            }
        }
        return null;
    }

    public boolean hatBuchungUeberschneidung(Buchung check){
        ArrayList<Buchung> compare = findBuchungenByRaum(check.getRaum().getRaumnummer());
        if (compare.isEmpty()) return false;
        for(Buchung b:compare){
            if(check.getStartzeit().isBefore(b.getEndzeit()) && b.getStartzeit().isBefore(check.getEndzeit())){
                return true;
            }
        }
        return false;
    }
    public Raum findeAlternative(LocalDateTime start, LocalDateTime ende){
        for (Raum r: raumList){
            Buchung b=new Buchung(r, start, ende, Client.angemeldeterUser);
            if(!hatBuchungUeberschneidung(b)){
                return r;
            }
        }
        return null;
    }
}
