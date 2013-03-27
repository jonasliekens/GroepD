package services;

import be.kdg.backend.entities.Request;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.services.interfaces.RequestService;
import be.kdg.backend.services.interfaces.TripService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 15:39
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class RequestServiceTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    RequestService requestService;

    @Autowired(required = true)
    private TripService tripService;

    private Trip trip;
    private Request request;

    @Before
    public void addTripAndRequest(){
        trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        tripService.add(trip);
        assertTrue(tripService.get(trip.getId()) != null);
        request = new Request();
        requestService.add(request);
        assertTrue(requestService.get(request.getId()) != null);
    }

    @Test
    public void requestTest(){
        request.setTrip(trip);
        assertNotNull(requestService.get(request.getId()).getTrip());
        request.setRequestId("578691422155249");
        Set<String> users = new HashSet<String>();
        users.add("1272280436");
        request.setUserIds(users);
        requestService.update(request);
        assertNotNull(requestService.findRequestByFBRequestId("578691422155249"));
    }

    @After
    public void removeAll(){
        for(Request req: requestService.getRequests()){
            requestService.remove(req);
        }
        tripService.remove(trip);
    }
}
