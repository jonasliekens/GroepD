package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.BroadcastDao;
import be.kdg.backend.entities.BroadcastMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 6/03/13
 * Time: 11:31
 * Copyright @ Soulware.be
 */
@Repository
public class BroadCastDaoImpl implements BroadcastDao {

    protected EntityManager entityManager;

    public BroadCastDaoImpl() {
        entityManager = emf.createEntityManager();
    }

    @Override
    public List<BroadcastMessage> findMessagesByUserId(Integer userId) {
        Query query = entityManager.createQuery("select b from BroadcastMessage b join b.recievers u where u.id = ?1");
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    public void add(BroadcastMessage entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(BroadcastMessage entity) {
        entityManager.getTransaction().begin();
        entity = entityManager.find(BroadcastMessage.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    @Deprecated
    public void update(BroadcastMessage entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public BroadcastMessage findById(Integer integer) throws NoResultException {
        Query query = entityManager.createQuery("select b from BroadcastMessage b where id = ?1");
        query.setParameter(1, integer);
        return (BroadcastMessage) query.getSingleResult();
    }

    @Override
    public List<BroadcastMessage> findAll() {
        Query query = entityManager.createQuery("select b from BroadcastMessage b");
        return query.getResultList();
    }
}
