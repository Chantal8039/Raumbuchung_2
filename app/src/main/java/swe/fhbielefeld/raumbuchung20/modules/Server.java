package swe.fhbielefeld.raumbuchung20.modules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server {
    private ArrayList<Buchung> buchungsList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Raum> raumList = new ArrayList<>();
    public static Server instance;
    public Server(){

        raumList.add(new Raum("F013"));
        raumList.add(new Raum("A102"));
        raumList.add(new Raum("B017"));
        raumList.add(new Raum("D019"));
        raumList.add(new Raum("1"));
        raumList.add(new Raum("2"));

        userList.add(new User("admin","admin"));
        userList.add(new User("chantal","chantal"));
        userList.add(new User("fouad","fouad"));
        userList.add(new User("marko","marko"));
        userList.add(new User("alwin","alwin"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startZeit = LocalDateTime.parse("09.01.2019 12:00",formatter);
        LocalDateTime endZeit = LocalDateTime.parse("09.01.2019 14:00",formatter);
        buchungsList.add(new Buchung(findRaum("D019"),startZeit, endZeit, findUser("chantal")));
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
    public Buchung findBuchung(String raum){
        for (Buchung b : buchungsList){
            if(b.getRaum().equals(raum)){
                return b;
            }
        }
        return null;
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
}
