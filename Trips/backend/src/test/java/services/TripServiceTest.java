package services;

import be.kdg.backend.entities.Announcement;
import be.kdg.backend.entities.Equipment;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.TripService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * User: Bart Verhavert
 * Date: 21/02/13 14:36
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class TripServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired(required = true)
    TripService tripService;
    User admin;

    @Before
    public void init() {
        this.admin = new User("email", "password", "firstname", "lastname", new Date(123456789));
    }

    @After
    public void testRemoveTrips() {
        for (Trip trip : this.tripService.getTrips()) {
            this.tripService.remove(trip);
        }

        assertFalse(this.tripService.getTrips().size() > 0);
    }

    @Test
    public void addTrip() {
        Trip trip = newTrip();
        this.tripService.add(trip);

        assertTrue(this.tripService.get(trip.getId()) != null);
    }

    @Test
    public void deleteTrip() {
        Trip trip = newTrip();
        this.tripService.add(trip);

        this.tripService.remove(trip);

        assertTrue(this.tripService.get(trip.getId()) == null);
    }

    @Test
    public void getTrip() {
        Trip trip = newTrip();
        this.tripService.add(trip);

        assertTrue(this.tripService.get(trip.getId()) != null);
    }

    @Test
    public void getTrips() {
        this.tripService.add(newTrip());
        this.tripService.add(newTrip());

        assertEquals(2, this.tripService.getTrips().size());
    }

    @Test
    public void testAddEquipmentToTrip() {
        Trip trip = newTrip();
        tripService.add(trip);
        Equipment equipment = new Equipment();
        equipment.setDescription("Shovel");
        trip.addEquipment(equipment);
        tripService.update(trip);
        assertTrue(tripService.get(trip.getId()).getEquipmentSet().size() > 0);
    }

    @Test
    public void testRemoveAnnouncement() {
        Trip trip = newTrip();
        tripService.add(trip);
        Announcement announcement = new Announcement();
        announcement.setMessage("TestAnnouncement");
        announcement.setTrip(trip);
        trip.addAnnouncement(announcement);
        tripService.update(trip);
        tripService.removeAnnouncementFromTrip(((Announcement) tripService.get(trip.getId()).getAnnouncements().toArray()[0]).getId());
        assertTrue(tripService.getAnnouncementsByTripId(trip.getId()).isEmpty());
    }

    @Test
    public void testRemoveEquipment() {
        Trip trip = newTrip();
        tripService.add(trip);
        Equipment equipment = new Equipment();
        equipment.setDescription("Shovel");
        equipment.setTrip(trip);
        trip.addEquipment(equipment);
        tripService.update(trip);
        tripService.removeEquipmentFromTrip(((Equipment) tripService.get(trip.getId()).getEquipmentSet().toArray()[0]).getId());
        assertTrue(tripService.getEquipmentByTripId(trip.getId()).isEmpty());
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