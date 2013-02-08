package be.kdg.services.dao;

import be.kdg.entities.User;
import be.kdg.services.GenericDao;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
public interface UserDao extends GenericDao<User,Integer> {
    public boolean removeUser(User user);
}
