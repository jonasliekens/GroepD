package be.kdg.entitiess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 16:09
 * Copyright @ Soulware.be
 */
public class LosseTrip {
    private List<Stopplaats> stopplaatsen;

    public LosseTrip() {
        stopplaatsen = new ArrayList<Stopplaats>();
        stopplaatsen.add(new Stopplaats("Karel de Grote Hogeschoo:"));
    }

    public List<Stopplaats> getStopplaatsen() {
        return stopplaatsen;
    }
}
