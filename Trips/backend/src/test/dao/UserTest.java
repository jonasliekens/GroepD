package dao;

import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.User;
import be.kdg.backend.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@ContextConfiguration(locations = "classpath*:/META-INF/applicationContext.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {
    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @After
    public void testRemoveUsers() {
        for (User user : userDao.findAll()) {
            removeUser(user);
        }
        assertFalse(userDao.findAll().size() > 0);
    }

    @Test
    public void testAddUser() {
        User temp = newUser();
        assertTrue(userDao.findById(temp.getId()) != null);
        removeUser(temp);
    }

    @Test
    public void testUpdateUser() {
        User temp = newUser();
        temp.setPassword("haha");
        userDao.update(temp);
        assertTrue(userDao.findById(temp.getId()).getPassword().equals("haha"));
        removeUser(temp);
    }

    @Test
    public void testRemoveUser() {
        User temp = newUser();
        removeUser(temp);
        assertTrue(userDao.findById(temp.getId()) == null);
    }

    @Test
    public void findUserByMail() {
        User temp = newUser();
        assertNotNull(userDao.findByEMail(temp.getEmail()));
        removeUser(temp);
    }

    @Test
    public void mergeUserWithFacebook() {
        User temp = newUser();
        String facebookId = "100000420715358";
        temp.setFacebookID(facebookId);
        userDao.update(temp);
        assertNotNull(userDao.findByFacebookId(facebookId));
        removeUser(temp);
    }

    private void removeUser(User temp) {
        userDao.remove(temp);
    }

    private User newUser() {
        User user = new User("test@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        userDao.add(user);
        return user;
    }
}
