package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Integer checkLoginWithFacebook(String facebookId) throws LoginInvalidException;
    @Transactional
    public User findUserByEMail(String eMail) throws DataNotFoundException;
    @Transactional
    public List<User> getAllUsers();
    @Transactional
    public List<User> getUninvitedUsers(Integer tripId, Integer userId);
    @Transactional
    public void createUserInvitations(List<Integer> userIdList, Integer tripId);

    @Override
    @Deprecated
    void add(User entity);
}
