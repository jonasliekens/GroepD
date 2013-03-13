package be.kdg.backend.services.interfaces;

import be.kdg.backend.entities.Expense;

import java.util.List;

/**
 * User: Bart Verhavert
 * Date: 12/03/13 14:01
 */
public interface ExpenseService extends GenericService<Expense, Integer> {
    public List<Expense> findAllByTripIdAndUserId(Integer tripId, Integer userId);

    List<Expense> findAllByTripId(Integer id);
}
