package be.kdg.services.impl;

import be.kdg.dao.interfaces.UserDao;
import be.kdg.entities.User;
import be.kdg.services.interfaces.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 19/02/13
 * Time: 14:46
 * Copyright @ Soulware.be
 */
@Service("userService")
@Component
@ContextConfiguration(locations = "/persistence-beans.xml")
public class UserServiceImpl implements UserService {
    @Autowired(required = true)
    private UserDao userDao;
    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Transactional
    public void addUser(User user) {
        userDao.add(user);
    }

    @Transactional
    public User getUser(Integer id) {
        return userDao.find(id);
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
        userDao.mergeUser(id, facebookid);
    }

    @Transactional
    public boolean checkLoginWithFacebook(String facebookId) {
        if (userDao.findByFacebook(facebookId) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean checkLogin(String email, String password) {
        User user = userDao.findByMail(email);
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