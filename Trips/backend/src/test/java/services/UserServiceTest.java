package services;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

    User user;

    @Before
    public void addUser() {
        user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        Assert.assertTrue(userService.addUser(user));
    }

    @Test
    public void addExisting() {
        Assert.assertFalse(userService.addUser(user));
    }

    @Test
    public void checkCorrectLogin() throws LoginInvalidException {
        Assert.assertNotNull(userService.checkLogin("soulscammer@gmail.com", "test"));
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginMail() throws LoginInvalidException{
        userService.checkLogin("soulscammer@gmail.com", "bla");
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginPass() throws LoginInvalidException{
        userService.checkLogin("fail@gmail.com", "test");
    }

    @Test(expected = LoginInvalidException.class)
    public void checkFalseLoginEverything() throws LoginInvalidException{
        userService.checkLogin("fail@gmail.com", "bla");
    }

    @Test
    public void checkFacebookLoginAfterMerge(){
        userService.mergeUserWithFacebook(user.getId(), "100000420715358");
        Assert.assertTrue(userService.checkLoginWithFacebook("100000420715358"));
    }

    @After
    public void deleteUser(){
        userService.remove(user);
        Assert.assertNull(userService.get(user.getId()));
    }
}
