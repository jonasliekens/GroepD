package dao;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.TripType;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 14:09
 * Copyright @ Soulware.be
 */
public class TripTest extends AbstractJUnit4SpringContextTests {
    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

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
        temp = tripDao.find(temp.getId());
        assertTrue(temp.getStops().size() > 0);
    }


    @Test
    public void testSetTripType() {
        Trip temp = newTrip();
        temp.setType(TripType.LOOSE);
        tripDao.add(temp);
        temp = tripDao.find(temp.getId());
        assertTrue(temp.getType().equals(TripType.LOOSE));
    }

    @Test
    public void testAddAdminToTrip() {
        Trip temp = newTrip();
        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        temp.addAdmin(user);
        tripDao.add(temp);
        temp = tripDao.find(temp.getId());
        assertTrue(temp.getAdmins().size() > 0);
    }

    @Test
    public void testAddParticipantToTrip(){
        Trip temp = newTrip();
        User user = new User("Invited@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        temp.addInviteduser(user);
        tripDao.add(temp);
        temp = tripDao.find(temp.getId());
        assertTrue(temp.getInvitedUsers().size() > 0);
    }


    @After
    public void testRemoveTrips() {
        for (Trip trip : tripDao.list()) {
            tripDao.remove(trip);
        }

        assertFalse(tripDao.list().size() > 0);
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        return trip;
    }
}
