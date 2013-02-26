package services;

import be.kdg.backend.entities.User;
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
    public void addUser(){
        user = new User("soulscammer@gmail.com", "test", "Jonas", "Liekens", Utilities.makeDate("04/08/1991"));
        userService.addUser(user);
        Assert.assertNotNull(userService.getUser(user.getId()));
    }

    @Test
    public void checkCorrectLogin(){
        Assert.assertTrue(userService.checkLogin("soulscammer@gmail.com", "test"));
    }

    @Test
    public void checkFalseLoginMail(){
        Assert.assertFalse(userService.checkLogin("soulscammer@gmail.com", "bla"));
    }

    @Test
    public void checkFalseLoginPass(){
        Assert.assertFalse(userService.checkLogin("fail@gmail.com", "test"));
    }

    @Test
    public void checkFalseLoginEverything(){
        Assert.assertFalse(userService.checkLogin("fail@gmail.com", "bla"));
    }

    @Test
    public void checkFacebookLoginAfterMerge(){
        userService.mergeUserWithFacebook(user.getId(), "100000420715358");
        Assert.assertTrue(userService.checkLoginWithFacebook("100000420715358"));
    }

    @After
    public void deleteUser(){
        userService.deleteUser(user.getId());
        Assert.assertNull(userService.getUser(user.getId()));
    }
}
