package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.StopDao;
import be.kdg.backend.entities.Photo;
import be.kdg.backend.entities.Stop;
import be.kdg.backend.utilities.StopComparator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 21/02/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class StopDaoImpl implements StopDao {

    protected EntityManager entityManager;

    public StopDaoImpl() {
        entityManager = emf.createEntityManager();
    }

    @Override
    public void add(Stop entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Stop entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Stop entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Stop findById(Integer stopId) {
        return entityManager.find(Stop.class, stopId);
    }

    @Override
    public List<Stop> findAll() {
        entityManager.clear();
        Query query = entityManager.createQuery("select s from Stop s");
        return query.getResultList();
    }

    @Override
    public List<Stop> findAllByTripId(Integer id) {
        entityManager.clear(); //Otherwise takes not updated but yet changed entities into the collection, now it works directly on the database
        Query query = entityManager.createQuery("SELECT s FROM Stop s WHERE s.trip.id = ?1");
        query.setParameter(1, id);
        List<Stop> stops = query.getResultList();
        Collections.sort(stops, new StopComparator());

        return stops;
    }

    @Override
    public List<Photo> findPhotosByStopId(Integer stopId) {
        Query query = entityManager.createQuery("Select p FROM Photo p WHERE p.stop.id = ?1");
        query.setParameter(1,stopId);
        List<Photo> photos = query.getResultList();
        return photos;
    }

    @Override
    public void removePhotoByPhotoId(Integer photoId) {
        entityManager.clear();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Delete FROM Photo p WHERE p.id = ?1");
        query.setParameter(1,photoId);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
