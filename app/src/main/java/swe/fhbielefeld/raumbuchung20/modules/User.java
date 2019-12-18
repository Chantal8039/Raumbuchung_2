package swe.fhbielefeld.raumbuchung20.modules;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String passwort;
    private static int userCounter=0;
    public User (String name, String passwort){
        this.name = name;
        this.passwort = passwort;
    }

    public String getName(){
        return name;
    }
    public String getPasswort(){
        return passwort;
    }


}
