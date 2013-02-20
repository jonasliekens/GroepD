import be.kdg.entities.Stop;
import be.kdg.entities.Trip;
import be.kdg.dao.interfaces.TripDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 14:09
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class TripTest extends AbstractJUnit4SpringContextTests {

    @Autowired(required = true)
    private TripDao tripDao;

    private Trip trip;

    @Before
    public void createTrip(){
        trip = newTrip();
    }

    @Test
    public void testAddTrip() {
        tripDao.add(trip);
        assertTrue(tripDao.find(trip.getId()) != null);
    }

    @Test
    public void testUpdateTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        temp = tripDao.find(temp.getId());
        temp.setPublished(true);
        tripDao.update(temp);
        assertTrue(temp.isPublished());
    }

    @Test
    public void testRemoveTrip() {
        Trip temp = newTrip();
        tripDao.add(temp);
        tripDao.remove(temp);
        assertTrue(tripDao.find(temp.getId()) == null);
    }

    @Test
    public void testAddStop() {
        Trip temp = newTrip();
        tripDao.add(temp);
        Stop stop = new Stop("Test", 12354.21, 125884.65, 12);
        temp.addStop(stop);
        tripDao.update(temp);
        temp = tripDao.findTripWithStops(temp.getId());
        assertTrue(temp.getStops().size() > 0);
    }

    /*
    @Test
    public void testPublishTrip(){

    }

    */

    @After
    public void testRemoveTrips() {
        for (Trip trip : tripDao.list()) {
            tripDao.remove(trip);
        }

        assertFalse(tripDao.list().size() > 0);
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        return trip;
    }
}
