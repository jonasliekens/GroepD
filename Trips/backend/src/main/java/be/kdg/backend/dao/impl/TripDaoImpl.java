package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Trip;
import org.junit.Before;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 9/02/13
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TripDaoImpl implements TripDao {
    protected EntityManager entityManager;
    protected EntityTransaction etx;

    @Before
    public void init(){



    }

    @Override
    @Transactional
    public void add(Trip entity) {
        /*entityManager.getTransaction().begin();
        entityManager.persist(entity);
        enityManager.getTransaction().commit();*/
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
        etx = entityManager.getTransaction();
        etx.begin();
        entityManager.persist(entity);
        etx.commit();
    }

    @Override
    @Transactional
    public void remove(Trip entity) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void update(Trip entity) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public Trip find(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
        return entityManager.find(Trip.class, id);
    }

    @Override
    @Transactional
    public List<Trip> list() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
        Query query = entityManager.createQuery("select t from Trip t");
        return query.getResultList();
    }
}
