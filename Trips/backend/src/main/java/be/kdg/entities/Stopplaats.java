package be.kdg.entities;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
public class Stopplaats {
    private String plaatsnaam;

    public Stopplaats(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }

    public String getPlaatsnaam() {
        return plaatsnaam;
    }

    public void setPlaatsnaam(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }
}
