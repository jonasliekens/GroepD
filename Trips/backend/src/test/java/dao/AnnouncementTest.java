package dao;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Announcement;
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
 * Date: 7/03/13
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class AnnouncementTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    @Qualifier("tripDaoImpl")
    private TripDao tripDao;

    private Trip trip;
    private Announcement announcement;

    @Test
    public void testAddAnouncement() {
        createNewAnnouncementWithTrip();
        assertTrue(trip.getAnnouncements().size() > 0);
    }

    @Test
    public void testEditAnnouncement() {
        createNewAnnouncementWithTrip();
        ((Announcement)trip.getAnnouncements().toArray()[0]).setMessage("Edit Announcement");
        tripDao.update(trip);
        trip = tripDao.findById(trip.getId());
        assertTrue(((Announcement)trip.getAnnouncements().toArray()[0]).getMessage().equals("Edit Announcement"));
    }

    @After
    public void removeAllTrips(){
        for (Trip trip: tripDao.findAll()){
            tripDao.remove(trip);
        }
    }

    private void createNewAnnouncementWithTrip() {
        announcement = new Announcement();
        trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        tripDao.add(trip);
        announcement.setMessage("TestAnnouncement");
        trip.addAnnouncement(announcement);
        tripDao.update(trip);
    }
}
