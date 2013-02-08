package be.kdg.entities;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
public class Stop {
    private String plaatsnaam;
    private double latitude;
    private double longitude;
    private Integer accuracy;

    public Stop(String plaatsnaam, double latitude, double longitude, Integer accuracy) {
        this.plaatsnaam = plaatsnaam;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public String getPlaatsnaam() {
        return plaatsnaam;
    }

    public void setPlaatsnaam(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }
}
