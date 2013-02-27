package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:46
 * Copyright @ Soulware.be
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @Transactional
    public boolean addUser(User user) {
        try{
            userDao.findByEMail(user.getEmail());
            return false;
        }catch(NoResultException e){
            userDao.add(user);
            return true;
        }
    }

    @Transactional
    public User getUser(Integer id) {
        try {
            return userDao.findById(id);
        } catch (NoResultException e) {
            return null; //TODO: null throwen of leeg object? => Coding rules.
        }
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        try {
            User userToDelete = getUser(id);
            if (userToDelete == null) {
                return false;
            } else {
                userDao.remove(userToDelete);
                return true;
            }
        } catch (NoResultException e) {
            return false;
        }
    }

    @Transactional
    public void mergeUserWithFacebook(Integer id, String facebookid) {
        User user = userDao.findById(id);
        user.setFacebookID(facebookid);
        userDao.update(user);
    }

    @Transactional
    public boolean checkLoginWithFacebook(String facebookId) {
        try {
            if (userDao.findByFacebookId(facebookId) != null) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            return false;
        }
    }

    @Transactional
    public User checkLogin(String email, String password) throws LoginInvalidException {
        try {
            User user = userDao.findByEMail(email);
            if (user.getPassword().equals(password)) {
               return userDao.findByEMail(email);
            } else {
                throw new LoginInvalidException();
            }
        } catch (NoResultException e) {
            throw new LoginInvalidException();
        }
    }
}