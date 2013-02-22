package be.kdg.services.interfaces;

import be.kdg.entities.User;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:42
 * Copyright @ Soulware.be
 */

public interface UserService {
    public void addUser(User user);
    public User getUser(Integer id);
    public boolean deleteUser(Integer id);
    public boolean checkLogin(String email, String password);
    public void mergeUserWithFacebook(Integer id, String facebookid);
    public boolean checkLoginWithFacebook(String facebookId);
}
