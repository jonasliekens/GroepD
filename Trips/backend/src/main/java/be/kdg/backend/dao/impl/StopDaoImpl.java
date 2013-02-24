package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.StopDao;
import be.kdg.backend.entities.Stop;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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
    public void add(Stop entity) {
        initEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void remove(Stop entity) {
        initEntityManager();
        entityManager.getTransaction().begin();
        entity=entityManager.find(Stop.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void update(Stop entity) {
        initEntityManager();
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public Stop find(Integer stopId) {
        initEntityManager();
        Query query = entityManager.createQuery("select s from Stop s");
        return (Stop)query.getResultList().get(0);
    }

    @Override
    @Transactional
    public List<Stop> list() {
        initEntityManager();
        Query query = entityManager.createQuery("select s from Stop s");
        return query.getResultList();
    }

    public void initEntityManager(){
        emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        entityManager = emf.createEntityManager();
    }
}
