import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten
 * Date: 7/02/13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class TripTest {
    private final LosseTrip trip = new LosseTrip();

    @Test
    public void getStopplaatsen() {
        List<Stopplaats> stopplaatsen;
        stopplaatsen = trip.getStopplaatsen();

        assertTrue(stopplaatsen.size() > 0);
    }
}
