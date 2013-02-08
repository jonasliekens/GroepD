package be.kdg.services.impl;

import be.kdg.entities.User;
import be.kdg.services.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
@Repository("userDao")
public class UserDaoImpl extends HibernateDao<User, Integer> implements UserDao{
    public UserDaoImpl() {
        super();
    }

    @Override
    public boolean removeUser(User user) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(User user) {

        super.add(user);    //To change body of overridden methods use File | Settings | File Templates.

    }
}
