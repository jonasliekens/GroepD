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
    public User findByMail(String email) {
        Criteria criteria = currentSession().createCriteria(daoType);
        return (User) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }

    public void mergeUser(Integer id, String facebookId) {
        User user = (User) currentSession().get(daoType, id);
        user.setFacebookID(facebookId);
        this.update(user);
    }

    public User findByFacebook(String facebookId) {
        Criteria criteria = currentSession().createCriteria(daoType);
        return (User) criteria.add(Restrictions.eq("facebookID", facebookId)).uniqueResult();
    }
}
