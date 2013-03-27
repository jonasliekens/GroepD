package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.RequestDao;
import be.kdg.backend.entities.Request;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 26/03/13
 * Time: 13:06
 * Copyright @ Soulware.be
 */
@Repository
public class RequestDaoImpl implements RequestDao{
    protected EntityManager entityManager;

    public RequestDaoImpl() {
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public Request findRequestByFBRequestId(String id) {
        Query query = entityManager.createQuery("select r from Request r where r.requestId = ?1");
        query.setParameter(1, id);
        return (Request) query.getSingleResult();
    }

    @Override
    public void add(Request entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Request entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(entity);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(Request entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(entity);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public Request findById(Integer integer) {
        return this.entityManager.find(Request.class, integer);
    }

    @Override
    public List<Request> findAll() {
        this.entityManager.clear();
        Query query = this.entityManager.createQuery("select r from Request r");
        return query.getResultList();
    }
}
