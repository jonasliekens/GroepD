package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.ExpenseDao;
import be.kdg.backend.entities.Expense;
import be.kdg.backend.services.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 14:01
 */
@Service("expenseService")
public class ExpenseServiceImpl implements ExpenseService {
    @Qualifier("expenseDaoImpl")
    @Autowired(required = true)
    private ExpenseDao expenseDao;

    @Override
    public void add(Expense entity) {
        expenseDao.add(entity);
    }

    @Override
    public void remove(Expense entity) {
        expenseDao.remove(entity);
    }

    @Override
    public void update(Expense entity) {
        expenseDao.update(entity);
    }

    @Override
    public Expense get(Integer id) {
        return expenseDao.findById(id);
    }

    @Override
    public List<Expense> findAllByTripIdAndUserId(Integer tripId, Integer userId) {
        return expenseDao.findAllByTripIdAndUserId(tripId, userId);
    }

    @Override
    public List<Expense> findAllByTripId(Integer id) {
        return expenseDao.findAllByTripId(id);
    }
}
