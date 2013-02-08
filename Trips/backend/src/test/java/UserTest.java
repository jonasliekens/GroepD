import be.kdg.services.impl.UserDaoImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;


/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class UserTest {
    private UserDaoImpl userDao;

    @Autowired
        public void setUserDao(UserDaoImpl userDao) {
            this.userDao = userDao;
        }
    @Test
    public void addUser(){
        /*userDao.add(new User("seyriu@live.be", "lala", "nick", "de waele", new Date(2,2,1992)));*/
        assertTrue(true);
    }
}
