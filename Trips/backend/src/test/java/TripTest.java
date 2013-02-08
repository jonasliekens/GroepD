import be.kdg.entities.Stop;
import be.kdg.entities.Trip;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 14:09
 * Copyright @ Soulware.be
 */
public class TripTest {
    private final Trip trip = new Trip();

    @Test
    public void getStopplaatsen() {
        assertTrue(trip.getStopplaatsen().size() == 0);
    }

    @Test
    public void addStop(){
        trip.addStop(new Stop("Karel de Grote Hogeschool", 51.2177208, 4.4008991, 31));
        assertTrue(trip.getStopplaatsen().size() == 1);
    }
}
