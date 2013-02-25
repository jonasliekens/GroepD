package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.Trip;
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

    public TripDaoImpl() {
        entityManager = emf.createEntityManager();
    }

    @Override
    @Transactional
    public void add(Trip entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void remove(Trip entity) {
        entityManager.getTransaction().begin();
        entity = entityManager.find(Trip.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void update(Trip entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public Trip findById(Integer id) {
        return entityManager.find(Trip.class, id);
    }

    @Override
    @Transactional
    public List<Trip> findAll() {
        Query query = entityManager.createQuery("select t from Trip t");
        return query.getResultList();
    }
}
