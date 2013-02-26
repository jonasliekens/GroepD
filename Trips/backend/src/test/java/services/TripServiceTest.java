package services;

import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.TripService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Date;

import static org.junit.Assert.assertFalse;

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
            this.tripService.deleteTrip(trip);
        }

        assertFalse(this.tripService.getTrips().size() > 0);
    }

    @Test
    public void addTrip() {
        Trip trip = newTrip();
        this.tripService.addTrip(trip);

        Assert.assertTrue(this.tripService.getTrip(trip.getId()) != null);
    }

    @Test
    public void deleteTrip() {
        Trip trip = newTrip();
        this.tripService.addTrip(trip);

        this.tripService.deleteTrip(trip);

        Assert.assertTrue(this.tripService.getTrip(trip.getId()) == null);
    }

    @Test
    public void getTrip() {
        Trip trip = newTrip();
        this.tripService.addTrip(trip);

        Assert.assertTrue(this.tripService.getTrip(trip.getId()) != null);
    }

    @Test
    public void getTrips() {
        this.tripService.addTrip(newTrip());
        this.tripService.addTrip(newTrip());

        Assert.assertEquals(2, this.tripService.getTrips().size());
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setName("A name");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        return trip;
    }
}