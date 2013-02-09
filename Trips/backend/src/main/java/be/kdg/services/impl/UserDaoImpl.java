package be.kdg.services.impl;

import be.kdg.entities.User;
import be.kdg.services.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
