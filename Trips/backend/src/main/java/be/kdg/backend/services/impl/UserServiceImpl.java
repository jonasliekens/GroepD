package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:46
 * Copyright @ Soulware.be
 */
@Service("userService")
//@ContextConfiguration(locations = "/persistence-beans.xml")
public class UserServiceImpl implements UserService {
    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;

    @Transactional
    public void addUser(User user) {
        userDao.add(user);
    }

    @Transactional
    public User getUser(Integer id) {
        return userDao.findById(id);
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        User userToDelete = getUser(id);
        if (userToDelete == null) {
            return false;
        } else {
            userDao.remove(userToDelete);
            return true;
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
        if (userDao.findByFacebookId(facebookId) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean checkLogin(String email, String password) {
        User user = userDao.findByEMail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}