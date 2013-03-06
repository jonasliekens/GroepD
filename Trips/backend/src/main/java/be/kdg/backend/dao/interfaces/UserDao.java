package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:36
 * Copyright @ Soulware.be
 */
public interface UserDao extends GenericDao<User, Integer>{
    @Transactional
    public User findByEMail(String mail);
    @Transactional
    public User findByFacebookId(String facebookId);
}
