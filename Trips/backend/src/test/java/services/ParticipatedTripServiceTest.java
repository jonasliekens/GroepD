package services;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 4/03/13
 * Time: 4:08
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class ParticipatedTripServiceTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    ParticipatedTripService participatedTripService;
    @Autowired(required = true)
    UserService userService;

    @Autowired(required = true)
    TripService tripService;

    ParticipatedTrip pt;

    @Test
    public void testAddParticipatedTrip(){
        pt = newParticipatedTrip();
        participatedTripService.add(pt);
        assertTrue(participatedTripService.get(pt.getId()).equals(pt));
    }

    @Test
    public void testUpdateParticipatedTrip() {
        pt = newParticipatedTrip();
        pt.setStarted(true);
        pt.setFinished(false);
        participatedTripService.add(pt);
        assertTrue(participatedTripService.get(pt.getId()).getStarted() && !(participatedTripService.get(pt.getId()).getFinished()));

    }

    @Test
    public void testRemoveParticipatedTrip() {
        pt = newParticipatedTrip();
        participatedTripService.add(pt);
        Trip trip = newTrip();
        tripService.add(trip);
        pt.setTrip(trip);
        participatedTripService.update(pt);
        participatedTripService.remove(pt);
        assertTrue(participatedTripService.get(pt.getId()) == null && tripService.get(pt.getTrip().getId()) != null);
        tripService.remove(trip);
        assertTrue(tripService.get(pt.getTrip().getId()) == null);
    }

    @Test
    public void testRemoveTripWithParticipatedTrip() {
        pt = newParticipatedTrip();
        participatedTripService.add(pt);
        User user = new User("soulscammerdfsrgr@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        Trip trip = newTrip();
        tripService.add(trip);
        trip.addAdmin(user);
        tripService.update(trip);
        Trip trip2 = new Trip();
        trip2.setName("A name 2");
        trip2.setPrivateTrip(false);
        trip2.setPublished(false);
        trip2.setNrDays(10);
        trip2.setNrHours(12);
        trip2.setCommunicationByChat(true);
        trip2.setCommunicationByLocation(true);
        pt.setTrip(trip);
        pt.setUser(user);
        participatedTripService.update(pt);
        ParticipatedTrip pt2 = new ParticipatedTrip();
        tripService.add(trip2);
        trip2.addAdmin(user);
        tripService.update(trip2);
        pt2.setTrip(trip2);
        trip2.addAdmin(pt.getUser());
        pt2.setUser(user);

        participatedTripService.update(pt2);
        trip = tripService.get(trip.getId());
        trip2 = tripService.get(trip2.getId());
        tripService.remove(pt.getTrip());
        assertTrue(tripService.get(trip.getId()) == null && tripService.get(trip2.getId()) != null);
    }

    @Test
    public void testGetConfirmedParticipatedTripsByTripId(){
        Trip trip = newTrip();
        tripService.add(trip);

        User user = new User("soulscammerdfsrgr@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);

        ParticipatedTrip pt = newParticipatedTrip();
        pt.setTrip(trip);
        pt.setUser(user);
        pt.setConfirmed(true);
        participatedTripService.add(pt);

        assertTrue(participatedTripService.getConfirmedParticipatedTripsByTripId(trip.getId()).get(0).getId().equals(pt.getId()));
    }

    @Test
    public void testSearchByTripId() {
        pt = newParticipatedTrip();
        participatedTripService.add(pt);

        Trip trip = newTrip();
        tripService.add(trip);

        pt.setTrip(trip);
        participatedTripService.update(pt);

        assertTrue(participatedTripService.getParticipatedTripsByTripId(trip.getId()) != null && tripService.get(pt.getTrip().getId()) != null);
    }

    @Test
    public void getAllParticipatedTripsStartedWithLocationByTripIdTest() {
        //TODO: Multiple participated trips fails this test
        ParticipatedTrip participatedTrip1 = newParticipatedTrip();
//        ParticipatedTrip participatedTrip2 = newParticipatedTrip();
//        ParticipatedTrip participatedTrip3 = newParticipatedTrip();

        participatedTripService.add(participatedTrip1);
//        participatedTripService.add(participatedTrip2);
//        participatedTripService.add(participatedTrip3);

        Trip trip = newTrip();

        tripService.add(trip);

        participatedTrip1.setTrip(trip);
//        participatedTrip2.setTrip(trip);
//        participatedTrip3.setTrip(trip);

        participatedTrip1.setStarted(true);
//        participatedTrip2.setStarted(true);
//        participatedTrip3.setStarted(false);

        participatedTripService.update(participatedTrip1);
//        participatedTripService.update(participatedTrip2);
//        participatedTripService.update(participatedTrip3);


        assertTrue(participatedTripService.getAllParticipatedTripsStartedWithLocationByTripId(trip.getId()).size() == 1);
    }

    @After
    public void removeAllParticipatedTrips() {
        List<ParticipatedTrip> ptrips = participatedTripService.getAllParticipatedTrips();
        for (ParticipatedTrip pt : ptrips) {
            participatedTripService.remove(pt);
        }
        for(Trip trip : tripService.getTrips()){
            tripService.remove(trip);
        }
        for(User user : userService.getAllUsers()){
            userService.remove(user);
        }
    }

    public ParticipatedTrip newParticipatedTrip() {
        pt = new ParticipatedTrip();
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
