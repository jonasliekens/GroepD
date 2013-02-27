package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:42
 * Copyright @ Soulware.be
 */

public interface UserService {
    public boolean addUser(User user);
    public User getUser(Integer id);
    public boolean deleteUser(Integer id);
    public User checkLogin(String email, String password) throws LoginInvalidException;
    public void mergeUserWithFacebook(Integer id, String facebookid);
    public boolean checkLoginWithFacebook(String facebookId);
}
