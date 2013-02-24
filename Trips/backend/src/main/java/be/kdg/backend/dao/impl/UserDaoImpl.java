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
    protected EntityTransaction etx;
    protected EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void add(User entity) {
        initEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void remove(User user) {
        initEntityManager();
        entityManager.getTransaction().begin();
        user = entityManager.find(User.class, user.getId());
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void update(User user) {
        initEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public User find(Integer id) {
        initEntityManager();
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public List<User> list() {
        initEntityManager();
        Query query = entityManager.createQuery("select u from User u");
        return query.getResultList();
    }

    @Override
    @Transactional
    public User findByMail(String mail) {
        initEntityManager();
        Query query = entityManager.createQuery("select u from User u where email = :mail");
        query.setParameter("mail", mail);
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional
    public User findByFacebook(String facebookId) {
        initEntityManager();
        Query query = entityManager.createQuery("select u from User u where facebookID = " + facebookId);
        return (User) query.getSingleResult();
    }

    public void initEntityManager(){
        emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
    }
}
