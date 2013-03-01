package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.entities.ParticipatedTrip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 28/02/13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ParticipatedTripDaoImpl implements ParticipatedTripDao {

    protected EntityManager entityManager;

    public ParticipatedTripDaoImpl() {
        entityManager = emf.createEntityManager();
    }
    @Override
    public void add(ParticipatedTrip entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(ParticipatedTrip entity) {
        entityManager.clear();
        entityManager.getTransaction().begin();
        entity = entityManager.find(ParticipatedTrip.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(ParticipatedTrip entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public ParticipatedTrip findById(Integer integer) {
        return entityManager.find(ParticipatedTrip.class, integer);
    }

    @Override
    public List<ParticipatedTrip> findAll() {
        Query query = entityManager.createQuery("select pt from ParticipatedTrip pt");
        return query.getResultList();
    }
}
