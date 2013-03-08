package services;

import be.kdg.backend.entities.BroadcastMessage;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.BroadcastService;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 8/03/13
 * Time: 12:06
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class BroadcastServiceTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private BroadcastService broadcastService;

    @Autowired(required = true)
    ParticipatedTripService participatedTripService;

    @Autowired(required = true)
    UserService userService;

    @Autowired(required = true)
    TripService tripService;

    private BroadcastMessage message;
    private Trip trip;
    private User user;
    private User user1;

    @Before
    public void addBroadcastMessage(){
        trip = newTrip();
        tripService.add(trip);
        user = new User("test@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        user1 = new User("test2@test.be", "lala", "test2", "test2", Utilities.makeDate("04/02/1992"));
        userService.addUser(user);
        userService.addUser(user1);
        ParticipatedTrip participatedTrip = new ParticipatedTrip();
        participatedTrip.setUser(user);
        participatedTrip.setTrip(trip);
        ParticipatedTrip participatedTrip1 = new ParticipatedTrip();
        participatedTrip1.setUser(user1);
        participatedTrip1.setTrip(trip);
        participatedTripService.add(participatedTrip);
        participatedTripService.add(participatedTrip1);
        trip.addParticipatedTrip(participatedTrip);
        trip.addParticipatedTrip(participatedTrip1);
        trip = tripService.get(trip.getId());
        Assert.assertEquals(2, trip.getParticipatedTrips().size());
        message = new BroadcastMessage("Hell yeah!", trip, new Date());
        broadcastService.add(message);
        BroadcastMessage message1 = new BroadcastMessage("Hell yeah to you too!", trip, new Date());
        broadcastService.add(message1);
    }

    @Test
    public void testFind(){
       BroadcastMessage message = broadcastService.get(this.message.getId());
       Assert.assertTrue(message.getMessage().equals("Hell yeah!"));
    }

    @Test
    public void testRecievers(){
        message = broadcastService.get(message.getId());
        Assert.assertEquals(2, message.getRecievers().size());
    }

    @Test(expected = NoResultException.class)
    public void addFindAndRemoveMessage(){
        BroadcastMessage message1 = new BroadcastMessage("Test", trip, new Date());
        broadcastService.add(message1);
        Assert.assertNotNull(broadcastService.get(message1.getId()));
        broadcastService.remove(message1);
        message1 = broadcastService.get(message1.getId());
    }

    @Test
    public void findMessagesByUserId(){
        Assert.assertEquals(2, broadcastService.getUserBroadcastMessages(user.getId()).size());
    }

    @Test
    public void confirmMessage(){
        broadcastService.confirmMessage(user.getId(), message.getId());
        Assert.assertEquals(1, message.getRecievers().size());
    }

    @Test
    public void getAll(){
        Assert.assertEquals(2, broadcastService.getAllBroadcastMessages().size());
    }

    @Test(expected = NoResultException.class)
    public void allMessagesConfirmed(){
        broadcastService.confirmMessage(user.getId(), message.getId());
        broadcastService.confirmMessage(user1.getId(), message.getId());
        broadcastService.get(message.getId());
    }

    @After
    public void removeAll(){
        for(BroadcastMessage broadcastMessage : broadcastService.getAllBroadcastMessages()){
            removeMessage(broadcastMessage);
        }
        Assert.assertEquals(0, broadcastService.getAllBroadcastMessages().size());

        for(Trip trip: tripService.getTrips()){
            tripService.remove(trip);
        }
        Assert.assertEquals(0, tripService.getTrips().size());

        for(User user: userService.getAllUsers()){
            userService.remove(user);
        }
        Assert.assertEquals(0, userService.getAllUsers().size());
    }

    private void removeMessage(BroadcastMessage message) {
        broadcastService.remove(message);
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
