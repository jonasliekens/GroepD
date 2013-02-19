import be.kdg.entities.User;
import be.kdg.services.interfaces.UserService;
import be.kdg.utilities.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:50
 * Copyright @ Soulware.be
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class UserServiceTest extends AbstractJUnit4SpringContextTests{
    @Autowired
    private UserService userService;
    private User user;

    @Before
    public void init() {
        //this.userService = new UserServiceImpl();
        this.user = new User("test@test.be", "lala", "test", "test", Utilities.makeDate("03/02/1992"));
    }

    @Test
    public void addUser() {
        this.userService.addUser(this.user);
        Assert.assertTrue(this.userService.getUser(this.user.getId()) != null);
    }


}
