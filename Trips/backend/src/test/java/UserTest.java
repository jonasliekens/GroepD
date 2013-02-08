import be.kdg.entities.User;
import be.kdg.services.impl.UserDaoImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
public class UserTest {
    @Test
    public void addUser(){
        UserDaoImpl userdao=new UserDaoImpl();
        userdao.add( new User("lala@hotmail.com", "lala", "nick", "de waele", new Date(2,2,1992)));
        Assert.assertTrue(true);
    }
}
