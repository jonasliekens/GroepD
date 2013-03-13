package be.kdg.backend.dao.interfaces;

import be.kdg.backend.entities.Expense;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 00:57
 */
public interface ExpenseDao extends GenericDao<Expense, Integer> {
    @Transactional
    public List<Expense> findAllByTripIdAndUserId(Integer tripId, Integer userId);

    @Transactional
    public List<Expense> findAllByTripId(Integer id);
}
