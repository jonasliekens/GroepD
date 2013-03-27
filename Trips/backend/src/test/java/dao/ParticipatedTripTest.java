package dao;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 6/03/13
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ParticipatedTripTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    @Qualifier("participatedTripDaoImpl")
    ParticipatedTripDao participatedTripDao;
    @Autowired(required = true)
    @Qualifier("tripDaoImpl")
    TripDao tripDao;
    ParticipatedTrip pt;
    @Qualifier("userDaoImpl")
       @Autowired(required = true)
       private UserDao userDao;

    @Test
    public void testAddParticipatedTrip() {
        pt = newParticipatedTrip();
        assertTrue(participatedTripDao.findById(pt.getId()) != null);
    }

    @Test
    public void testUpdateParticipatedTrip() {
        pt = newParticipatedTrip();
        pt.setFinished(true);
        pt.setStarted(false);
        participatedTripDao.update(pt);
    }

    @Test
    public void testRemoveParticipatedTrip() {
        pt = newParticipatedTrip();
        participatedTripDao.remove(pt);
        assertTrue(participatedTripDao.findById(pt.getId()) == null);
    }

    @Test
    public void testFindByTripId() {
        pt = newParticipatedTrip();

        Trip trip = newTrip();
        tripDao.add(trip);

        pt.setTrip(trip);
        participatedTripDao.update(pt);

        assertTrue(participatedTripDao.findAllByTripId(trip.getId())!=null);
    }

    @Test
    public void findAllParticipatedTripsStartedWithLocationByTripIdTest() {
        //TODO: Multiple participated trips fails this test, detached entity => trip ... eager loading is a dirty solution
        ParticipatedTrip participatedTrip1 = new ParticipatedTrip();
//        ParticipatedTrip participatedTrip2 = new ParticipatedTrip();
//        ParticipatedTrip participatedTrip3 = new ParticipatedTrip();

        participatedTripDao.add(participatedTrip1);
//        participatedTripDao.add(participatedTrip2);
//        participatedTripDao.add(participatedTrip3);

        Trip trip = newTrip();
        User user = new User("Admin@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        user.setShareLocation(true);
                userDao.add(user);

        tripDao.add(trip);

        participatedTrip1.setTrip(trip);
//        participatedTrip2.setTrip(trip);
//        participatedTrip3.setTrip(trip);

        participatedTrip1.setUser(user);

        participatedTrip1.setStarted(true);
//        participatedTrip2.setStarted(true);
//        participatedTrip3.setStarted(false);

        participatedTripDao.update(participatedTrip1);
//        participatedTripDao.update(participatedTrip2);
//        participatedTripDao.update(participatedTrip3);

        assertTrue(participatedTripDao.findAllParticipatedTripsStartedWithLocationByTripId(trip.getId()).size() == 1);
    }

    @After
    public void removeAllParticipatedTrips() {
        for (ParticipatedTrip pt : participatedTripDao.findAll()) {
            participatedTripDao.remove(pt);
        }
    }

    private ParticipatedTrip newParticipatedTrip() {
        ParticipatedTrip pt = new ParticipatedTrip();
        participatedTripDao.add(pt);
        return pt;
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setName("A name");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        return trip;
    }
}
