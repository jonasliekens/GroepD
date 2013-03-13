package be.kdg.backend.dao.impl;

import be.kdg.backend.dao.interfaces.ExpenseDao;
import be.kdg.backend.entities.Expense;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 00:57
 */
@Repository
public class ExpenseDaoImpl implements ExpenseDao {
    protected EntityManager entityManager;

    public ExpenseDaoImpl() {
        this.entityManager =  emf.createEntityManager();
    }

    @Override
    public void add(Expense entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Expense entity) {
        entityManager.getTransaction().begin();
        entity=entityManager.find(Expense.class, entity.getId());
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Expense entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Expense findById(Integer id) {
        return entityManager.find(Expense.class, id);
    }

    @Override
    public List<Expense> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Expense> findAllByTripIdAndUserId(Integer tripId, Integer userId) {
        Query query = entityManager.createQuery("SELECT e FROM Expense e WHERE e.trip.id = :tripId AND e.user.id = :userId");
        query.setParameter("tripId", tripId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Expense> findAllByTripId(Integer id) {
        Query query = entityManager.createQuery("SELECT e FROM Expense e WHERE e.trip.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
