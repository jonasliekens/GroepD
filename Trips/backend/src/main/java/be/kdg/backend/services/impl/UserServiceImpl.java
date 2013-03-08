package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.User;
import be.kdg.backend.exceptions.DataNotFoundException;
import be.kdg.backend.exceptions.LoginInvalidException;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

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

    @Qualifier("participatedTripDaoImpl")
    @Autowired(required = true)
    private ParticipatedTripDao participatedTripDao;

    @Override
    public void add(User entity) {
        userDao.add(entity);
    }

    @Override
    public boolean addUser(User user) {
        try{
            userDao.findByEMail(user.getEmail());
            return true;
        }catch (NoResultException e){
            userDao.add(user);
            return false;
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
    public Integer checkLoginWithFacebook(String facebookId) throws LoginInvalidException{
        try {
            User user = userDao.findByFacebookId(facebookId);
            return user.getId();
        } catch (NoResultException e) {
            throw new LoginInvalidException();
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

    @Override
    public User findUserByEMail(String eMail) throws DataNotFoundException {
        try{
            return userDao.findByEMail(eMail);
        }catch (NoResultException e){
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getUninvitedUsers(Integer tripId, Integer userId) {
        List<User> users = new ArrayList<User>();
        for(User user : userDao.findAll()){
            for(ParticipatedTrip invitation : participatedTripDao.findAllByTripId(tripId)){
                if(user.getId() != invitation.getUser().getId() && user.getId() != userId){
                    users.add(user);
                }
            }
        }
        return users;
    }
}