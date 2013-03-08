package dao;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
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
        Trip trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        tripDao.add(trip);
        pt.setTrip(trip);
        participatedTripDao.update(pt);
        assertTrue(participatedTripDao.findAllByTripId(trip.getId())!=null);
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
}
