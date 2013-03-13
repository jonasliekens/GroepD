package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.TripDao;
import be.kdg.backend.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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
    public void add(Trip entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Trip entity) {
        entityManager.getTransaction().begin();
        entityManager.clear();
        entity = entityManager.find(Trip.class, entity.getId());
        for (ParticipatedTrip pt : entity.getParticipatedTrips()) {
            pt = entityManager.find(ParticipatedTrip.class, pt.getId());
            entityManager.remove(pt);
        }

        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Trip entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Trip findById(Integer id) {
        return entityManager.find(Trip.class, id);
    }

    @Override
    public List<Trip> findAll() {
        Query query = entityManager.createQuery("select t from Trip t");
        return query.getResultList();
    }

    @Override
    public List<Trip> getPublicTrips() {
        Query query = entityManager.createQuery("select t from Trip t where t.privateTrip = false");
        return query.getResultList();
    }

    @Override
    public void removeAnnouncementFromTrip(Integer announcementId) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from Announcement a where a.id = ?1");
        query.setParameter(1, announcementId);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Announcement> getAnnouncementsByTripId(Integer tripId) {
        entityManager.clear();
        Query query = entityManager.createQuery("select a from Announcement a where a.trip.id=?1");
        query.setParameter(1, tripId);
        return query.getResultList();
    }

    @Override
    public void removeEquipmentFromTrip(Integer equipmentId) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from Equipment eq where eq.id = ?1");
        query.setParameter(1, equipmentId);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Equipment> getEquipmentByTrip(Integer tripId) {
        entityManager.clear();
        Query query = entityManager.createQuery("select eq from Equipment eq where eq.trip.id=?1");
        query.setParameter(1, tripId);
        return query.getResultList();
    }

    @Override
    public List<Trip> findOwnTripsByUserId(Integer userId){
        Query query;
        List<Integer> adminsId = new ArrayList<>();
        query = entityManager.createQuery("select admins from Trip");
        List<User> admins = query.getResultList();

        for(User user : admins){
            adminsId.add(user.getId());
        }

        query = entityManager.createQuery("select t from Trip t where ?1 IN ?2");
        query.setParameter(1, userId);
        query.setParameter(2, adminsId);
        List<Trip> trips = query.getResultList();
        return trips;
    }
}
