package dao;

import be.kdg.backend.dao.impl.ParticipatedTripDaoImpl;
import be.kdg.backend.dao.interfaces.BroadcastDao;
import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.BroadcastMessage;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 6/03/13
 * Time: 11:21
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class BroadcastTest extends AbstractJUnit4SpringContextTests {
    @Qualifier("broadCastDaoImpl")
    @Autowired(required = true)
    private BroadcastDao broadcastDao;

    @Qualifier("tripDaoImpl")
    @Autowired(required = true)
    private TripDao tripDao;

    @Qualifier("participatedTripDaoImpl")
    @Autowired(required = true)
    private ParticipatedTripDaoImpl participatedTripDao;

    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    private BroadcastMessage message;
    private Trip trip;
    private User user;

    @Before
    public void addMessage(){
        trip = newTrip();
        tripDao.add(trip);
        user = new User("test@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        User user1 = new User("test2@test.be", "lala", "test2", "test2", Utilities.makeDate("04/02/1992"));
        userDao.add(user);
        userDao.add(user1);
        ParticipatedTrip participatedTrip = new ParticipatedTrip();
        participatedTrip.setUser(user);
        participatedTrip.setTrip(trip);
        ParticipatedTrip participatedTrip1 = new ParticipatedTrip();
        participatedTrip1.setUser(user1);
        participatedTrip1.setTrip(trip);
        participatedTripDao.add(participatedTrip);
        participatedTripDao.add(participatedTrip1);
        trip.addParticipatedTrip(participatedTrip);
        trip.addParticipatedTrip(participatedTrip1);
        trip = tripDao.findById(trip.getId());
        Assert.assertEquals(2,trip.getParticipatedTrips().size());
        message = new BroadcastMessage("Hell yeah!", trip, new Date());
        broadcastDao.add(message);
        for (ParticipatedTrip trip : message.getTrip().getParticipatedTrips()) {
            message.addReciever(trip.getUser());
        }
        broadcastDao.update(message);
        BroadcastMessage message1 = new BroadcastMessage("Hell yeah to you too!", trip, new Date());
        broadcastDao.add(message1);
        for (ParticipatedTrip trip : message1.getTrip().getParticipatedTrips()) {
            message1.addReciever(trip.getUser());
        }
        broadcastDao.update(message1);
    }

    @Test
    public void getBroadcastMessage(){
        Assert.assertTrue(broadcastDao.findById(message.getId()).getMessage().equals("Hell yeah!"));
    }

    @Test
    public void testRecievers(){
        message = broadcastDao.findById(message.getId());
        Assert.assertEquals(2, message.getRecievers().size());
    }

    @Test(expected = NoResultException.class)
    public void addFindAndRemoveMessage(){
        BroadcastMessage message1 = new BroadcastMessage("Test", trip, new Date());
        broadcastDao.add(message1);
        Assert.assertNotNull(broadcastDao.findById(message1.getId()));
        broadcastDao.remove(message1);
        message1 = broadcastDao.findById(message1.getId());
    }

    @Test
    public void findMessagesByUserId(){
        Assert.assertEquals(2, broadcastDao.findMessagesByUserId(user.getId()).size());
    }

    @After
    public void removeAll(){
        for(BroadcastMessage broadcastMessage : broadcastDao.findAll()){
            removeMessage(broadcastMessage);
        }
        Assert.assertEquals(0, broadcastDao.findAll().size());

        for(Trip trip: tripDao.findAll()){
            tripDao.remove(trip);
        }
        Assert.assertEquals(0, tripDao.findAll().size());

        for(User user: userDao.findAll()){
            userDao.remove(user);
        }
        Assert.assertEquals(0, userDao.findAll().size());
    }

    private void removeMessage(BroadcastMessage message) {
        broadcastDao.remove(message);
    }

    private Trip newTrip() {
        Trip trip = new Trip();
        trip.setName("test");
        trip.setPrivateTrip(false);
        trip.setPublished(false);
        trip.setNrDays(10);
        trip.setNrHours(12);
        trip.setCommunicationByChat(true);
        trip.setCommunicationByLocation(true);
        return trip;
    }
}
