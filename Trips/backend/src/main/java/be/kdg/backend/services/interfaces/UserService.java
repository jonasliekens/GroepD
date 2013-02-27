package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:42
 * Copyright @ Soulware.be
 */

public interface UserService extends GenericService<User, Integer>{
    @Transactional
    public boolean addUser(User user);
    @Transactional
    public User checkLogin(String email, String password) throws LoginInvalidException;
    @Transactional
    public boolean mergeUserWithFacebook(Integer id, String facebookId);
    @Transactional
    public boolean checkLoginWithFacebook(String facebookId);
}
