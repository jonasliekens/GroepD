import be.kdg.entities.User;
import be.kdg.dao.interfaces.UserDao;
import be.kdg.utilities.Utilities;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

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
        assertTrue(userDao.find(temp.getId()) != null);
        userDao.remove(temp);
    }

    @Test
    public void testUpdateUser() {
        User temp = newUser();
        temp.setPassword("haha");
        userDao.update(temp);

        assertTrue(userDao.find(temp.getId()).getPassword().equals("haha"));
        userDao.remove(temp);
    }

    @Test
    public void testRemoveUser() {
        User temp = newUser();
        userDao.remove(temp);
        assertTrue(userDao.find(temp.getId()) == null);
    }

    @Test
    public void findUserByMail(){
        User temp = newUser();
        assertNotNull(userDao.find(temp.getEmail()));
        userDao.remove(temp);
    }

    private User newUser() {

        user = new User("test@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
        userDao.add(user);
        return user;
    }
}
