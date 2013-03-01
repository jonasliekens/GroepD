package services;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

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
    public void testAddTripToUser(){
        User user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);

    }

    @After
    public void deleteAllUsers(){
        for(User user : userService.getAllUsers()){
            userService.remove(user);
        }
    }
}
