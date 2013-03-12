package services;

import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.ParticipatedTripService;
import be.kdg.backend.services.interfaces.TripService;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/02/13
 * Time: 13:53
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired(required = true)
    UserService userService;

    @Autowired(required = true)
    ParticipatedTripService participatedTripService;

    @Autowired(required = true)
    TripService tripService;

    @Test
    public void addUser() {
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        Assert.assertFalse(userService.addUser(user));
    }

    @Test
    public void addExisting() {
        User userFirst = new User("soulscammer@gmail.com", "test", "Blabla", "Test", Utilities.makeDate("04/03/1991"));
        userService.addUser(userFirst);
        User userDouble = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        boolean userExisted = userService.addUser(userDouble);
        Assert.assertTrue(userExisted);
    }

    @Test
    public void checkCorrectLogin() throws LoginInvalidException {
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        Assert.assertNotNull(userService.checkLogin("soulscammer@gmail.com", "test"));
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginMail() throws LoginInvalidException{
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        userService.checkLogin("soulscammer@gmail.com", "bla");
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginPass() throws LoginInvalidException{
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        userService.checkLogin("fail@gmail.com", "test");
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginEverything() throws LoginInvalidException{
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        userService.checkLogin("fail@gmail.com", "bla");
    }

    @Test
    public void checkFacebookLoginAfterMerge() throws DataNotFoundException, LoginInvalidException {
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        user = userService.findUserByEMail(user.getEmail());
        userService.mergeUserWithFacebook(user.getId(), "100000420715358");
        Assert.assertNotNull(userService.checkLoginWithFacebook("100000420715358"));
    }

    @Test(expected = DataNotFoundException.class)
    public void deleteUser() throws DataNotFoundException {
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        userService.remove(user);
        userService.findUserByEMail(user.getEmail());
    }

    @Test
     public void testGetUninvitedUsers(){
        Trip trip = newTrip();
        tripService.add(trip);

        User user1 = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        User user2 = new User("bartpraats@gmail.com", "test", "Bart", "Praats", Utilities.makeDate("04/08/1991"));
        User user3 = new User("maartenkonings@gmail.com", "test", "Maarten", "Konings", Utilities.makeDate("04/08/1991"));

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        trip.addAdmin(user1);
        ParticipatedTrip pt = new ParticipatedTrip();
        pt.setTrip(trip);
        pt.setUser(user2);
        participatedTripService.add(pt);

        Assert.assertTrue(userService.getUninvitedUsers(trip.getId(), user1.getId()).get(0).getId() == user3.getId());
    }

    @Test
    public void testCreateUserInvitations(){
        List<Integer> userIds = new ArrayList<Integer>();

        Trip trip = newTrip();
        tripService.add(trip);

        User user1 = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        User user2 = new User("bartpraats@gmail.com", "test", "Bart", "Praats", Utilities.makeDate("04/08/1991"));
        User user3 = new User("maartenkonings@gmail.com", "test", "Maarten", "Konings", Utilities.makeDate("04/08/1991"));

        userService.addUser(user1);
        userIds.add(user1.getId());
        userService.addUser(user2);
        userIds.add(user2.getId());
        userService.addUser(user3);
        userIds.add(user3.getId());

        userService.createUserInvitations(userIds, trip.getId());

        Assert.assertTrue(participatedTripService.getAllParticipatedTrips().size() == 3);
    }

    @After
    public void deleteAllUsers(){
        for(User user : userService.getAllUsers()){
            userService.remove(user);
        }
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
