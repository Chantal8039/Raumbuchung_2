package swe.fhbielefeld.raumbuchung20.modules;

public class Raum {
    private String raumnummer;

    public Raum(String raumnummer){
        this.raumnummer = raumnummer;
    }

    public String getRaumnummer(){
        return raumnummer;
    }
}
