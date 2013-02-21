package be.kdg.dao.impl;

import be.kdg.dao.interfaces.UserDao;
import be.kdg.entities.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDao<User, Integer> implements UserDao{
    public User find(String email) {
        Criteria criteria = currentSession().createCriteria(daoType);
        return (User) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }
}
