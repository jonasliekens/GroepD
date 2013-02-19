import be.kdg.entities.Stop;
import be.kdg.entities.Trip;
import be.kdg.services.dao.TripDao;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 14:09
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class TripTest {
    @Autowired(required = true)
    private TripDao tripDao;
    // TO DO: INCLUDE OTHER DAOs
    // IMPROVE TESTS
    private Trip trip;

    @After
    public void testRemoveTrips() {
        for (Trip trip : tripDao.list()) {
            tripDao.remove(trip);
        }

        assertFalse(tripDao.list().size() > 0);
    }

    @Test
    public void testAddTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        assertTrue(tripDao.find(temp.getId()) != null);
    }

    @Test
    public void testUpdateTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        tripDao.update(temp);
                //EDIT
        assertTrue(true);
    }

    @Test
    public void testRemoveTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        tripDao.remove(temp);
        //EDIT
        assertTrue(true);
    }

    private Trip newTrip() {
        Trip trip= new Trip();
        tripDao.add(trip);
        return trip;
    }

    @Test
    public void testAddStop() {
        //   Add stopPlaats to stopDao & then add stop object to list
        trip.addStop(new Stop("Karel de Grote Hogeschool", 51.2177208, 4.4008991, 31));
        assertTrue(true);
    }
}
