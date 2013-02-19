package be.kdg.dao.impl;

import be.kdg.entities.User;
import be.kdg.dao.interfaces.UserDao;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDao<User, Integer> implements UserDao{

    @Override
    public boolean removeUser(User user) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
