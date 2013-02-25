package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.User;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:36
 * Copyright @ Soulware.be
 */
public interface UserDao extends GenericDao<User, Integer>{
    public User findByEMail(String mail);
    public User findByFacebookId(String facebookId);
}
