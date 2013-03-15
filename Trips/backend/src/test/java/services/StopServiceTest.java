package services;

import be.kdg.backend.entities.Photo;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.StopService;
import be.kdg.backend.services.interfaces.TripService;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 26/02/13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class StopServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired(required = true)
    StopService stopService;

    @Autowired(required = true)
    TripService tripService;

    @Test
    public void testAddStop() {
        Trip trip = newTrip();
        tripService.add(trip);
        Stop stop = newStop(1, trip);
        stopService.add(stop, trip.getId());
        assertTrue(stopService.getStopsByTripId(trip.getId()).get(0).getName().equals(stop.getName()));
    }

    @Test
    public void testUpdateStop() {
        Trip trip = newTrip();
        tripService.add(trip);
        Stop stop = newStop(2, trip);
        stopService.add(stop, trip.getId());
        stop = stopService.getStopsByTripId(trip.getId()).get(0);
        stop.setAccuracy(3);
        stopService.update(stop, trip.getId());
        assertTrue(stopService.get(stop.getId()).getAccuracy() == 3);
    }

    @Test
    public void testDeleteStop() {
        Trip trip = newTrip();
        tripService.add(trip);
        Stop stop = newStop(3, trip);
        stopService.add(stop, trip.getId());
        stop = stopService.getStopsByTripId(trip.getId()).get(0);
        stopService.remove(stop);
        assertTrue(stopService.get(stop.getId()) == null);
    }

    @Test
    public void testAddStopBetween() {
        Trip trip = newTrip();
        tripService.add(trip);

        Stop stop1 = newStop(1, trip);
        Stop stop2 = newStop(2, trip);
        Stop stop3 = newStop(3, trip);
        Stop stop4 = newStop(4, trip);
        stopService.add(stop1, trip.getId());
        stopService.add(stop2, trip.getId());
        stopService.add(stop3, trip.getId());
        stopService.add(stop4, trip.getId());

        Stop duplicate = newStop(2, trip);
        stopService.add(duplicate, trip.getId());
        List<Stop> stops = stopService.getStopsByTripId(trip.getId());
        assertTrue(stops.get(1).getName().equals(duplicate.getName()));
    }

    @Test
    public void testStopComparator() {
        Trip trip = newTrip();
        tripService.add(trip);

        Stop stop1 = newStop(1, trip);
        Stop stop2 = newStop(2, trip);
        Stop stop3 = newStop(3, trip);
        Stop stop4 = newStop(4, trip);
        stopService.add(stop4, trip.getId());
        stopService.add(stop2, trip.getId());
        stopService.add(stop3, trip.getId());
        stopService.add(stop1, trip.getId());

        List<Stop> stops = stopService.getStopsByTripId(trip.getId());
        assertTrue(stops.get(0).getName().equals(stop1.getName()));
    }

    @Test
    public void testUpdateStopBetween() {
        Trip trip = newTrip();
        tripService.add(trip);

        Stop stop1 = newStop(1, trip);
        Stop stop2 = newStop(2, trip);
        Stop stop3 = newStop(3, trip);
        Stop stop4 = newStop(4, trip);
        stopService.add(stop1, trip.getId());
        stopService.add(stop2, trip.getId());
        stopService.add(stop3, trip.getId());
        stopService.add(stop4, trip.getId());

        Stop dubbel = stopService.getStopsByTripId(trip.getId()).get(3);
        dubbel.setOrderNumber(1);
        stopService.update(dubbel, trip.getId());

        List<Stop> stops = stopService.getStopsByTripId(trip.getId());
        assertTrue(stops.get(0).getName().equals(dubbel.getName()));
    }

    @Test
    public void testDeleteStopBetween() {
        Trip trip = newTrip();
        tripService.add(trip);

        Stop stop1 = newStop(1, trip);
        Stop stop2 = newStop(2, trip);
        Stop stop3 = newStop(3, trip);
        Stop stop4 = newStop(4, trip);
        stopService.add(stop1, trip.getId());
        stopService.add(stop2, trip.getId());
        stopService.add(stop3, trip.getId());
        stopService.add(stop4, trip.getId());
        stop3 = stopService.getStopsByTripId(trip.getId()).get(2);
        stopService.remove(stop3);

        List<Stop> stops = stopService.getStopsByTripId(trip.getId());
        assertTrue(stops.get(2).getName().equals(stop4.getName()));
    }

    @Test
    public void testAddPhotoToStop() {
        Trip trip = newTrip();
        tripService.add(trip);
        Stop stop = newStop(0,trip);
        Photo photo = new Photo();
        photo.setTargetId("1234");
        photo.setTargetName("test");
        photo.setStop(stop);
        stop.addPhoto(photo);
        stop.setTrip(trip);
        trip.addStop(stop);
        tripService.update(trip);
        assertTrue(stopService.getStopsByTripId(trip.getId()).get(0).getPhotos().size() > 0);
    }

    @After
    public void removeTripsWithStops() {
        for (Trip trip : tripService.getTrips()) {
            tripService.remove(trip);
        }
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

    private Stop newStop(Integer order, Trip trip) {
        Stop stop = new Stop();
        stop.setLatitude(12342.245);
        stop.setLongitude(15572.245);
        stop.setName("Stop" + order);
        stop.setOrderNumber(order);
        return stop;
    }
}
