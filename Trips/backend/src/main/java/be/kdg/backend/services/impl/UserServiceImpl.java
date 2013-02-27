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

    @Override
    @Deprecated
    public void add(User entity) {
        userDao.add(entity);
    }

    @Override
    public boolean addUser(User user) {
        try{
            userDao.findByEMail(user.getEmail());
            return false;
        }catch (NoResultException e){
            userDao.add(user);
            return true;
        }
    }

    @Override
    public User get(Integer id) {
        try {
            return userDao.findById(id);
        } catch (NoResultException e) {
            return null; //TODO: null throwen of leeg object of exception doorthrowen? => Coding rules.
        }
    }

    @Override
    public void remove(User entity) {
        userDao.remove(entity);
    }

    @Override
    public void update(User entity) {
        userDao.update(entity);
    }

    @Override
    public boolean mergeUserWithFacebook(Integer id, String facebookId) {
        try{
            User user = userDao.findById(id);
            user.setFacebookID(facebookId);
            userDao.update(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
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

    @Override
    public User checkLogin(String email, String password) throws LoginInvalidException{
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