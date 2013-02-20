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

}
