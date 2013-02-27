package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 14:39
 * Copyright @ Soulware.be
 */
@Repository
public class UserDaoImpl implements UserDao {

    protected EntityManager entityManager;

    public UserDaoImpl() {
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(User user) {
        entityManager.getTransaction().begin();
        user = entityManager.find(User.class, user.getId());
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery("select u from User u");
        return query.getResultList();
    }

    @Override
    public User findByEMail(String mail) {
        Query query = entityManager.createQuery("select u from User u where email = :mail");
        query.setParameter("mail", mail);
        return (User) query.getSingleResult();
    }

    @Override
    public User findByFacebookId(String facebookId) {
        Query query = entityManager.createQuery("select u from User u where facebookID = " + facebookId);
        return (User) query.getSingleResult();
    }
}
