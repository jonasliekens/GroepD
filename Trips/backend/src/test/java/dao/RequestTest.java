package dao;

import be.kdg.backend.dao.interfaces.RequestDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Request;
import be.kdg.backend.entities.Trip;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 13:22
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class RequestTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    @Qualifier("requestDaoImpl")
    RequestDao requestDao;

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

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
        tripDao.add(trip);
        assertTrue(tripDao.findById(trip.getId()) != null);
        request = new Request();
        requestDao.add(request);
        assertTrue(requestDao.findById(request.getId()) != null);
    }

    @Test
    public void requestTest(){
        request.setTrip(trip);
        assertNotNull(requestDao.findById(request.getId()).getTrip());
        request.setRequestId("578691422155249");
        Set<String> users = new HashSet<String>();
        users.add("1272280436");
        request.setUserIds(users);
        requestDao.update(request);
        assertNotNull(requestDao.findRequestByFBRequestId("578691422155249"));
    }

    @After
    public void removeAll(){
        for(Request req: requestDao.findAll()){
            requestDao.remove(req);
        }
        tripDao.remove(trip);
    }

}
