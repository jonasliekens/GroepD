package be.kdg.dao.interfaces;

import be.kdg.entities.User;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
public interface UserDao extends GenericDao<User,Integer> {
    public User find(String email);
}
