package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.ParticipatedTripDao;
import be.kdg.backend.entities.ParticipatedTrip;
import be.kdg.backend.entities.Trip;
import be.kdg.backend.entities.User;
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
        if (entity.getTrip() != null) {
        entity.setTrip(entityManager.find(Trip.class, entity.getTrip().getId()));
        }
        if (entity.getUser() != null) {
            entity.setUser(entityManager.find(User.class, entity.getUser().getId()));
        }

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
        if (entity.getTrip() != null) {
            entity.setTrip(entityManager.find(Trip.class, entity.getTrip().getId()));
        }
        if (entity.getUser() != null) {
            entity.setUser(entityManager.find(User.class, entity.getUser().getId()));
        }
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
    @Override
    public List<ParticipatedTrip> findAllByTripId(Integer id) {
        entityManager.clear(); //Otherwise takes not updated but yet changed entities into the collection, now it works directly on the database
        Query query = entityManager.createQuery("SELECT pt FROM ParticipatedTrip pt WHERE pt.trip.id = ?1");
        query.setParameter(1, id);
        List<ParticipatedTrip> participatedTrips = query.getResultList();
        return participatedTrips;
    }
}
