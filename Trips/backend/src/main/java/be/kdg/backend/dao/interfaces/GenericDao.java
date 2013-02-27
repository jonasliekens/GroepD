package be.kdg.backend.dao.interfaces;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 15:08
 * Copyright @ Soulware.be
 */
public interface GenericDao<E, ID extends Serializable> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
    @Transactional
    public void add(E entity);
    @Transactional
    public void remove(E entity);
    @Transactional
    public void update(E entity);
    @Transactional
    public E findById(ID id);
    @Transactional
    public List<E> findAll();
}
