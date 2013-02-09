import be.kdg.entities.User;
import be.kdg.services.dao.UserDao;
import be.kdg.services.impl.UserDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Date;

import static org.junit.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {
    @Autowired(required = true)
    private UserDao userDao;

    private User user;

    @After
    public void testRemoveUsers() {
        for (User user : userDao.list()) {
            userDao.remove(user);
        }

        assertFalse(userDao.list().size() > 0);
    }

    @Test
    public void testAddUser() {
        User temp = newUser();
        userDao.add(temp);
        assertTrue(userDao.find(temp.getId()) != null);
    }

    @Test
    public void testUpdateUser() {
        User temp = newUser();
        userDao.add(temp);
        temp.setPassword("haha");
        userDao.update(temp);

        assertTrue(userDao.find(temp.getId()).getPassword().equals("haha"));
    }

    @Test
    public void testRemoveUser() {
        User temp = newUser();
        userDao.add(temp);
        userDao.remove(temp);
        assertTrue(userDao.find(temp.getId()) == null);
    }

    private User newUser() {
        user = new User("test@test.be", "lala", "test", "test", new Date(2, 2, 1992));
        userDao.add(user);
        return user;
    }
}
